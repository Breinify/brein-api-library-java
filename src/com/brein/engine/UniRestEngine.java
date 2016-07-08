package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinBase;
import com.brein.api.BreinException;
import com.brein.api.BreinLookup;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.brein.util.BreinUtil;
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
public class UniRestEngine implements IRestEngine {

    /**
     * some constants
     */
    public static final String MSG_URL_IS_NULL = "url is null";
    public static final String MSG_REQUEST_HAS_FAILED = "the request has failed";
    public static final String MSG_REQUEST_HAS_BEEN_CANCELLED = "the request has been cancelled";
    public static final String MSG_REQUEST_WAS_SUCCESSFUL = "the request was successful";

    /**
     * Logger instance
     */
    private static final Logger LOG = Logger.getLogger(UniRestEngine.class);

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
     * Invokes the asynch post call
     *
     * @param breinActivity data to send
     */
    @Override
    public void doRequest(final BreinActivity breinActivity) throws BreinException {

        /**
         * validation of activity and config
         */
        validateActivity(breinActivity);
        validateConfig(breinActivity);

        /**
         * invoke the request
         */
        Unirest.post(getFullyQualifiedUrl(breinActivity))
                .header(HEADER_ACCESS, HEADER_APP_JSON)
                .body(getRequestBody(breinActivity))
                .asJsonAsync(new Callback<JsonNode>() {

                    @Override
                    public void failed(final UnirestException e) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(MSG_REQUEST_HAS_FAILED);
                        }
                        throw new BreinException(BreinException.REQUEST_FAILED);
                    }

                    @Override
                    public void completed(final HttpResponse<JsonNode> response) {

                        if (LOG.isDebugEnabled()) {
                            LOG.debug(MSG_REQUEST_WAS_SUCCESSFUL);
                        }
                    }

                    @Override
                    public void cancelled() {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(MSG_REQUEST_HAS_BEEN_CANCELLED);
                        }

                    }
                });
    }

    /**
     * performs a lookup and provides details
     *
     * @param breinLookup contains request data
     * @return response from Breinify
     * @throws BreinException
     */
    @Override
    public BreinResult doLookup(final BreinLookup breinLookup) throws BreinException {

        /**
         * validation of lookup and config
         */
        validateLookup(breinLookup);
        validateConfig(breinLookup);

        try {
            final HttpResponse<JsonNode> jsonResponse = Unirest.post(getFullyQualifiedUrl(breinLookup))
                    .header(HEADER_ACCESS, HEADER_APP_JSON)
                    .body(getRequestBody(breinLookup))
                    .asJson();

            return new BreinResult(jsonResponse.getBody());

        } catch (UnirestException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within doLookup - exception has occurred. " + e );
            }
            throw new BreinException(BreinException.LOOKUP_EXCEPTION);
        }
    }

    /**
     * retrieves the request body depending of the object
     * @param breinBase object to use
     * @return request as json string
     */
    public String getRequestBody(final BreinBase breinBase) {

        final String requestBody = breinBase.prepareJsonRequest();
        if (!BreinUtil.containsValue(requestBody)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(MSG_URL_IS_NULL);
            }
            throw new BreinException(BreinException.REQUEST_BODY_FAILED);
        }
        return requestBody;
    }

    /**
     * retrieves the fully qualified url (base + endpoint)
     * @param breinBase activity or lookup object
     * @return full url
     */
    public String getFullyQualifiedUrl(final BreinBase breinBase) {
        final BreinConfig breinConfig = breinBase.getConfig();

        final String url = breinConfig.getUrl();
        if (null == url) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(MSG_URL_IS_NULL);
            }
            throw new BreinException(BreinException.URL_IS_NULL);
        }

        final String endPoint = breinBase.getEndPoint();

        return url + endPoint;
    }

    /**
     * validates the configuration object
     * @param breinBase activity or lookup object
     */
    public void validateConfig(final BreinBase breinBase) {

        final BreinConfig breinConfig = breinBase.getConfig();
        if (null == breinConfig) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within doRequestAsynch - breinConfig is null");
            }
            throw new BreinException(BreinException.CONFIG_VALIDATION_FAILED);
        }
    }

    /**
     * validates the activity object
     * @param breinActivity object to validate
     */
    public void validateActivity(final BreinActivity breinActivity) {
        if (null == breinActivity) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("breinActivity is null");
            }
            throw new BreinException(BreinException.ACTIVITY_VALIDATION_FAILED);
        }
    }

    /**
     * validates the lookup object
     * @param breinLookup object to validate
     */
    public void validateLookup(final BreinLookup breinLookup) {
        if (null == breinLookup) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("breinLookup is null");
            }
            throw new BreinException(BreinException.LOOKUP_VALIDATION_FAILED);
        }
    }

    /**
     * used to stop the UNIREST threads
     */
    @Override
    public void terminate() {
        try {
            Unirest.shutdown();
        } catch (IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.error("Exception within UNIREST shutdown has occurred. ", e);
            }
        }
    }
}
