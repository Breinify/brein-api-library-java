package com.brein.api;

import com.brein.domain.BreinUser;
import com.brein.domain.results.BreinTemporalDataResult;
import com.brein.util.BreinUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides the lookup functionality
 */
public class BreinTemporalDataRequest extends BreinBase implements ISecretStrategy {

    /**
     * initializes the values of this instance
     */
    public void init() {
    }

    /**
     * resets all values of this class and base class to initial values. This will lead to empty strings or null
     * objects
     */
    public void resetAllValues() {
        // reset base values (User & Config)
        super.init();

        // reset init values
        init();
    }

    /**
     * TemporalData implementation. For a given user (BreinUser) a temporalData request will be performed.
     *
     * @return response from request or null if no data can be retrieved
     */
    public BreinTemporalDataResult execute() {
        if (getBreinEngine() == null) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }
        return new BreinTemporalDataResult(getBreinEngine().performTemporalDataRequest(this));
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
     * Used to create a clone of a temporal data object. This is important in order to prevent concurrency issues.
     *
     * @param source contains the original temporaldata object
     *
     * @return the clone of the temporaldata object
     */
    public static BreinTemporalDataRequest clone(final BreinTemporalDataRequest source) {

        // create a new activity object
        final BreinTemporalDataRequest temporalData = new BreinTemporalDataRequest();

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
