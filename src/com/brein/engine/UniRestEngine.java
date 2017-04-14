package com.brein.engine;

import com.brein.api.BreinBase;
import com.brein.api.BreinException;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.options.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * Unirest Implementation
 * <p>
 * UNIREST (see: http://unirest.io/java.html)
 */
public class UniRestEngine implements IRestEngine {

    /**
     * some constants
     */
    public static final String MSG_URL_IS_NULL = "The url is null.";
    public static final String MSG_REQUEST_HAS_FAILED = "FAILED";
    public static final String MSG_REQUEST_HAS_BEEN_CANCELLED = "CANCELLED";
    public static final String MSG_REQUEST_WAS_SUCCESSFUL = "SUCCESS";

    /**
     * Logger instance
     */
    private static final Logger LOG = LoggerFactory.getLogger(UniRestEngine.class);

    /**
     * configures the rest engine
     */
    @Override
    public void configure(final BreinConfig config) {
        final long connectionTimeout = config.getConnectionTimeout();
        final long socketTimeout = config.getSocketTimeout();

        if (connectionTimeout != 0 && socketTimeout != 0) {
            Options.refresh();
            Unirest.setTimeouts(connectionTimeout, socketTimeout);

            // we need to warm-up Unirest, see https://github.com/Mashape/unirest-java/issues/92
            final Future<HttpResponse<String>> future = Unirest
                    .get("https://www.breinify.com").asStringAsync();

            while (!future.isDone()) {
                // wait for the request to return
            }
        }
    }

    @Override
    public void invokeAsyncRequest(final BreinConfig config,
                                   final BreinBase data,
                                   final Consumer<BreinResult> callback) {

        // validate the input objects
        validate(config, data);

        /*
         * invoke the request
         */
        Unirest.post(getFullyQualifiedUrl(config, data))
                .header(HEADER_ACCESS, HEADER_APP_JSON)
                .body(getRequestBody(config, data))
                .asJsonAsync(new Callback<JsonNode>() {

                    @Override
                    public void completed(final HttpResponse<JsonNode> response) {
                        final String strResponse = response.getBody().toString();
                        final int status = response.getStatus();

                        final BreinResult result;
                        if (status == 200) {
                            final Map<String, Object> mapResponse = parseJson(strResponse);
                            result = new BreinResult(mapResponse);
                        } else {
                            result = new BreinResult(strResponse, status);
                        }

                        if (callback != null) {
                            callback.accept(result);
                        }
                    }

                    @Override
                    public void failed(final UnirestException e) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(MSG_REQUEST_HAS_FAILED, e);
                        }

                        if (callback != null) {
                            callback.accept(new BreinResult(e, 500));
                        }
                    }

                    @Override
                    public void cancelled() {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(MSG_REQUEST_HAS_BEEN_CANCELLED);
                        }

                        if (callback != null) {
                            callback.accept(new BreinResult(MSG_REQUEST_HAS_BEEN_CANCELLED, 400));
                        }
                    }
                });
    }

    /**
     * used to stop the UNIREST threads
     */
    @Override
    public void terminate() {
        try {
            Unirest.shutdown();
        } catch (final IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.error("Exception within UNIREST shutdown has occurred. ", e);
            }
        }
    }

    @Override
    public BreinResult invokeRequest(final BreinConfig config, final BreinBase data) {
        try {
            final String requestBody = getRequestBody(config, data);
            final HttpResponse<JsonNode> response = Unirest.post(getFullyQualifiedUrl(config, data))
                    .header(HEADER_ACCESS, HEADER_APP_JSON)
                    .body(requestBody)
                    .asJson();

            if (response.getStatus() == 200) {
                final String strResponse = response.getBody().toString();
                final Map<String, Object> mapResponse = parseJson(strResponse);
                return new BreinResult(mapResponse);
            } else {
                throw new BreinException("invoke request exception. Status is: " + response.getStatus());
            }
        } catch (final UnirestException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within invokeRequest - exception has occurred. " + e);
            }
            throw new BreinException("invoke request exception " + e);
        }
    }
}
