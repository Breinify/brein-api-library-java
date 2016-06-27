package com.brein.api;

import com.brein.config.BreinConfig;
import com.brein.domain.BreinActivityType;
import com.brein.domain.BreinUser;
import com.brein.domain.BreinCategory;
import com.brein.engine.BreinEngine;

/**
 * Sends an activity to the engine utilizing the API.
 * The call is done asynchronously as a POST request.
 * It is important that a valid API-key is configured
 * prior to using this function.
 *
 */
public class BreinActivity {

    /**
     * Engine
     */
    private final BreinEngine breinEngine = new BreinEngine();

    /**
     * Configuration
     */
    private final BreinConfig breinConfig = new BreinConfig();

    /**
     * User
     */
    private BreinUser breinUser;

    /**
     * ActivityType
     */
    private BreinActivityType breinActivityType;

    /**
     * Category
     */
    private BreinCategory breinCategory;

    /**
     * Description
     */
    private String description;

    /**
     * Sign
     */
    private boolean sign;

    /**
     * retrieves the breinuser
     * @return breinuser
     */
    public BreinUser getBreinUser() {
        return breinUser;
    }

    /**
     * sets the brein user
     * @param breinUser user data
     */
    public void setBreinUser(final BreinUser breinUser) {
        this.breinUser = breinUser;
    }

    public BreinActivityType getBreinActivityType() {
        return breinActivityType;
    }

    public void setBreinActivityType(final BreinActivityType breinActivityType) {
        this.breinActivityType = breinActivityType;
    }

    public BreinCategory getBreinCategory() {
        return breinCategory;
    }

    public void setBreinCategory(final BreinCategory breinCategory) {
        this.breinCategory = breinCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(final boolean sign) {
        this.sign = sign;
    }

    /**
     * set the api key
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
     * @param baseUrl contains the base url
     */
    public void setBaseUrl(final String baseUrl) {
        getBreinConfig().setBaseUrl(baseUrl);
    }

    /**
     * retrieves the configuration
     * @return brein config
     */
    public BreinConfig getBreinConfig() {
        return breinConfig;
    }

    /**
     * Sends an activity to the Breinify server.
     *
     * @param breinUser the user-information
     * @param breinActivityType the type of activity
     * @param breinCategory the category (can be null or undefined)
     * @param description the description for the activity
     * @param sign true if a signature should be added (needs the secret to be configured -
     *             not recommended in open systems), otherwise false (can be null or undefined)
     */
    public void activity(final BreinUser breinUser,
                         final BreinActivityType breinActivityType,
                         final BreinCategory breinCategory,
                         final String description,
                         final boolean sign) {

        /**
         * set the values for further usage
         */
        setBreinUser(breinUser);
        setBreinActivityType(breinActivityType);
        setBreinCategory(breinCategory);
        setDescription(description);
        setSign(sign);

        /**
         * invoke the request, "this" has all necessary information
         */
        breinEngine.sendActivity(this);
    }
}
