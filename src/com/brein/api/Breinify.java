package com.brein.api;

import com.brein.domain.BreinActivityType;
import com.brein.domain.BreinCategoryType;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinDimension;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;

/**
 * Static Implementation of Breinify activity & lookup calls
 */
public class Breinify {

    /**
     * contains the current version of the usage library
     */
    private static final String VERSION = "1.1.0";

    /**
     * contains the configuration
     */
    private static BreinConfig config;

    /**
     * contains the activity object
     */
    private static final BreinActivity breinActivity = new BreinActivity();

    /**
     * contains the lookup object
     */
    private static final BreinLookup breinLookup = new BreinLookup();

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
     * returns the  version
     *
     * @return version
     */
    public String getVersion() {
        return VERSION;
    }

    /**
     *
     * @return
     */
    public static BreinActivity getBreinActivity() {
        return breinActivity;
    }

    /**
     *
     * @return
     */
    public static BreinLookup getBreinLookup() {
        return breinLookup;
    }

    /**
     * Sends an activity to the engine utilizing the API. The call is done asynchronously as a POST request. It is
     * important that a valid API-key is configured prior to using this function.
     * <p>
     * This request is asynchronous.
     *
     * @param user         a plain object specifying the user information the activity belongs to
     * @param activityType the type of the activity collected, i.e., one of search, login, logout, addToCart,
     *                     removeFromCart, checkOut, selectProduct, or other. if not specified, the default other will
     *                     be used
     * @param category     the category of the platform/service/products, i.e., one of apparel, home, education, family,
     *                     food, health, job, services, or other
     * @param description  a string with further information about the activity performed
     * @param sign         a boolean value specifying if the call should be signed
     */
    public static void activity(final BreinUser user,
                                final BreinActivityType activityType,
                                final BreinCategoryType category,
                                final String description,
                                final boolean sign) {
        activity(breinActivity, user, activityType, category, description, sign);
    }

    /**
     * Sends an activity to the engine utilizing the API. The call is done asynchronously as a POST request. It is
     * important that a valid API-key is configured prior to using this function.
     * <p>
     * This request is asynchronous.
     *
     * @param breinActivity an activity object
     * @param user          a plain object specifying the user information the activity belongs to
     * @param activityType  the type of the activity collected, i.e., one of search, login, logout, addToCart,
     *                      removeFromCart, checkOut, selectProduct, or other. if not specified, the default other will
     *                      be used
     * @param category      the category of the platform/service/products, i.e., one of apparel, home, education, family,
     *                      food, health, job, services, or other
     * @param description   a string with further information about the activity performed
     * @param sign          a boolean value specifying if the call should be signed
     */
    public static void activity(final BreinActivity breinActivity,
                                final BreinUser user,
                                final BreinActivityType activityType,
                                final BreinCategoryType category,
                                final String description,
                                final boolean sign) {
        breinActivity.setBreinUser(user);
        breinActivity.setBreinActivityType(activityType);
        breinActivity.setBreinCategoryType(category);
        breinActivity.setDescription(description);
        breinActivity.setSign(sign);

        /*
         * invoke the request, "this" has all necessary information
         */
        if (null == breinActivity.getBreinEngine()) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }
        breinActivity.getBreinEngine().sendActivity(breinActivity);
    }

    /**
     * Sends an activity to the engine utilizing the API. The call is done asynchronously as a POST request. It is
     * important that a valid API-key is configured prior to using this function.
     * <p>
     * This request is asynchronous.
     *
     * @param breinActivity an activity object
     */
    public static void activity(final BreinActivity breinActivity) {

        if (breinActivity.getBreinUser() == null) {
            throw new BreinException(BreinException.USER_NOT_SET);
        }

        if (breinActivity.getBreinActivityType() == null) {
            throw new BreinException(BreinException.ACTIVITY_TYPE_NOT_SET);
        }

        if (breinActivity.getBreinCategoryType() == null) {
            throw new BreinException(BreinException.CATEGORY_TYPE_NOT_SET);
        }

        if (null == breinActivity.getBreinEngine()) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }

        breinActivity.getBreinEngine().sendActivity(breinActivity);
    }

    /**
     * Retrieves a lookup result from the engine. The function needs a valid API-key to be configured to succeed.
     * <p>
     * This request is synchronous.
     *
     * @param user      a plain object specifying information about the user to retrieve data for.
     * @param dimension an object (with an array) containing the names of the dimensions to lookup.
     * @param sign      a boolean value specifying if the call should be signed.
     *
     * @return response from request wrapped in an object called BreinResponse
     */
    public static BreinResult lookup(final BreinUser user,
                                     final BreinDimension dimension,
                                     final boolean sign) {
        return lookup(breinLookup, user, dimension, sign);
    }

    public static BreinResult lookup(final BreinLookup breinLookup,
                                     final BreinUser user,
                                     final BreinDimension dimension,
                                     final boolean sign) {
        breinLookup.setBreinUser(user);
        breinLookup.setBreinDimension(dimension);
        breinLookup.setSign(sign);

        /*
         * invoke the lookup request
         */
        if (null == breinLookup.getBreinEngine()) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }
        return breinLookup.getBreinEngine().performLookUp(breinLookup);
    }

    /**
     * Shutdown Breinify services
     */
    public static void shutdown() {
        if (getConfig() != null) {
            getConfig().shutdownEngine();
        }
    }

}
