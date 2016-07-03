package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinLookup;
import com.brein.config.BreinConfig;
import com.brein.domain.BreinResponse;
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
     * configures the rest engine
     */
    public void configure() {

    }

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

        final BreinConfig breinConfig = breinActivity.getConfig();
        if (breinConfig == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within doRequestAsynch - breinConfig is null");
            }
            return;
        }

        /**
         * set some timeouts
         */
        final long connectionTimeout = breinActivity.getConfig().getConnectionTimeout();
        final long socketTimeout = breinActivity.getConfig().getSocketTimeout();

        // TODO
        /*

        MAYBE THIS WILL CAUSE THE FILE-HANDLER ISSUE

        if (connectionTimeout != 0 && socketTimeout != 0) {
            Unirest.setTimeouts(connectionTimeout, socketTimeout);
        }
        */

        /**
         * create endpoint url
         */
        final String url = breinActivity.getConfig().getUrl();
        final String fullUrl = url + breinActivity.getConfig().getActivityEndpoint();


        final String requestBody = breinActivity.prepareJsonRequest();

        Unirest.post(fullUrl)
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
     * performs a lookup and provides details



     * SAMPLE:
     {
     "user": {
     "email": "philipp@meisen.net"
     },

     "lookup": {
     "dimensions": ["firstname", "gender", "age", "agegroup", "digitalfootprint", "images"]
     },

     "apiKey": "{{lookupApiKey}}"
     }
     *
     *


     * @param breinLookup contains request data
     * @return response from Breinify
     */
    public BreinResponse doLookup(final BreinLookup breinLookup) {

        if (breinLookup == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within doRequestAsynch - breinLookup is null");
            }
            return null;
        }

        final BreinConfig breinConfig = breinLookup.getConfig();
        if (breinConfig == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within doRequestAsynch - breinConfig is null");
            }
            return null;
        }

        /**
         * create endpoint url
         */
        final String url = breinLookup.getConfig().getUrl();
        final String fullUrl = url + breinLookup.getConfig().getLookupEndpoint();
        final String requestBody = breinLookup.prepareJsonRequest();

        try {
            final HttpResponse<JsonNode> jsonResponse = Unirest.post(fullUrl)
                    .header("accept", "application/json")
                    .body(requestBody)
                    .asJson();

            final JsonNode json = jsonResponse.getBody();
            return new BreinResponse(json.toString());

        } catch (UnirestException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within doLookup - exception has occurred. " + e );
            }
        }


        return null;
    }

    /**
     * used to stop the UNIREST threads
     */
    public void stop() {
        try {
            Unirest.shutdown();
        } catch (IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.error("Exception within UNIREST shutdown has occurred. ", e);
            }
        }
    }
}
