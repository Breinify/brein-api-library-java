package com.brein.api;

import com.brein.config.BreinConfig;
import com.brein.domain.ActivityType;
import com.brein.domain.BreinifyUser;
import com.brein.domain.Category;
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
    private BreinifyUser breinifyUser;

    /**
     * ActivityType
     */
    private ActivityType activityType;

    /**
     * Category
     */
    private Category category;

    /**
     * Description
     */
    private String description;

    /**
     * Sign
     */
    private boolean sign;

    /**
     *
     * @return
     */
    public BreinifyUser getBreinifyUser() {
        return breinifyUser;
    }

    public void setBreinifyUser(BreinifyUser breinifyUser) {
        this.breinifyUser = breinifyUser;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    /**
     *
     * @param apiKey
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
     *
     * @return
     */
    public BreinConfig getBreinConfig() {
        return breinConfig;
    }

    /**
     * Sends an activity to the Breinify server.
     *
     * @param breinifyUser the user-information
     * @param activityType the type of activity
     * @param category the category (can be null or undefined)
     * @param description the description for the activity
     * @param sign true if a signature should be added (needs the secret to be configured -
     *             not recommended in open systems), otherwise false (can be null or undefined)
     */
    public void activity(final BreinifyUser breinifyUser,
                         final ActivityType activityType,
                         final Category category,
                         final String description,
                         final boolean sign) {

        /**
         * set the values for further usage
         */
        setBreinifyUser(breinifyUser);
        setActivityType(activityType);
        setCategory(category);
        setDescription(description);
        setSign(sign);

        /**
         * invoke the request, "this" has all necessary information
         */
        breinEngine.sendActivity(this);

    }

}
