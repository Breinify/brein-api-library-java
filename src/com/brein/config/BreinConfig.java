package com.brein.config;

import com.brein.engine.BreinEngine;
import com.brein.engine.BreinEngineType;

/**
 * Contains Breinify Endpoint configuration
 */
public class BreinConfig {

    /**
     * Configuration constants
     */
    public static final String DEFAULT_ACTIVITY_ENDPOINT = "/activity";
    public static final String DEFAULT_LOOKUP_ENDPOINT = "/lookup";
    public static final long DEFAULT_CONNECTION_TIMEOUT = 1000;
    public static final long DEFAULT_SOCKET_TIMEOUT = 6000;
    public static boolean DEFAULT_VALIDATE = true;  // not used yet!
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
    private BreinEngineType restClientType = BreinEngineType.UNIREST_ENGINE;

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
        setRestClientType(breinEngineType);
        initClient();
    }

    /**
     * initializes the rest client
     */
    private void initClient() {
        breinEngine = new BreinEngine(getRestClientType());
    }

    /**
     * Empty Ctor - necessary
     */
    public BreinConfig() {
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
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * retrieves rest type client
     *
     * @return configured rest type client
     */
    public BreinEngineType getRestClientType() {
        return restClientType;
    }

    /**
     * set rest type client
     *
     * @param restClientType of the rest impl
     */
    public void setRestClientType(final BreinEngineType restClientType) {
        this.restClientType = restClientType;
    }

    /**
     * returns the configured brein engine for the rest calls
     * @return brein engine
     */
    public BreinEngine getBreinEngine() {
        return breinEngine;
    }

    /**
     * sets the apikey
     *
     * @param apiKey the apikey
     */
    public void setApiKey(final String apiKey) {

        if (apiKey == null) {
            return;
        }

        if (apiKey.length() > 0) {
            this.apiKey = apiKey;
        }
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
     */
    public void setActivityEndpoint(String activityEndpoint) {
        this.activityEndpoint = activityEndpoint;
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
     */
    public void setLookupEndpoint(String lookupEndpoint) {
        this.lookupEndpoint = lookupEndpoint;
    }
}
