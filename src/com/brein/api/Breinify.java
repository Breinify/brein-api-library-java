package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.domain.results.BreinTemporalDataResult;
import com.brein.util.M;

import java.util.function.Consumer;

public class Breinify {
    private static BreinConfig lastConfig = null;
    private static Brein lastBrein = null;

    /**
     * Specifies the overall configuration used by the library. The configuration must be set prior to any call to the
     * API.
     *
     * @param config the configuration to use
     *
     * @return the {@code Brein} instance, usable if multiple different configurations are used
     *
     * @see Brein
     * @see BreinConfig
     */
    public static Brein setConfig(final BreinConfig config) {
        lastConfig = config;
        return new Brein().setConfig(config);
    }

    /**
     * Specifies the overall configuration used by the library. The configuration must be set prior to any call to the
     * API.
     *
     * @param apiKey the API key to be used
     *
     * @return the {@code Brein} instance, usable if multiple different configurations are used
     *
     * @see Brein
     */
    public static Brein setConfig(final String apiKey) {
        return setConfig(apiKey, null);
    }

    /**
     * Specifies the overall configuration used by the library. The configuration must be set prior to any call to the
     * API.
     *
     * @param apiKey the API key to be used
     * @param secret the secret to be used to sign the messages (Verification Signature must be enabled for the API
     *               key)
     *
     * @return the {@code Brein} instance, usable if multiple different configurations are used
     *
     * @see Brein
     */
    public static Brein setConfig(final String apiKey, final String secret) {
        return setConfig(new BreinConfig(apiKey, secret));
    }

    /**
     * Method to send an activity asynchronous.
     *
     * @param identifiers  the identifiers of the user as chainable map (i.e., {@code M}), e.g., email, sessionId,
     *                     userId
     * @param activityType the type of the activity, e.g., login, pageView, addToCart, readArticle
     *
     * @see M
     */
    public static void activity(final M<String> identifiers,
                                final String activityType) {
        activity(identifiers, activityType, null, null, null);
    }

    /**
     * Method to send an activity asynchronous.
     *
     * @param identifiers  the identifiers of the user as chainable map (i.e., {@code M}), e.g., email, sessionId,
     *                     userId
     * @param activityType the type of the activity, e.g., login, pageView, addToCart, readArticle
     * @param callback     callback to get informed whenever the activity was sent, the callback retrieves the {@code
     *                     BreinResult}
     *
     * @see BreinResult
     * @see M
     */
    public static void activity(final M<String> identifiers,
                                final String activityType,
                                final Consumer<BreinResult> callback) {
        activity(identifiers, activityType, null, null, callback);
    }

    /**
     * Method to send an activity asynchronous.
     *
     * @param identifiers  the identifiers of the user as chainable map (i.e., {@code M}), e.g., email, sessionId,
     *                     userId
     * @param activityType the type of the activity, e.g., login, pageView, addToCart, readArticle
     * @param description  a textual description of the activity
     *
     * @see M
     */
    public static void activity(final M<String> identifiers,
                                final String activityType,
                                final String description) {
        activity(identifiers, activityType, null, description, null);
    }

    /**
     * Method to send an activity asynchronous.
     *
     * @param identifiers  the identifiers of the user as chainable map (i.e., {@code M}), e.g., email, sessionId,
     *                     userId
     * @param activityType the type of the activity, e.g., login, pageView, addToCart, readArticle
     * @param description  a textual description of the activity
     * @param callback     callback to get informed whenever the activity was sent, the callback retrieves the {@code
     *                     BreinResult}, can be {@code null}
     *
     * @see BreinResult
     * @see M
     */
    public static void activity(final M<String> identifiers,
                                final String activityType,
                                final String description,
                                final Consumer<BreinResult> callback) {
        activity(identifiers, activityType, null, description, callback);
    }

    /**
     * Method to send an activity asynchronous.
     *
     * @param identifiers  the identifiers of the user as chainable map (i.e., {@code M}), e.g., email, sessionId,
     *                     userId
     * @param activityType the type of the activity, e.g., login, pageView, addToCart, readArticle
     * @param category     a category of the activity, e.g., apparel, food, or other, if {@code null} the configured
     *                     default category will be used
     * @param description  a textual description of the activity
     * @param callback     callback to get informed whenever the activity was sent, the callback retrieves the {@code
     *                     BreinResult}
     *
     * @see BreinResult
     * @see BreinConfig#setDefaultCategory(String)
     * @see M
     */
    public static void activity(final M<String> identifiers,
                                final String activityType,
                                final String category,
                                final String description,
                                final Consumer<BreinResult> callback) {
        final BreinUser user = new BreinUser();
        identifiers.forEach(user::set);

        activity(user, activityType, category, description, null);
    }

    /**
     * Method to send an activity asynchronous.
     *
     * @param user         the {@code BreinUser} instance specifying information about the user performing the activity
     * @param activityType the type of the activity, e.g., login, pageView, addToCart, readArticle
     *
     * @see BreinUser
     */
    public static void activity(final BreinUser user,
                                final String activityType) {
        activity(user, activityType, null, null, null);
    }

