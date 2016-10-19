package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinException;
import com.brein.api.BreinLookup;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.function.Function;

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
    public static final String MSG_REQUEST_HAS_FAILED = "The request has failed.";
    public static final String MSG_REQUEST_HAS_BEEN_CANCELLED = "The request has been cancelled.";
    public static final String MSG_REQUEST_WAS_SUCCESSFUL = "The request was successful.";
    /**
     * Logger instance
     */
    private static final Logger LOG = LoggerFactory.getLogger(UniRestEngine.class);

    /**
     * configures the rest engine
     */
    @Override
    public void configure(final BreinConfig breinConfig) {

        final long connectionTimeout = breinConfig.getConnectionTimeout();
        final long socketTimeout = breinConfig.getSocketTimeout();

        if (connectionTimeout != 0 && socketTimeout != 0) {
            Unirest.setTimeouts(connectionTimeout, socketTimeout);
        }
    }

    /**
     * Invokes the asynchronous post call
     *
     * @param breinActivity data to send
     * @param errorCallback will be invoked in case of an error
     */
    @Override
    public void doRequest(final BreinActivity breinActivity,
                          final Function<String, Void> errorCallback) throws BreinException {

        // validate the input objects
        validate(breinActivity);

        /*
         * invoke the request
         */

        Unirest.post(getFullyQualifiedUrl(breinActivity))
                .header(HEADER_ACCESS, HEADER_APP_JSON)
                .body(getRequestBody(breinActivity))
                .asJsonAsync(new Callback<JsonNode>() {

                    @Override
                    public void completed(final HttpResponse<JsonNode> response) {

                        if (LOG.isDebugEnabled()) {
                            LOG.debug(MSG_REQUEST_WAS_SUCCESSFUL);
                        }
                    }

                    @Override
                    public void failed(final UnirestException e) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(MSG_REQUEST_HAS_FAILED);
                        }

                        if (errorCallback != null) {
                            errorCallback.apply(MSG_REQUEST_HAS_FAILED);
                        }
                    }

                    @Override
                    public void cancelled() {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(MSG_REQUEST_HAS_BEEN_CANCELLED);
                        }

                        if (errorCallback != null) {
                            errorCallback.apply(MSG_REQUEST_HAS_BEEN_CANCELLED);
                        }
                    }
                });
    }

    /**
     * performs a lookup  and provides details
     *
     * @param breinLookup contains request data
     * @return response from Breinify
     * @throws BreinException in case of an error
     */
    @Override
    public BreinResult doLookup(final BreinLookup breinLookup) throws BreinException {

        // validate the input objects
        validate(breinLookup);

        HttpResponse<JsonNode> jsonResponse;
        try {
            final String requestBody = getRequestBody(breinLookup);
            jsonResponse =
                    Unirest.post(getFullyQualifiedUrl(breinLookup))
                            .header(HEADER_ACCESS, HEADER_APP_JSON)
                            .body(requestBody)
                            .asJson();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Result from lookup is: ");
            }

            return new BreinResult(jsonResponse.getBody().toString());

        } catch (final UnirestException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within doLookup - exception has occurred. " + e);
            }
            throw new BreinException(BreinException.LOOKUP_EXCEPTION, e);
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
}
