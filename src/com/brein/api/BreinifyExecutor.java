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
public class BreinifyExecutor {

    /**
     * contains the current version of the library
     */
    private static final String VERSION = "1.0.0-snapshot";
    /**
     * contains the activity object
     */
    private final BreinActivity breinActivity = new BreinActivity();
    /**
     * contains the lookup object
     */
    private final BreinLookup breinLookup = new BreinLookup();
    /**
     * contains the configuration
     */
    private BreinConfig config;

    /**
     * gets the config
     *
     * @return config
     */
    public BreinConfig getConfig() {
        return config;
    }

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
     * returns the version
     *
     * @return version
     */
    public String getVersion() {
        return VERSION;
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
    public void activity(final BreinUser user,
                         final BreinActivityType activityType,
                         final BreinCategoryType category,
                         final String description,
                         final boolean sign) {
        Breinify.activity(breinActivity, user, activityType, category, description, sign);
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
    public BreinResult lookup(final BreinUser user,
                              final BreinDimension dimension,
                              final boolean sign) {
        return Breinify.lookup(breinLookup, user, dimension, sign);
    }

    /**
     * Shutdown Breinify services
     */
    public void shutdown() {
        this.config.shutdownEngine();
    }
}