    /**
     * Method to send an activity asynchronous.
     *
     * @param user         the {@code BreinUser} instance specifying information about the user performing the activity
     * @param activityType the type of the activity, e.g., login, pageView, addToCart, readArticle
     * @param category     a category of the activity, e.g., apparel, food, or other, if {@code null} the configured
     *                     default category will be used
     * @param description  a textual description of the activity
     *
     * @see BreinUser
     * @see BreinConfig#setDefaultCategory(String)
     */
    public static void activity(final BreinUser user,
                                final String activityType,
                                final String category,
                                final String description) {
        activity(user, activityType, category, description, null);
    }

    /**
     * Method to send an activity asynchronous.
     *
     * @param user         the {@code BreinUser} instance specifying information about the user performing the activity
     * @param activityType the type of the activity, e.g., login, pageView, addToCart, readArticle
     * @param category     a category of the activity, e.g., apparel, food, or other, if {@code null} the configured
     *                     default category will be used
     * @param description  a textual description of the activity
     * @param callback     callback to get informed whenever the activity was sent, the callback retrieves the {@code
     *                     BreinResult}
     *
     * @see BreinUser
     * @see BreinConfig#setDefaultCategory(String)
     * @see BreinResult
     */
    public static void activity(final BreinUser user,
                                final String activityType,
                                final String category,
                                final String description,
                                final Consumer<BreinResult> callback) {
        if (user == null) {
            throw new BreinException(BreinException.USER_NOT_SET);
        }

        final BreinActivity activity = new BreinActivity()
                .setUser(user)
                .setActivityType(activityType)
                .setCategory(category)
                .setDescription(description);

        activity(activity, callback);
    }

    /**
     * Method to send an activity asynchronous.
     *
     * @param activity the {@code BreinActivity} to be sent
     *
     * @see BreinActivity
     */
    public static void activity(final BreinActivity activity) {
        activity(activity, null);
    }

    /**
     * Method to send an activity asynchronous.
     *
     * @param activity the {@code BreinActivity} to be sent
     * @param callback callback to get informed whenever the activity was sent, the callback retrieves the {@code
     *                 BreinResult}
     *
     * @see BreinActivity
     * @see BreinResult
     */
    public static void activity(final BreinActivity activity, final Consumer<BreinResult> callback) {
        getBrein().activity(activity, callback);
    }

    /**
     * Method to retrieve temporal information based on temporal data. This method uses the available information from
     * the system it is running on to be passed to the API, which resolves the temporal information. Normally (if not
     * using a VPN) the ip-address is a good source to determine, e.g., the location.
     *
     * @return the retrieved {@code BreinTemporalDataResult}
     *
     * @see BreinTemporalDataResult
     */
    public static BreinTemporalDataResult temporalData() {
        final BreinTemporalData data = new BreinTemporalData().setLocalDateTime();

        return getBrein().temporalData(data);
    }

    /**
     * Method to retrieve temporal information based on temporal data. This method uses the {@code ipAddress} to
     * determine further information, i.e., weather, location, events, time, timezone, and holidays.
     *
     * @param ipAddress the address to resolve the information for
     *
     * @return the retrieved {@code BreinTemporalDataResult}
     *
     * @see BreinTemporalDataResult
     */
    public static BreinTemporalDataResult temporalData(final String ipAddress) {
        final BreinTemporalData data = new BreinTemporalData().setLookUpIpAddress(ipAddress);

        return getBrein().temporalData(data);
    }

    /**
     * Method to retrieve temporal information based on temporal data. This method uses the {@code latitude} and {@code
     * longitude} to determine further information, i.e., weather, location, events, time, timezone, and holidays.
     *
     * @param latitude   the latitude of the geo-coordinates to resolve
     * @param longitude  the longitude of the geo-coordinates to resolve
     * @param shapeTypes the shape-types to retrieve (if empty, no shape-types will be returned), e.g., CITY,
     *                   NEIGHBORHOOD, ZIP-CODES
     *
     * @return the retrieved {@code BreinTemporalDataResult}
     *
     * @see BreinTemporalDataResult
     */
    public static BreinTemporalDataResult temporalData(final double latitude,
                                                       final double longitude,
                                                       final String... shapeTypes) {
        final BreinTemporalData data = new BreinTemporalData()
                .setLongitude(longitude)
                .setLatitude(latitude)
                .addShapeTypes(shapeTypes);

        return getBrein().temporalData(data);
    }

    /**
     * Method to retrieve temporal information based on temporal data. This method can be used to form any type of
     * request utilizing {@code BreinTemporalData}.
     *
     * @param data the data specifying the temporal data to retrieve the temporal information for
     *
     * @return the retrieved {@code BreinTemporalDataResult}
     *
     * @see BreinTemporalData
     * @see BreinTemporalDataResult
     */
    public static BreinTemporalDataResult temporalData(final BreinTemporalData data) {
        return getBrein().temporalData(data);
    }

    public static void shutdown() {
        if (lastBrein != null) {
            lastBrein.shutdown();
            lastBrein = null;
        }
    }

    protected static Brein getBrein() {
        if (lastBrein == null) {
            lastBrein = new Brein().setConfig(lastConfig);
        }

        return lastBrein;
    }

    public static BreinResult recommendation(final BreinRecommendation data) {
        return getBrein().recommendation(data);
    }

    public static BreinResult lookUp(final BreinLookup data) {
        return getBrein().lookup(data);
    }
}
