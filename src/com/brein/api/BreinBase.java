package com.brein.api;

import com.brein.config.BreinConfig;
import com.brein.domain.BreinUser;
import com.brein.engine.BreinEngine;

/**
 * Base Class for activity and lookup operations.
 *
 */
public class BreinBase {

    /**
     * Engine
     */
    private final BreinEngine breinEngine = new BreinEngine();

    /**
     * contains the User that will be used for the request
     */
    private BreinUser breinUser;

    /**
     * Configuration
     */
    private final BreinConfig breinConfig = new BreinConfig();

    /**
     * sets the api key
     *
     * @param apiKey value
     */
    public void setApiKey(final String apiKey) {
        breinConfig.setApiKey(apiKey);
    }

    /**
     * checks if the api key is valid
     *
     * @return true if config is correct
     */
    public boolean validApiKey() {

        final String apiKey = breinConfig.getApiKey();

        return apiKey != null && apiKey.length() != 0;
    }

    /**
     * sets the base url of the breinify backend
     *
     * @param baseUrl contains the base url
     */
    public void setBaseUrl(final String baseUrl) {
        getBreinConfig().setBaseUrl(baseUrl);
    }

    /**
     * retrieves the configuration
     *
     * @return brein config
     */
    public BreinConfig getBreinConfig() {
        return breinConfig;
    }

    /**
     * retrieves the breinuser
     *
     * @return breinuser
     */
    public BreinUser getBreinUser() {
        return breinUser;
    }

    /**
     * sets the brein user
     *~
     * @param breinUser user data
     */
    public void setBreinUser(final BreinUser breinUser) {
        this.breinUser = breinUser;
    }

    /**
     * returns the configured brein engine
     * @return brein engine
     */
    public BreinEngine getBreinEngine() {
        return breinEngine;
    }
}
