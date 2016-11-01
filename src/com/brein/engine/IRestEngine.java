package com.brein.engine;

import com.brein.api.*;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.brein.util.BreinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Function;


/**
 * Interface for all possible rest  engines
 */
public interface IRestEngine {

    /**
     * Logger instance
     */
    Logger LOG = LoggerFactory.getLogger(IRestEngine.class);

    String HEADER_ACCESS = "accept";
    String HEADER_APP_JSON = "application/json";

    /**
     * configures the rest engine
     *
     * @param breinConfig configuration object
     */
    void configure(final BreinConfig breinConfig);

    /**
     * invokes the post request
     *
     * @param breinActivity data
     * @param errorCallback will be invoked in case of an error
     */
    void doRequest(final BreinActivity breinActivity,
                   final Function<String, Void> errorCallback) throws BreinException;

    /**
     * performs a lookup and provides details
     *
     * @param breinLookup contains request data
     * @return response from Breinify
     */
    BreinResult doLookup(final BreinLookup breinLookup) throws BreinException;

    /**
     * terminates the rest engine
     */
    void terminate();

    /**
     * Validates if the URL is correct.
     *
     * @param url to check
     * @return true if ok otherwise false
     */
    static boolean isUrlValid(final String url) {

        try {
            final URL u = new URL(url);
            final HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("POST");
            huc.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");

            // int responseCode = huc.getResponseCode();

            huc.connect();

            //if (LOG.isDebugEnabled()) {
            //    LOG.debug("response for URL (" + url + ") is: " + huc.getResponseCode());
            //}

            return true;

        } catch (final IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("isUrlValid throws exception: ", e);

            }
            // this must be an error case!
            return false;
        }
    }

    /**
     * checks if the url is valid -> if not an exception will be thrown
     * @param fullyQualifiedUrl url with endpoint
     */
    default void validateUrl(final String fullyQualifiedUrl) throws BreinException {

        final boolean validUrl = isUrlValid(fullyQualifiedUrl);
        if (!validUrl) {
            final String msg = "URL: " + fullyQualifiedUrl + " is not valid!";
            if(LOG.isDebugEnabled()) {
                LOG.debug(msg);
            }
            throw new BreinException(msg);
        }
    }

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
     * @param breinBase object to validate
     */
    default void validateBreinBase(final BreinBase breinBase) {
        if (null == breinBase) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Object is null");
            }
            throw new BreinException(BreinException.BREIN_BASE_VALIDATION_FAILED);
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

    /**
     * Invokes validation of BreinBase object, configuration and url.
     *
     * @param breinBase activity or lookup object
     */
    default void validate(final BreinBase breinBase) {

        // validation of activity and config
        validateBreinBase(breinBase);
        validateConfig(breinBase);
    }

    /**
     * performs a temporalData request
     * @param breinTemporalData contains the request data
     * @return result from request
     * @throws BreinException exception that will be thrown
     */
    BreinResult doTemporalDataRequest(BreinTemporalData breinTemporalData) throws BreinException;
}
