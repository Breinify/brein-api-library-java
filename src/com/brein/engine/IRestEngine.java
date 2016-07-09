package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinBase;
import com.brein.api.BreinException;
import com.brein.api.BreinLookup;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.brein.util.BreinUtil;
import org.apache.log4j.Logger;

/**
 * Interface for all possible rest  engines
 */
public interface IRestEngine {

    /**
     * Logger instance
     */
    Logger LOG = Logger.getLogger(IRestEngine.class);

    String HEADER_ACCESS = "accept";
    String HEADER_APP_JSON = "application/json";

    /**
     * configures the rest engine
     * @param breinConfig configuration object
     */
    void configure(final BreinConfig breinConfig);

    /**
     * invokes the post request
     *
     * @param breinActivity data
     */
    void doRequest(final BreinActivity breinActivity) throws BreinException;

    /**
     * performs a lookup and provides details
     *
     * @param breinLookup contains request data
     * @return response from Breinify
     */
    BreinResult doLookup(final BreinLookup breinLookup) throws BreinException;

    /**
     * terminates the rest engine
     *
     */
    void terminate();

    /**
     * Creates the requested Rest Engine.
     *
     * @param engine type of engine
     * @return created Rest-Engine
     */
    static IRestEngine getRestEngine(final BreinEngineType engine) {
        switch (engine) {
            case UNIREST_ENGINE:
                return new UniRestEngine();

            case JERSEY_ENGINE:
                return new JerseyRestEngine();

            default:
                throw new BreinException("no rest engine specified!");
        }
    }

    /**
     * validates the activity object
     *
     * @param breinActivity object to validate
     */
    default void validateActivity(final BreinActivity breinActivity) {
        if (null == breinActivity) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("breinActivity is null");
            }
            throw new BreinException(BreinException.ACTIVITY_VALIDATION_FAILED);
        }
    }

    /**
     * validates the lookup object
     *
     * @param breinLookup object to validate
     */
    default void validateLookup(final BreinLookup breinLookup) {
        if (null == breinLookup) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("breinLookup is null");
            }
            throw new BreinException(BreinException.LOOKUP_VALIDATION_FAILED);
        }
    }

    /**
     * validates the configuration object
     *
     * @param breinBase activity or lookup object
     */
    default void validateConfig(final BreinBase breinBase) {

        final BreinConfig breinConfig = breinBase.getConfig();
        if (null == breinConfig) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within doRequestAsynch - breinConfig is null");
            }
            throw new BreinException(BreinException.CONFIG_VALIDATION_FAILED);
        }
    }

    /**
     * retrieves the fully qualified url (base + endpoint)
     *
     * @param breinBase activity or lookup object
     *
     * @return full url
     */
    default String getFullyQualifiedUrl(final BreinBase breinBase) {
        final BreinConfig breinConfig = breinBase.getConfig();

        final String url = breinConfig.getUrl();
        if (null == url) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("url is null");
            }
            throw new BreinException(BreinException.URL_IS_NULL);
        }

        final String endPoint = breinBase.getEndPoint();

        return url + endPoint;
    }

    /**
     * retrieves the request body depending of the object
     *
     * @param breinBase object to use
     *
     * @return request as json string
     */
    default String getRequestBody(final BreinBase breinBase) {

        final String requestBody = breinBase.prepareJsonRequest();
        if (!BreinUtil.containsValue(requestBody)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("url is null");
            }
            throw new BreinException(BreinException.REQUEST_BODY_FAILED);
        }
        return requestBody;
    }


}
