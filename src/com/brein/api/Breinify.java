package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.BreinDimension;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;

import java.util.Map;
import java.util.function.Function;

/**
 * Static Implementation of Breinify activity & lookup calls
 */
public class Breinify {

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
     * @param errorCallback a callback function that is invoked in case of an error (can be null)
     */
    public static void activity(final BreinUser user,
                                final String activityType,
                                final String categoryType,
                                final String description,
                                final Function<String, Void> errorCallback) {

        if (user == null) {
            throw new BreinException(BreinException.USER_NOT_SET);
        }

        if (breinActivity.getBreinEngine() == null) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }

        // apply the current configuration
        breinActivity.setBreinUser(user);
        breinActivity.setBreinActivityType(activityType);
        breinActivity.setBreinCategoryType(categoryType);
        breinActivity.setDescription(description);
        breinActivity.setErrorCallback(errorCallback);

        // create a clone in order to prevent concurrency issues
        final BreinActivity newActivity = Breinify.cloneActivity(breinActivity);
        newActivity.getBreinEngine().sendActivity(newActivity);
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
     */
    public static void activity(final BreinUser user,
                                final String activityType,
                                final String categoryType,
                                final String description) {

        // default behaviour is no callback
        activity(user, activityType, categoryType, description, null);
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

        // request is based on the static instance
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

        if (breinActivity.getBreinEngine() == null) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }

        // create a clone in order to prevent concurrency issues
        final BreinActivity newActivity = Breinify.cloneActivity(breinActivity);

        breinActivity.getBreinEngine().sendActivity(newActivity);
    }

    /**
     * Used to create a clone of an activity. This is important in order to prevent
     * concurrency issues.
     *
     * @param breinActivity contains the original activity object
     * @return the clone of the activity object
     */
    public static BreinActivity cloneActivity(final BreinActivity breinActivity) {

        // create a new activity object
        final BreinActivity activity = new BreinActivity()
                .setBreinActivityType(breinActivity.getBreinActivityType())
                .setBreinCategoryType(breinActivity.getBreinCategoryType())
                .setDescription(breinActivity.getDescription());

        // set further data...
        activity.setIpAddress(breinActivity.getIpAddress());
        activity.setUnixTimestamp(breinActivity.getUnixTimestamp());

        // callback
        activity.setErrorCallback(breinActivity.getErrorCallback());

        // configuration
        activity.setConfig(breinActivity.getConfig());

        // clone user
        final BreinUser clonedUser = BreinUser.clone(breinActivity.getBreinUser());
        activity.setBreinUser(clonedUser);

        // clone maps
        final Map<String, Object> activityMap = BreinMapUtil
                .copyMap(breinActivity.getActivityMap());
        activity.setActivityMap(activityMap);

        final Map<String, Object> tagsMapCopy = BreinMapUtil
                .copyMap(breinActivity.getTagsMap());
        activity.setTagsMap(tagsMapCopy);

        return activity;
    }

    /**
     * Retrieves a lookup result from the engine. The function needs a valid API-key to be configured to succeed.
     * <p>
     * This request is synchronous.
     *
     * @param user      a plain object specifying information about the user to retrieve data for.
     * @param dimension an object (with an array) containing the names of the dimensions to lookup.
     * @return response from request wrapped in an object called BreinResponse
     */
    public static BreinResult lookup(final BreinUser user,
                                     final BreinDimension dimension) {
        return lookup(breinLookup, user, dimension);
    }

    /**
     * Retrieves a lookup result from the engine. The function needs a valid API-key to be configured to succeed.
     * <p>
     * This request is synchronous.
     *
     * @param breinLookup a plain object specifying the lookup information.
     * @param user        a plain object specifying information about the user to retrieve data for.
     * @param dimension   an object (with an array) containing the names of the dimensions to lookup.
     * @return response from request wrapped in an object called BreinResponse
     */
    public static BreinResult lookup(final BreinLookup breinLookup,
                                     final BreinUser user,
                                     final BreinDimension dimension) {

        if (user == null) {
            throw new BreinException(BreinException.USER_NOT_SET);
        }

        if (dimension == null) {
            throw new BreinException("Dimension not set");
        }

        if (breinLookup == null) {
            throw new BreinException("BreinLookup object is null");
        }

        if (breinLookup.getBreinEngine() == null) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }

        breinLookup.setBreinUser(user);
        breinLookup.setBreinDimension(dimension);

        // invoke the lookup request
        return breinLookup.getBreinEngine().performLookUp(breinLookup);
    }

    /**
     * Sends a temporalData to the engine utilizing the API. The call is done synchronously as a POST request. It is
     * important that a valid API-key is configured prior to using this function.
     * <p>
     * Furthermore it uses the internal instance of BreinTemporalData.
     *
     * @param user a plain object specifying information about the user to retrieve data for.
     * @return result from the Breinify engine
     */
    public static BreinResult temporalData(final BreinUser user) {

        final BreinTemporalData breinTemporalData = new BreinTemporalData();
        breinTemporalData.setConfig(getConfig());

        // clone user
        final BreinUser clonedUser = BreinUser.clone(user);
        return temporalData(breinTemporalData, clonedUser);
    }

    /**
     * Sends a temporalData to the engine utilizing the API. The call is done synchronously as a POST request. It is
     * important that a valid API-key is configured prior to using this function.
     *
     * @param breinTemporalData a plain object specifying the information of the temporaldata.
     * @param user              a plain object specifying information about the user to retrieve data for.
     * @return result from the Breinify engine
     */
    public static BreinResult temporalData(final BreinTemporalData breinTemporalData,
                                           final BreinUser user) {

        if (user == null) {
            throw new BreinException(BreinException.USER_NOT_SET);
        }

        if (breinTemporalData == null) {
            throw new BreinException("BreinTemporalData object not set");
        }

        if (breinTemporalData.getBreinEngine() == null) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }

        breinTemporalData.setBreinUser(user);

        // invoke the temporaldata request
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
