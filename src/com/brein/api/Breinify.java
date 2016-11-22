package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.BreinDimension;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.util.BreinUtil;

import java.util.function.Function;

/**
 * Static Implementation of Breinify activity & lookup calls
 */
public class Breinify {

    /**
     * contains the current version of the usage library
     */
    private static final String VERSION = "1.2.0";

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
     * contains the temporaldata object
     */
    private static final BreinTemporalData breinTemporalData = new BreinTemporalData();

    /**
     * sets the configuration
     *
     * @param breinConfig config object
     */
    public static void setConfig(final BreinConfig breinConfig) {
        config = breinConfig;
        breinActivity.setConfig(breinConfig);
        breinLookup.setConfig(breinConfig);
        breinTemporalData.setConfig(breinConfig);
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
     * @return breinActivity instance
     */
    public static BreinActivity getBreinActivity() {
        return breinActivity;
    }

    /**
     * @return breinLookup instance
     */
    public static BreinLookup getBreinLookup() {
        return breinLookup;
    }

    /**
     * @return temporaldata instance
     */
    public static BreinTemporalData getBreinTemporalData() {
        return breinTemporalData;
    }

    /**
     * Sends an activity to the engine utilizing the API. The call is done asynchronously as a POST request. It is
     * important that a valid API-key is configured prior to using this function.
     * <p>
     * This request is asynchronous.
     *
     * @param user          a plain object specifying the user information the activity belongs to
     * @param activityType  the type of the activity collected, i.e., one of search, login, logout, addToCart,
     *                      removeFromCart, checkOut, selectProduct, or other. if not specified, the default other will
     *                      be used
     * @param categoryType  the category of the platform/service/products, i.e., one of apparel, home, education, family,
     *                      food, health, job, services, or other
     * @param description   a string with further information about the activity performed
     * @param sign          a boolean value specifying if the call should be signed
     * @param errorCallback a callback function that is invoked in case of an error (can be null)
     */
    public static void activity(final BreinUser user,
                                final String activityType,
                                final String categoryType,
                                final String description,
                                final boolean sign,
                                final Function<String, Void> errorCallback) {

        if (user == null) {
            throw new BreinException(BreinException.USER_NOT_SET);
        }

        breinActivity.setBreinUser(user);
        breinActivity.setBreinActivityType(activityType);
        breinActivity.setBreinCategoryType(categoryType);
        breinActivity.setDescription(description);
        breinActivity.setSign(sign);

        // invoke the request, "this" has all necessary information
        if (breinActivity.getBreinEngine() == null) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }

        breinActivity.setErrorCallback(errorCallback);
        breinActivity.getBreinEngine().sendActivity(breinActivity);
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
     * @param categoryType the category of the platform/service/products, i.e., one of apparel, home, education, family,
     *                     food, health, job, services, or other
     * @param description  a string with further information about the activity performed
     * @param sign         a boolean value specifying if the call should be signed
     */
    public static void activity(final BreinUser user,
                                final String activityType,
                                final String categoryType,
                                final String description,
                                final boolean sign) {

        // default behaviour is no callback
        activity(user, activityType, categoryType, description, sign, null);
    }

    /**
     * Sends an activity to the engine utilizing the API. The call is done asynchronously as a POST request. It is
     * important that a valid API-key is configured prior to using this function.
     * Furthermore it uses the internal instance of BreinActivity. In order to use this method correctly you have
     * to do the following:
     * <p>
     * // retrieve BreinActivity instance from Breinify class
     * BreinActivity breinActivity = Breinify.getBreinActivity();
     * <p>
     * // set methods as desired to breinActivity (for instance)
     * breinActivity.setBreinUser(new BreinUser("user.name@email.com");
     * ...
     * <p>
     * // invoke this method
     * Breinify.activity();
     * <p>
     * <p>
     * This request is asynchronous.
     */
    public static void activity() {

        // use the own instance
        final BreinActivity breinActivity = getBreinActivity();

        if (breinActivity.getBreinUser() == null) {
            throw new BreinException(BreinException.USER_NOT_SET);
        }

        if (breinActivity.getBreinActivityType() == null) {
            throw new BreinException(BreinException.ACTIVITY_TYPE_NOT_SET);
        }

        if (breinActivity.getBreinCategoryType() == null) {
            // check if there is an default category set
            final String defaultCategory = getConfig().getDefaultCategory();
            if (BreinUtil.containsValue(defaultCategory)) {
                breinActivity.setBreinCategoryType(defaultCategory);
            }
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
     * @return response from request wrapped in an object called BreinResponse
     */
    public static BreinResult lookup(final BreinUser user,
                                     final BreinDimension dimension,
                                     final boolean sign) {
        return lookup(breinLookup, user, dimension, sign);
    }

    /**
     * Retrieves a lookup result from the engine. The function needs a valid API-key to be configured to succeed.
     * <p>
     * This request is synchronous.
     *
     * @param breinLookup  a plain object specifying the lookup information.
     * @param user         a plain object specifying information about the user to retrieve data for.
     * @param dimension    an object (with an array) containing the names of the dimensions to lookup.
     * @param sign         a boolean value specifying if the call should be signed.
     * @return response from request wrapped in an object called BreinResponse
     */
    public static BreinResult lookup(final BreinLookup breinLookup,
                                     final BreinUser user,
                                     final BreinDimension dimension,
                                     final boolean sign) {

        if (user == null) {
            throw new BreinException(BreinException.USER_NOT_SET);
        }

        if (dimension == null) {
            throw new BreinException("Dimension not set");
        }

        if (breinLookup == null) {
            throw new BreinException("BreinLookup object is null");
        }

        breinLookup.setBreinUser(user);
        breinLookup.setBreinDimension(dimension);
        breinLookup.setSign(sign);

        // invoke the lookup request
        if (breinLookup.getBreinEngine() == null) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }
        return breinLookup.getBreinEngine().performLookUp(breinLookup);
    }

    /**
     * Sends a temporalData to the engine utilizing the API. The call is done synchronously as a POST request. It is
     * important that a valid API-key is configured prior to using this function.
     *
     * Furthermore it uses the internal instance of BreinTemporalData.
     *
     * @param user a plain object specifying information about the user to retrieve data for.
     * @param sign a boolean value specifying if the call should be signed.
     * @return result from the Breinify engine
     */
    public static BreinResult temporalData(final BreinUser user,
                                           final boolean sign) {

        return temporalData(breinTemporalData, user, sign);
    }

    /**
     * Sends a temporalData to the engine utilizing the API. The call is done synchronously as a POST request. It is
     * important that a valid API-key is configured prior to using this function.
     *
     * @param breinTemporalData a plain object specifying the information of the temporaldata.
     * @param user a plain object specifying information about the user to retrieve data for.
     * @param sign a boolean value specifying if the call should be signed.
     * @return result from the Breinify engine
     */
    public static BreinResult temporalData(final BreinTemporalData breinTemporalData,
                                           final BreinUser user,
                                           final boolean sign) {

        if (user == null) {
            throw new BreinException(BreinException.USER_NOT_SET);
        }

        if (breinTemporalData == null) {
            throw new BreinException("BreinTemporalData object not set");
        }

        breinTemporalData.setBreinUser(user);
        breinTemporalData.setSign(sign);

        // invoke the temporaldata request
        if (breinTemporalData.getBreinEngine() == null) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }
        return breinTemporalData.getBreinEngine().performTemporalDataRequest(breinTemporalData);
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
