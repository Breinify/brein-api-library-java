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

    public static Brein setConfig(final BreinConfig config) {
        lastConfig = config;
        return new Brein().setConfig(config);
    }

    public static Brein setConfig(final String apiKey) {
        return setConfig(apiKey, null);
    }

    public static Brein setConfig(final String apiKey, final String secret) {
        return setConfig(new BreinConfig(apiKey, secret));
    }

    public static void activity(final M<String> identifiers,
                                final String activityType) {
        activity(identifiers, activityType, null, null, null);
    }

    public static void activity(final M<String> identifiers,
                                final String activityType,
                                final Consumer<BreinResult> callback) {
        activity(identifiers, activityType, null, null, callback);
    }

    public static void activity(final M<String> identifiers,
                                final String activityType,
                                final String description) {
        activity(identifiers, activityType, null, description, null);
    }

    public static void activity(final M<String> identifiers,
                                final String activityType,
                                final String description,
                                final Consumer<BreinResult> callback) {
        activity(identifiers, activityType, null, description, callback);
    }

    public static void activity(final M<String> identifiers,
                                final String activityType,
                                final String category,
                                final String description,
                                final Consumer<BreinResult> callback) {
        final BreinUser user = new BreinUser();
        identifiers.forEach(user::set);

        activity(user, activityType, category, description, null);
    }

    public static void activity(final BreinUser user,
                                final String activityType) {
        activity(user, activityType, null, null, null);
    }

    public static void activity(final BreinUser user,
                                final String activityType,
                                final String category,
                                final String description) {
        activity(user, activityType, category, description, null);
    }

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

    public static void activity(final BreinActivity activity) {
        activity(activity, null);
    }

    public static void activity(final BreinActivity activity, final Consumer<BreinResult> callback) {
        getBrein().activity(activity, callback);
    }

    public static BreinTemporalDataResult temporalData() {
        final BreinTemporalData data = new BreinTemporalData().setLocalDateTime();

        return getBrein().temporalData(data);
    }

    public static BreinTemporalDataResult temporalData(final String ipAddress) {
        final BreinTemporalData data = new BreinTemporalData().setLookUpIpAddress(ipAddress);

        return getBrein().temporalData(data);
    }

    public static BreinTemporalDataResult temporalData(final double latitude,
                                                       final double longitude,
                                                       final String... shapeTypes) {
        final BreinTemporalData data = new BreinTemporalData()
                .setLongitude(longitude)
                .setLatitude(latitude)
                .addShapeTypes(shapeTypes);

        return getBrein().temporalData(data);
    }

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
