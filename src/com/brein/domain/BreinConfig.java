package com.brein.domain;

import com.brein.api.BreinInvalidConfigurationException;
import com.brein.api.BreinifyExecutor;
import com.brein.engine.BreinEngine;
import com.brein.engine.BreinEngineType;
import com.brein.engine.IRestEngine;
import com.brein.util.BreinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

/**
 * Provides the configuration of the library for the properties supplied.
 */
public class BreinConfig {

    /**
     * Logger instance
     */
    private static final Logger LOG = LoggerFactory.getLogger(BreinConfig.class);

    /**
     * default endpoint for activity
     */
    public static final String DEFAULT_ACTIVITY_ENDPOINT = "/activity";;


    /**
     * default endpoint for lookup
     */
    public static final String DEFAULT_LOOKUP_ENDPOINT = "/lookup";

    /**
     * default endpoint for temporalData
     */
    public static final String DEFAULT_TEMPORALDATA_ENDPOINT = "/temporaldata";

    /**
     * default endpoint for recommendation
     */
    public static final String DEFAULT_RECOMMENDATION_ENDPOINT = "/recommendation";

    /**
     * default connection timeout
     */
    public static final long DEFAULT_CONNECTION_TIMEOUT = 1000;

    /**
     * default socket timeout
     */
    public static final long DEFAULT_SOCKET_TIMEOUT = 6000;

    /**
     * default Breinify base url
     */
    public static final String DEFAULT_BASE_URL = "https://api.breinify.com";

    /**
     * default {@code BreinEngineType}
     */
    public static final BreinEngineType DEFAULT_ENGINE_TYPE = BreinEngineType.AUTO_DETECT;

    /**
     * BASE URL
     */
    private String baseUrl = DEFAULT_BASE_URL;

    /**
     * contains the api key
     */
    private String apiKey;

    /**
     * Default REST client configuration
     */
    private BreinEngineType restEngineType = DEFAULT_ENGINE_TYPE;

    /**
     * contains the activity endpoint (default = ACTIVITY_ENDPOINT)
     */
    private String activityEndpoint = DEFAULT_ACTIVITY_ENDPOINT;

    /**
     * contains the lookup endpoint (default = LOOKUP_ENDPOINT)
     */
    private String lookupEndpoint = DEFAULT_LOOKUP_ENDPOINT;

    /**
     * contains the temporalData endpoint (default = DEFAULT_TEMPORALDATA_ENDPOINT)
     */
    private String temporalDataEndpoint = DEFAULT_TEMPORALDATA_ENDPOINT;

    /**
     * contains the recommendation endpoint (default = DEFAULT_RECOMMENDATION_ENDPOINT)
     */
    private String recommendationEndpoint = DEFAULT_RECOMMENDATION_ENDPOINT;

    /**
     * connection timeout
     */
    private long connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;

    /**
     * Engine with default value
     */
    private BreinEngine breinEngine;

    /**
     * socket timeout
     */
    private long socketTimeout = DEFAULT_SOCKET_TIMEOUT;

    /**
     * default category
     */
    private String defaultCategory = "";

    /**
     * contains the secret that will be used for the signature
     */
    private String secret;

    /**
     * @param apiKey contains the Breinify api key
     */
    public BreinConfig(final String apiKey) {
        this();
        setApiKey(apiKey);
    }

    /**
     * @param apiKey contains the Breinify api key
     * @param secret contains the secret
     */
    public BreinConfig(final String apiKey,
                       final String secret) {
        this(apiKey);
        setSecret(secret);
    }

    /**
     * Base Constructor - will be invoked in any case to initialize the
     * Rest Engine.
     */
    public BreinConfig() {
        initEngine();
    }

    /**
     * initializes the rest client
     * @return self
     */
    public BreinConfig initEngine() {
        BreinEngineType engine = getRestEngineType();

        if (BreinEngineType.AUTO_DETECT.equals(engine)) {
            engine = Stream.of(BreinEngineType.values())
                    .filter(BreinEngineType::isSupported)
                    .findFirst()
                    .orElse(BreinEngineType.AUTO_DETECT);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Selected '" + engine + "' through auto-detection.");
            }
        }

