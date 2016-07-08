package com.brein.api;

import com.brein.domain.*;

/**
 * Static Implementation of Breinify activity & lookup calls
 */
public class BreinifyExecutor {

    /**
     * contains the current version of the usage library
     */
    private static final String VERSION = "1.0.0-snapshot";

    /**
     * contains the configuration
     */
    private BreinConfig config;

    /**
     * contains the activity object
     */
    private final BreinActivity breinActivity = new BreinActivity();

    /**
     * contains the lookup object
     */
    private final BreinLookup breinLookup = new BreinLookup();

    /**
     * sets the configuration
     *
     * @param breinConfig config object
     */
    public void setConfig(final BreinConfig breinConfig) {
        config = breinConfig;
        breinActivity.setConfig(breinConfig);
        breinLookup.setConfig(breinConfig);
    }

    /**
     * gets the config
     *
     * @return config
     */
    public BreinConfig getConfig() {
        return config;
    }

    /**
     * returns the version
     *
     * @return version
     */
    public String getVersion() {
        return VERSION;
    }

    /**
     * Sends an activity to the engine utilizing the API. The call is done asynchronously as a POST request.
     * It is important that a valid API-key is configured prior to using this function.
     *
     * This request is asynchronous.
     *
     * @param user         a plain object specifying the user information the activity belongs to
     * @param activityType the type of the activity collected, i.e., one of search, login, logout, addToCart,
     *                     removeFromCart, checkOut, selectProduct, or other.
     *                     if not specified, the default other will be used
     * @param category     the category of the platform/service/products, i.e., one of apparel, home, education,
     *                     family, food, health, job, services, or other
     * @param description  a string with further information about the activity performed
     * @param sign         a boolean value specifying if the call should be signed
     */
    public void activity(final BreinUser user,
                                final BreinActivityType activityType,
                                final BreinCategoryType category,
                                final String description,
                                final boolean sign) {

        breinActivity.setBreinUser(user);
        breinActivity.setBreinActivityType(activityType);
        breinActivity.setBreinCategoryType(category);
        breinActivity.setDescription(description);
        breinActivity.setSign(sign);

        /**
         * invoke the request "this" has all necessary information
         */
        if (null == breinActivity.getBreinEngine()) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }
        breinActivity.getBreinEngine().sendActivity(breinActivity);
    }

    /**
     * Retrieves a lookup result from the engine. The function needs a
     * valid API-key to be configured to succeed.
     *
     * This request is synchronous.
     *
     * @param user      a plain object specifying information about the user to retrieve data for.
     * @param dimension an object (with an array) containing the names of the dimensions to lookup.
     * @param sign      a boolean value specifying if the call should be signed.
     *
     * @return response from request wrapped in an object called BreinResponse
     */
    public BreinResult lookup(final BreinUser user,
                                     final BreinDimension dimension,
                                     final boolean sign) {

        breinLookup.setBreinUser(user);
        breinLookup.setBreinDimension(dimension);

        /**
         * invoke the lookup request
         */
        if (null == breinLookup.getBreinEngine()) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }
        return breinLookup.getBreinEngine().performLookUp(breinLookup);
    }
}
