package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.config.BreinConfig;
import com.brein.domain.BreinRequest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.log4j.Logger;

import java.io.IOException;


/**
 * Unirest Implementation
 * <p>
 * UNIREST (see: http://unirest.io/java.html)
 */
public class UniRestEngine implements IRestClient {

    /**
     * Logger instance
     */
    private static final Logger LOG = Logger.getLogger(UniRestEngine.class);

    /**
     * Invokes the asynch post call
     *
     * @param breinActivity data to send
     */
    public void doRequest(final BreinActivity breinActivity) {

        if (breinActivity == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within doRequestAsynch - breinActivity is null");
            }
            return;
        }

        final BreinConfig breinConfig = breinActivity.getBreinConfig();
        if (breinConfig == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within doRequestAsynch - breinConfig is null");
            }
            return;
        }

        final long connectionTimeout = breinActivity.getBreinConfig().getConnectionTimeout();
        final long socketTimeout = breinActivity.getBreinConfig().getSocketTimeout();

        if (connectionTimeout != 0 && socketTimeout != 0) {
            Unirest.setTimeouts(connectionTimeout, socketTimeout);
        }

        final String url = breinActivity.getBreinConfig().getUrl();

        /**
         * timestamp (Java 8 Impl)

         final long unixTimestamp = Instant.now().getEpochSecond();
         */

        final long unixTimestamp = System.currentTimeMillis() / 1000L;
        final BreinRequest breinRequest = new BreinRequest(breinActivity, unixTimestamp);
        final String requestBody = breinRequest.toJson();

        Unirest.post(url)
                .header("accept", "application/json")
                .body(requestBody)
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("The request has failed");
                        }
                    }

                    public void completed(HttpResponse<JsonNode> response) {

                        if (LOG.isDebugEnabled()) {
                            LOG.debug("The request was completed");
                        }
                    }

                    public void cancelled() {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("The request has been cancelled");
                        }

                    }

                });


    }

    /**
     * used to stop the unirest threads
     */
    public void stop() {
        try {
            Unirest.shutdown();
        } catch (IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.error("Exception within Unirest shutdown has occurred. ", e);
            }
        }
    }
}