        breinEngine = new BreinEngine(engine);
        return this;
    }

    /**
     * builder method - based on th configuration an universal executer
     * will be created.
     *
     * @return new created executer
     */
    public BreinifyExecutor build() {

        final BreinifyExecutor breinifyExecutor = new BreinifyExecutor();
        breinifyExecutor.setConfig(this);

        initEngine();

        return breinifyExecutor;
    }

    /**
     * retrieves the base url
     *
     * @return base url
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * set the base url of the breinify backend and will check
     * if the URL is valid.
     *
     * @param baseUrl contains the url
     * @return the config object itself
     */
    public BreinConfig setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
        checkBaseUrl(baseUrl);
        return this;
    }

    /**
     * checks if the url is valid. If not a BreinInvalidConfigurationException will
     * be thrown.
     *
     * @param baseUrl url to check
     */
    public void checkBaseUrl(final String baseUrl) throws BreinInvalidConfigurationException {

        if (!isUrlValid(baseUrl)) {
            final String msg = "BreinConfig issue. Value for BaseUrl is not valid. Value is: "
                    + baseUrl;
            if (LOG.isDebugEnabled()) {
                LOG.debug(msg);
            }
            throw new BreinInvalidConfigurationException(msg);
        }
    }

    /**
     * retrieves rest type client
     *
     * @return configured rest type client
     */
    public BreinEngineType getRestEngineType() {
        return restEngineType;
    }

    /**
     * set rest type client
     *
     * @param restEngineType of the rest impl
     * @return the config object itself
     */
    public BreinConfig setAndInitRestEngine(final BreinEngineType restEngineType) {
        this.restEngineType = restEngineType;
        initEngine();
        return this;
    }

    /**
     * returns the configured brein engine for the rest calls
     *
     * @return brein engine
     */
    public BreinEngine getBreinEngine() {
        return breinEngine;
    }

    /**
     * retrieves the apikey
     *
     * @return apikey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * sets the apikey
     *
     * @param apiKey the apikey
     * @return the config object itself
     */
    public BreinConfig setApiKey(final String apiKey) {

        if (BreinUtil.containsValue(apiKey)) {
            this.apiKey = apiKey;
        }
        return this;
    }

    /**
     * retrieves the url for the post requests
     *
     * @return base url
     */
    public String getUrl() {
        return baseUrl;
    }

    /**
     * retrieves the configured timeout values
     *
     * @return connection time out
     */
    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * set the connection timeout
     *
     * @param connectionTimeout value
     * @return self
     */
    public BreinConfig setConnectionTimeout(final long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    /**
     * socket timeout values
     *
     * @return connection time out values
     */
    public long getSocketTimeout() {
        return socketTimeout;
    }

    /**
     * set the socket timeout
     *
     * @param socketTimeout value
     * @return self
     */
    public BreinConfig setSocketTimeout(final long socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    /**
     * retrieves the activity endpoint
     *
     * @return endpoint
     */
    public String getActivityEndpoint() {
        return activityEndpoint;
    }

    /**
     * sets the activity endpoint
     *
     * @param activityEndpoint endpoint
     * @return the config object itself
     */
    public BreinConfig setActivityEndpoint(final String activityEndpoint) {
        this.activityEndpoint = activityEndpoint;
        return this;
    }

    /**
     * retrieves the lookup endpoint
     *
     * @return lookup endpoint
     */
    public String getLookupEndpoint() {
        return lookupEndpoint;
    }

    /**
     * sets the lookup endpoint
     *
     * @param lookupEndpoint endpoint
     * @return the config object itself
     */
    public BreinConfig setLookupEndpoint(final String lookupEndpoint) {
        this.lookupEndpoint = lookupEndpoint;
        return this;
    }

    /**
     * retrieves the temporaldata endpoint
     *
     * @return temporaldata endpoint
     */
    public String getTemporalDataEndpoint() {
        return temporalDataEndpoint;
    }

    /**
     * sets the temporaldata endpoint
     *
     * @param temporalDataEndpoint endpoint
     */
    public void setTemporalDataEndpoint(final String temporalDataEndpoint) {
        this.temporalDataEndpoint = temporalDataEndpoint;
    }

    /**
     * returns the recommendation endpoint
     *
     * @return the recommendation endpoint
     */
    public String getRecommendationEndpoint() {
        return recommendationEndpoint;
    }

    /**
     * sets the recommendation endpoint
     *
     * @param recommendationEndpoint contains the endpoint
     * @return self
     */
    public BreinConfig setRecommendationEndpoint(final String recommendationEndpoint) {
        this.recommendationEndpoint = recommendationEndpoint;
        return this;
    }

    /**
     * returns the configured secret
     *
     * @return raw secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * set the secret
     *
     * @param secret raw secret
     * @return self
     */
    public BreinConfig setSecret(final String secret) {
        this.secret = secret;
        return this;
    }

    /**
     * returns the default category (if set)
     *
     * @return default category
     */
    public String getDefaultCategory() {
        return defaultCategory;
    }

    /**
     * sets the default category
     *
     * @param defaultCategory default to set
     * @return self
     */
    public BreinConfig setDefaultCategory(final String defaultCategory) {
        this.defaultCategory = defaultCategory;
        return this;
    }

    /**
     * invokes the termination of the rest engine.
     * Depending of the configured engine additional threads might
     * have been allocated and this will close those threads.
     */
    public void shutdownEngine() {

        // check valid objects
        if (this.breinEngine == null) {
            return;
        }

        if (this.breinEngine.getRestEngine() == null) {
            return;
        }

        // invoke termination of the engine
        this.breinEngine.getRestEngine().terminate();
    }

    /**
     * Validates if the URL is correct.
     *
     * @param url to check
     * @return true if ok otherwise false
     */
    public boolean isUrlValid(final String url) {
        return IRestEngine.isUrlValid(url);
    }
}
