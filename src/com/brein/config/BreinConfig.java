package com.brein.config;

import com.brein.engine.RestClientFactory;

/**
 *
 */
public class BreinConfig {

    /**
     * Configuration constants
     */
    private static final String activityEndpoint = "/activity";
    private static final String url = "https://api.breinify.com";
    private static final String lookupEndpoint = "/lookup";
    private static final long connectionTimeout = 1000;
    private static final long socketTimeout = 6000;
    private static boolean validate = true;

    /**
     * contains the api key
     */
    private String apiKey;

    private int restClient = RestClientFactory.UNIREST_ENGINE;

    public int getRestClient() {
        return restClient;
    }

    public void setRestClient(int restClient) {
        this.restClient = restClient;
    }

    /**
     * @param apiKey contains the Breinify api-key
     */
    public BreinConfig(final String apiKey) {

        setApiKey(apiKey);

    }

    /**
     * Empty Ctor
     */
    public BreinConfig() {}

    /**
     *
     * @param apiKey
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
     *
     * @return
     */
    public String getApiKey() {
        return apiKey;
    }

    public String getUrl() {
        return url;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public long getSocketTimeout() {
        return socketTimeout;
    }




}
