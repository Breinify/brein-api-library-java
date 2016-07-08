package com.brein.domain;

import com.brein.api.BreinifyExecutor;
import com.brein.engine.BreinEngine;
import com.brein.engine.BreinEngineType;
import com.brein.util.BreinUtil;

/**
 * Contains Breinify Endpoint configuration
 */
public class BreinConfig {

    /**
     * default endpoint of activity
     */
    public static final String DEFAULT_ACTIVITY_ENDPOINT = "/activity";

    /**
     * default endpoint of lookup
     */
    public static final String DEFAULT_LOOKUP_ENDPOINT = "/lookup";

    /**
     * default connection timeout
     */
    public static final long DEFAULT_CONNECTION_TIMEOUT = 1000;

    /**
     * default socket timeout
     */
    public static final long DEFAULT_SOCKET_TIMEOUT = 6000;

    /**
     * default validation
     */
    public static boolean DEFAULT_VALIDATE = true;

    /**
     * default breinify base url
     */
    public static final String DEFAULT_BASE_URL = "https://api.breinify.com";

    /**
     * BASE URL
     */
    private String baseUrl = DEFAULT_BASE_URL;

    /**
     * contains the api key
     */
    private String apiKey;

    /**
     * Default REST client
     */
    private BreinEngineType restEngineType = BreinEngineType.UNIREST_ENGINE;

    /**
     * contains the activity endpoint (default = ACTIVITY_ENDPOINT)
     */
    private String activityEndpoint = DEFAULT_ACTIVITY_ENDPOINT;

    /**
     * contains the lookup endpoint (default = LOOKUP_ENDPOINT)
     */
    private String lookupEndpoint = DEFAULT_LOOKUP_ENDPOINT;

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
     * @param apiKey  contains the Breinify api-key
     * @param baseUrl contains the base url
     */
    public BreinConfig(final String apiKey,
                       final String baseUrl) {

        setApiKey(apiKey);
        setBaseUrl(baseUrl);
        setRestEngineType(BreinEngineType.DEFAULT_ENGINE);
    }

    /**
     * Configuration object
     *
     * @param apiKey          contains the Breinify api-key
     * @param baseUrl         contains the base url
     * @param breinEngineType selected engine
     */
    public BreinConfig(final String apiKey,
                       final String baseUrl,
                       final BreinEngineType breinEngineType) {

        setApiKey(apiKey);
        setBaseUrl(baseUrl);
        setRestEngineType(breinEngineType);
        initEngine();
    }

    /**
     * Empty Ctor - necessary
     */
    public BreinConfig() {
    }

    /**
     * initializes the rest client
     */
    public void initEngine() {
        breinEngine = new BreinEngine(getRestEngineType());
    }

    /**
     * builder method - based on th configuration an universal executer
     * will be created.
     *
     * @return new created executer
     */
    public BreinifyExecutor build() {

        BreinifyExecutor breinifyExecutor = new BreinifyExecutor();
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
     * set the base url of the breinify backend
     *
     * @param baseUrl contains the url
     * @return the config object itself
     */
    public BreinConfig setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;

        return this;
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
    public BreinConfig setRestEngineType(final BreinEngineType restEngineType) {
        this.restEngineType = restEngineType;

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
     * retrieves the apikey
     *
     * @return apikey
     */
    public String getApiKey() {
        return apiKey;
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
     * retrieves the configures timeout values
     *
     * @return connection time out
     */
    public long getConnectionTimeout() {
        return connectionTimeout;
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
     * @param socketTimeout value
     */
    public void setSocketTimeout(final long socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    /**
     * set the connection timeout
     * @param connectionTimeout value
     */
    public void setConnectionTimeout(final long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
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

}
