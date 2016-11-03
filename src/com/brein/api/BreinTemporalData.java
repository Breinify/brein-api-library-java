package com.brein.api;

import com.brein.domain.BreinBaseRequest;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.domain.BreinUserRequest;
import com.brein.util.BreinUtil;
import com.google.gson.JsonObject;

/**
 * Provides the lookup functionality
 */
public class BreinTemporalData extends BreinBase implements ISecretStrategy {

    /**
     * contains the data structures for the base part
     */
    private final BreinBaseRequest breinBaseRequest = new BreinBaseRequest();

    /**
     * contains the data structure for the user request part including additional
     */
    private final BreinUserRequest breinUserRequest = new BreinUserRequest();

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
     * @param sign      if set to true a secret will be sent as well
     * @return response from request or null if no data can be retrieved
     */
    public BreinResult temporalData(final BreinUser breinUser,
                                    final boolean sign) {

        setBreinUser(breinUser);
        setSign(sign);

        if (getBreinEngine() == null) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }

        return getBreinEngine().performTemporalDataRequest(this);
    }

    /**
     * prepares a JSON request for a lookup
     *
     * @return well formed json request
     */
    @Override
    public String prepareJsonRequest() {

        // call base class
        super.prepareJsonRequest();

        // user data level and additional
        final BreinUser breinUser = getBreinUser();
        final JsonObject requestData = new JsonObject();
        if (breinUser != null) {
            breinUserRequest.prepareUserRequestData(requestData, breinUser);
        }

        // base level data...
        breinBaseRequest.prepareBaseRequestData(this, requestData, isSign());

        return getGson().toJson(requestData);
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
