package com.brein.api;

import com.brein.config.BreinConfig;
import com.brein.domain.*;


/**
 * Static Implementation of Breinify activity & lookup calls
 */
public class Breinify {

    /**
     * contains the current version of the usage library
     */
    private final String version = "1.0.0-snapshot";

    /**
     * contains the configuration
     */
    private static BreinConfig config;

    /**
     * contains the activity object
     */
    private static BreinActivity breinActivity = new BreinActivity();

    /**
     * contains the lookup object
     */
    private static BreinLookup breinLookup = new BreinLookup();

    /**
     * sets the configuration
     *
     * @param breinConfig config object
     */
    public static void setConfig(final BreinConfig breinConfig) {
        config = breinConfig;
        breinActivity.setConfig(breinConfig);
        breinLookup.setConfig(breinConfig);
    }

    /**
     * gets the config
     *
     * @return config
     */
    public static BreinConfig getConfig() {
        return config;
    }

    /**
     * returns the version
     *
     * @return version
     */
    public String getVersion() {
        return version;
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
    public static void activity(final BreinUser user,
                                final BreinActivityType activityType,
                                final BreinCategory category,
                                final String description,
                                final boolean sign) {

        breinActivity.setBreinUser(user);
        breinActivity.setBreinActivityType(activityType);
        breinActivity.setBreinCategory(category);
        breinActivity.setDescription(description);
        breinActivity.setSign(sign);

        /**
         * invoke the request, "this" has all necessary information
         */
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
    public static BreinResponse lookup(final BreinUser user,
                                       final BreinDimension dimension,
                                       final boolean sign) {

        breinLookup.setBreinUser(user);
        breinLookup.setBreinDimension(dimension);

        /**
         * invoke the lookup request
         */
        return breinLookup.getBreinEngine().performLookUp(breinLookup);
    }

}
