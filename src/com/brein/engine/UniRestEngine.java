package com.brein.engine;

import com.brein.api.BreinBase;
import com.brein.api.BreinException;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.options.Options;
import com.mashape.unirest.request.body.RequestBodyEntity;
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
    public static final String MSG_URL_IS_NULL = "The url is null.";

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
                .asJsonAsync(new UniRestCallback(this, callback));
    }

    @Override
    public BreinResult invokeRequest(final BreinConfig config, final BreinBase data) {
        try {
            final String requestBody = getRequestBody(config, data);
            final RequestBodyEntity entity = Unirest.post(getFullyQualifiedUrl
                    (config, data))
                    .header(HEADER_ACCESS, HEADER_APP_JSON)
                    .body(requestBody);

            final HttpResponse<String> strResponse = entity.asString();

            if (strResponse.getStatus() == 200) {
                final Map<String, Object> mapResponse = parseJson(strResponse.getBody());
                return new BreinResult(mapResponse);
            } else {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Request failed  with status '" + strResponse.getStatus() + "' and content " +
                            strResponse.getBody());
                }

                throw new BreinException("Request failed with status '" + strResponse.getStatus() + "'.");
            }
        } catch (final UnirestException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within invokeRequest - exception has occurred. ", e);
            }
            throw new BreinException("Invoke request exception.", e);
        }
    }
}
