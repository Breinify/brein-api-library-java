package com.brein.config;

/**
 * Created by marco on 15.06.16.
 */
public class BreinifyConfig {
    /**
     * contains the api key
     */
    private String apiKey;

    /**
     * @param apiKey contains the Breinify api-key
     */
    public BreinifyConfig(final String apiKey) {

        setConfig(apiKey);

    }

    /**
     * Empty Ctor
     */
    public BreinifyConfig() {}

    /**
     *
     * @param apiKey
     */
    public void setConfig(final String apiKey) {
        if (apiKey == null) {
            return;
        }

        if (apiKey.length() > 0) {
            this.apiKey = apiKey;
        }

    }

    public String getApiKey() {
        return apiKey;
    }

}
