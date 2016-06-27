package com.brein.config;

import com.brein.engine.RestClientFactory;

/**
 * Contains Breinify Endpoint configuration
 *
 */
public class BreinConfig {

    /**
     * Configuration constants
     */
    public static final String ACTIVITY_ENDPOINT    = "/activity";
    public static final String LOOKUP_ENDPOINT      = "/lookup";
    public static final long   CONNECTION_TIMEOUT   = 1000;
    public static final long   SOCKET_TIMEOUT       = 6000;
    public static boolean      VALIDATE             = true;

    /**
     * BASE URL
     */
    private String baseUrl = "https://api.breinify.com";

    /**
     * contains the api key
     */
    private String apiKey;

    private int restClientType = RestClientFactory.UNIREST_ENGINE;

    /**
     * @param apiKey contains the Breinify api-key
     */
    public BreinConfig(final String apiKey, final String baseUrl) {

        setApiKey(apiKey);
        setBaseUrl(baseUrl);
    }

    /**
     * Empty Ctor
     */
    public BreinConfig() {}

    /**
     * retrieves the base url
     * @return base url
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * set the base url of the breinify backend
     * @param baseUrl
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * retrieves rest type client
     * @return configured rest type client
     */
    public int getRestClientType() {
        return restClientType;
    }

    /**
     * set rest type client
     * @param restClientType of the rest impl
     */
    public void setRestClientType(int restClientType) {
        this.restClientType = restClientType;
    }

    /**
     * sets the apikey
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
     * @return apikey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * retrieves the url for the post requests
     * @return base url
     */
    public String getUrl() {
        return baseUrl;
    }

    /**
     * retrieves the configures timeout values
     * @return connection time out
     */
    public long getConnectionTimeout() {
        return CONNECTION_TIMEOUT;
    }

    /**
     * socket timeout values
     * @return connection time out values
     */
    public long getSocketTimeout() {
        return SOCKET_TIMEOUT;
    }

}
