package com.brein.engine;

import com.brein.api.BreinBase;
import com.brein.api.BreinException;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.brein.util.BreinUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Interface for all possible rest  engines
 */
public interface IRestEngine {
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
     * terminates the rest engine
     */
    void terminate();

    /**
     * checks if the url is valid. If not an exception will be thrown
     *
     * @param fullyQualifiedUrl url with endpoint
     */
    default void validateUrl(final String fullyQualifiedUrl) throws BreinException {
        if (!BreinUtil.isValidUrl(fullyQualifiedUrl)) {
            throw new BreinException("URL: " + fullyQualifiedUrl + " is not valid!");
        }
    }

    /**
     * Creates the requested Rest Engine.
     *
     * @param engine type of engine
     *
     * @return created Rest-Engine
     */
    static IRestEngine getRestEngine(final BreinEngineType engine) {
        switch (engine) {
            case UNIREST_ENGINE:
                return new UniRestEngine();

            case JERSEY_ENGINE:
                return new JerseyRestEngine();

            case DUMMY_ENGINE:
                return new DummyRestEngine();

            case AUTO_DETECT:
                return getRestEngine(Stream.of(BreinEngineType.values())
                        .filter(BreinEngineType::isSupported)
                        .findFirst()
                        .orElse(BreinEngineType.AUTO_DETECT));

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
        if (breinBase == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Object is null");
            }

            throw new BreinException(BreinException.BREIN_BASE_VALIDATION_FAILED);
        }
    }

    /**
     * validates the configuration object
     *
     * @param breinConfig activity or lookup object
     */
    default void validateConfig(final BreinConfig breinConfig) {
        if (breinConfig == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("within doRequestAsynch - breinConfig is null");
            }

            throw new BreinException(BreinException.CONFIG_VALIDATION_FAILED);
        }
    }

    default String getFullyQualifiedUrl(final BreinConfig breinConfig, final BreinBase breinBase) {
        final String url = breinConfig.getUrl();
        if (null == url) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("url is null");
            }
            throw new BreinException(BreinException.URL_IS_NULL);
        }

        final String endPoint = breinBase.getEndPoint(breinConfig);

        return url + endPoint;
    }

    default String getRequestBody(final BreinConfig breinConfig, final BreinBase breinBase) {

        final String requestBody = breinBase.prepareRequestData(breinConfig);
        if (!BreinUtil.containsValue(requestBody)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("url is null");
            }
            throw new BreinException(BreinException.REQUEST_BODY_FAILED);
        }
        return requestBody;
    }

    default void validate(final BreinConfig breinConfig, final BreinBase breinBase) {

        // validation of activity and config
        validateBreinBase(breinBase);
        validateConfig(breinConfig);
    }

    @SuppressWarnings("unchecked")
    default Map<String, Object> parseJson(final String jsonResponse) {
        return new Gson().fromJson(jsonResponse, Map.class);
    }

    void invokeAsyncRequest(final BreinConfig config,
                            final BreinBase data,
                            final Consumer<BreinResult> callback);

    BreinResult invokeRequest(final BreinConfig breinConfig, final BreinBase data);
}
