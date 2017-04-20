package com.brein.engine;

import com.brein.api.BreinBase;
import com.brein.api.BreinException;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * could be the jersey rest engine implementation
 */
public class JerseyRestEngine implements IRestEngine {
    private static final Logger LOG = LoggerFactory.getLogger(JerseyRestEngine.class);

    private final Client client = Client.create();
    private ExecutorService threadPool;

    /**
     * configuration of the rest  client
     */
    @Override
    public void configure(final BreinConfig config) {
        if (threadPool != null) {
            closeExecutor(1, 4);
        }

        // TODO: add the value to the configuration
        threadPool = Executors.newFixedThreadPool(5);
    }

    @Override
    public void terminate() {
        closeExecutor(5, 10);
    }

    @Override
    public void invokeAsyncRequest(final BreinConfig config,
                                   final BreinBase data,
                                   final Consumer<BreinResult> callback) {
        threadPool.submit(() -> {
            BreinResult result;

            try {
                result = invokeRequest(config, data);
            } catch (final Exception e) {
                result = new BreinResult(e, 500);
            }

            callback.accept(result);
        });
    }

    @Override
    public BreinResult invokeRequest(final BreinConfig config, final BreinBase data) {
        validate(config, data);

        final String url = getFullyQualifiedUrl(config, data);
        final WebResource webResource = client.resource(url);

        try {
            final ClientResponse response = webResource.type("application/json")
                    .post(ClientResponse.class, getRequestBody(config, data));

            if (response.getStatus() == 200) {
                final String strResponse = response.getEntity(String.class);
                final Map<String, Object> mapResponse = parseJson(strResponse);
                return new BreinResult(mapResponse);
            } else {
                final String exceptionMsg = "Rest call exception with status code: " + response.getStatus();
                throw new BreinException(exceptionMsg);
            }
        } catch (final UniformInterfaceException | ClientHandlerException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception", e);
                throw new BreinException("Jersey rest call exception");
            }
        }

        return null;
    }

    public void closeExecutor(final int firstWaitInSeconds,
                              final int secondWaitInSeconds) {
        // shutdown the executor
        try {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Shutting down the executor-service.");
            }

            /*
             * Shutdown gracefully, which will probably fail because the dequeue
             * is blocking with take(). Nevertheless, we try it and afterwards
             * force it.
             */
            threadPool.shutdown();
            threadPool.awaitTermination(firstWaitInSeconds, TimeUnit.SECONDS);
        } catch (final InterruptedException e) {
            // ignore
        } finally {
            final List<Runnable> notExecuted = threadPool.shutdownNow();
            if (!notExecuted.isEmpty()) {
                LOG.warn("Not handling " + notExecuted.size() + " tasks, have to shut down now.");
            }

            // wait again, we wait a long time here, because a process may be still running
            try {
                threadPool.awaitTermination(secondWaitInSeconds, TimeUnit.SECONDS);
            } catch (final InterruptedException e) {
                // don't do anything
            }

            if (threadPool.isTerminated()) {
                if (LOG.isInfoEnabled()) {
                    LOG.info("The executor-service is shut-down.");
                }
            } else {
                LOG.warn("Cannot terminate the executor-service.");
            }
        }

        threadPool = null;
    }
}
