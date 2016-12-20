package com.brein.api;

import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.util.BreinUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides the lookup functionality
 */
public class BreinTemporalData extends BreinBase implements ISecretStrategy {

    /**
     * initializes the values of this instance
     */
    public void init() {
    }

    /**
     * resets all values of this class and base class to initial values.
     * This will lead to empty strings or null objects
     */
    public void resetAllValues() {
        // reset base values (User & Config)
        super.init();

        // reset init values
        init();
    }

    /**
     * Lookup implementation. For a given user (BreinUser) a lookup will be performed with the requested dimensions
     * (BreinDimension)
     *
     * @param breinUser contains the breinify user
     * @return response from request or null if no data can be retrieved
     */
    public BreinResult temporalData(final BreinUser breinUser) {

        if (getBreinEngine() == null) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }

        setBreinUser(breinUser);

        return getBreinEngine().performTemporalDataRequest(this);
    }

    /**
     * prepares a JSON request for a temporalData
     *
     * @return well formed json request
     */
    @Override
    public String prepareJsonRequest() {

        // call base class
        super.prepareJsonRequest();

        // user data level and additional
        final BreinUser breinUser = getBreinUser();
        final Map<String, Object> requestData = new HashMap<>();
        if (breinUser != null) {
            breinUser.prepareUserRequestData(requestData, breinUser);
        }

        // base level data...
        prepareBaseRequestData(this, requestData);

        return getGson().toJson(requestData);
    }

    /**
     * Used to create a clone of a temporal data object. This is important in order to prevent
     * concurrency issues.
     *
     * @param source contains the original temporaldata object
     * @return the clone of the temporaldata object
     */
    public static BreinTemporalData clone(final BreinTemporalData source) {

        // create a new activity object
        final BreinTemporalData temporalData = new BreinTemporalData();

        // clone from base class
        temporalData.cloneBase(source);

        return temporalData;
    }

    /**
     * retrieves the configured temporaldata endpoint (e.g. \temporaldata)
     *
     * @return endpoint
     */
    @Override
    public String getEndPoint() {
        return getConfig().getTemporalDataEndpoint();
    }

    /**
     * Creates the signature for temporaldata
     *
     * @return signature
     */
    @Override
    public String createSignature() {
        final String localDateTime = getBreinUser().getLocalDateTime();
        final String paraLocalDateTime = localDateTime == null ? "" : localDateTime;

        final String timeZone = getBreinUser().getTimezone();
        final String paraTimezone = timeZone == null ? "" : timeZone;

        final String message = String.format("%d-%s-%s",
                getUnixTimestamp(), paraLocalDateTime, paraTimezone);

        return BreinUtil.generateSignature(message, getConfig().getSecret());
    }
}
