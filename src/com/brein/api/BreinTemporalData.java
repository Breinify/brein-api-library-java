package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.BreinUser;
import com.brein.domain.results.BreinTemporalDataResult;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Provides the lookup functionality
 */
public class BreinTemporalData extends BreinBase<BreinTemporalData> implements IExecutable<BreinTemporalDataResult> {
    public static DateTimeFormatter JAVA_SCRIPT_FORMAT =
            DateTimeFormatter.ofPattern("E MMM dd yyyy HH:mm:ss \'GMT\'Z (z)");

    /*
     * The following fields are used within the additional
     */
    public final static String LOCATION_FIELD = "location";
    public final static String LOCAL_DATE_TIME_FIELD = "localDateTime";
    public final static String TIMEZONE_FIELD = "timezone";
    public final static String IP_ADDRESS_FIELD = "ipAddress";

    /*
     * The following fields are used within the location
     */
    public final static String LONGITUDE_FIELD = "longitude";
    public final static String LATITUDE_FIELD = "latitude";
    public final static String SHAPE_TYPES_FIELD = "shapeTypes";
    public final static String TEXT_FIELD = "text";

    public final static String CITY_TEXT_FIELD = "text";
    public final static String STATE_TEXT_FIELD = "text";
    public final static String COUNTRY_TEXT_FIELD = "text";

    @Override
    public String getEndPoint(final BreinConfig config) {
        return config.getTemporalDataEndpoint();
    }

    @Override
    public void prepareRequestData(final BreinConfig config, final Map<String, Object> requestData) {
        // nothing additionally to be added
    }

    public BreinTemporalData setTimezone(final TimeZone timezone) {
        return setTimezone(timezone == null ? null : timezone.getID());
    }

    public BreinTemporalData setTimezone(final String timezone) {
        setAdditional(TIMEZONE_FIELD, timezone);
        return this;
    }

    public BreinTemporalData setLookUpIpAddress(final String ipAddress) {
        setAdditional(IP_ADDRESS_FIELD, ipAddress);
        return this;
    }

    public BreinTemporalData setLongitude(final double longitude) {
        setLocation(LONGITUDE_FIELD, longitude);
        return this;
    }

    public BreinTemporalData setLatitude(final double latitude) {
        setLocation(LATITUDE_FIELD, latitude);
        return this;
    }

    public BreinTemporalData setShapeTypes(final String... shapeTypes) {
        setLocation(SHAPE_TYPES_FIELD, new ArrayList<>(Arrays.asList(shapeTypes)));
        return this;
    }

    public BreinTemporalData setLocation(final String freeText) {
        setLocation(TEXT_FIELD, freeText);
        return this;
    }

    /**
     * Sets the current localDateTime based on the system's time.
     *
     * @return {@code this}
     *
     * @see ZonedDateTime#now()
     */
    public BreinTemporalData setLocalDateTime() {
        return setLocalDateTime(ZonedDateTime.now());
    }

    public BreinTemporalData setLocalDateTime(final ZonedDateTime zonedDateTime) {
        setAdditional(LOCAL_DATE_TIME_FIELD, zonedDateTime.format(JAVA_SCRIPT_FORMAT));
        return this;
    }

    public BreinTemporalData setLocation(final String city, final String state, final String country) {
        setLocation(CITY_TEXT_FIELD, city);
        setLocation(STATE_TEXT_FIELD, state);
        setLocation(COUNTRY_TEXT_FIELD, country);
        return this;
    }

    public BreinTemporalData addShapeTypes(final String... shapeTypes) {
        if (shapeTypes == null || shapeTypes.length == 0) {
            return this;
        }

        List<String> list = getLocation(SHAPE_TYPES_FIELD);
        if (list == null) {
            list = new ArrayList<>();
            setLocation(SHAPE_TYPES_FIELD, list);
        }
        list.addAll(Arrays.asList(shapeTypes));

        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getLocation(final String key) {
        final Map<String, Object> location = getUser().getAdditional(LOCATION_FIELD);
        return location == null ? null : (T) location.get(key);
    }

    public BreinTemporalData setLocation(final String key, final Object value) {
        Map<String, Object> location = getUser().getAdditional(LOCATION_FIELD);
        if (location == null) {
            location = new HashMap<>();
            getUser().setAdditional(LOCATION_FIELD, location);
        }

        location.put(key, value);

        return this;
    }

    /**
     * Creates the signature for temporaldata
     *
     * @return signature
     */
    @Override
    public String createSignature(final BreinConfig config, final Map<String, Object> requestData) {
        final String localDateTime = BreinMapUtil.getNestedValue(requestData,
                BreinUser.USER_FIELD, BreinUser.ADDITIONAL_FIELD, LOCAL_DATE_TIME_FIELD);
        final String paraLocalDateTime = localDateTime == null ? "" : localDateTime;

        final String timeZone = BreinMapUtil.getNestedValue(requestData,
                BreinUser.USER_FIELD, BreinUser.ADDITIONAL_FIELD, TIMEZONE_FIELD);
        final String paraTimezone = timeZone == null ? "" : timeZone;

        final long unixTimestamp = BreinMapUtil.getNestedValue(requestData, UNIX_TIMESTAMP_FIELD);

        final String message = String.format("%d-%s-%s", unixTimestamp, paraLocalDateTime, paraTimezone);

        return BreinUtil.generateSignature(message, config.getSecret());
    }

    @Override
    public BreinTemporalDataResult execute() {
        return Breinify.temporalData(this);
    }
}
