package com.brein;

import com.brein.config.BreinifyConfig;

/**
 *
 */
public class Breinify {

    /**
     * Configuration
     */
    private BreinifyConfig breinifyConfig = new BreinifyConfig();


    public Breinify(final String apiKey) {
        setConfig(apiKey);
    }

    public Breinify() {

    }

    public void setConfig(final String apiKey) {
        breinifyConfig.setConfig(apiKey);
    }

    public boolean validConfig() {
        final String apiKey = breinifyConfig.getApiKey();
        if (apiKey == null) {
            return false;
        }

        if (apiKey.length() == 0) {
            return false;
        }

        return true;
    }

}
