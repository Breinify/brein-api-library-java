package com.brein.api;

import com.brein.domain.BreinDimension;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.util.BreinUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Provides the lookup functionality
 */
public class BreinLookup extends BreinBase implements ISecretStrategy {

    /**
     * used for lookup request
     */
    private BreinDimension breinDimension;

    /**
     * retrieves the Brein dimension object
     *
     * @return object
     */
    public BreinDimension getBreinDimension() {
        return breinDimension;
    }

    /**
     * sets the breindimension object - will be used for lookup
     *
     * @param breinDimension object to set
     */
    public BreinLookup setBreinDimension(final BreinDimension breinDimension) {
        this.breinDimension = breinDimension;
        return this;
    }

    /**
     * initializes the values of this instance
     */
    public void init() {
        breinDimension = null;
    }

    /**
     * resets all values of this class and base class to initial values.
     * This will lead to empty strings or null objects
     */
    public void resetAllValues() {
        // reset init values
        init();

        // reset base values (User & Config)
        super.init();
    }

    /**
     * Lookup implementation. For a given user (BreinUser) a lookup will be performed with the requested dimensions
     * (BreinDimension)
     *
     * @param breinUser      contains the breinify user
     * @param breinDimension contains the dimensions to look after
     * @return response from request or null if no data can be retrieved
     */
    public BreinResult lookUp(final BreinUser breinUser,
                              final BreinDimension breinDimension) {

        setBreinUser(breinUser);
        setBreinDimension(breinDimension);

        if (null == getBreinEngine()) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }

        return getBreinEngine().performLookUp(this);
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

        final JsonObject requestData = new JsonObject();

        // user data level and additional
        final BreinUser breinUser = getBreinUser();
        if (null != breinUser) {
            breinUser.getBreinUserRequest().prepareUserRequestData(requestData, breinUser);
        }

        // this is the section that is only available within the lookup request
        if (BreinUtil.containsValue(getBreinDimension())) {
            final JsonObject lookupData = new JsonObject();
            final JsonArray dimensions = new JsonArray();
            for (final String field : getBreinDimension().getDimensionFields()) {
                dimensions.add(field);
            }
            if (getBreinDimension().getDimensionFields().length > 0) {
                lookupData.add("dimensions", dimensions);
                requestData.add("lookup", lookupData);
            }
        }

        // build base level structure
        getBreinBaseRequest().prepareBaseRequestData(this, requestData);

        return getGson().toJson(requestData);
    }

    /**
     * retrieves the configured lookup endpoint (e.g. \lookup)
     *
     * @return endpoint
     */
    @Override
    public String getEndPoint() {
        return getConfig().getLookupEndpoint();
    }

    /**
     * Creates the signature for lookup
     *
     * @return signature
     */
    @Override
    public String createSignature() {

        final String[] dimensions = getBreinDimension().getDimensionFields();

        // we need the first one
        final String message = String.format("%s%d%d",
                dimensions == null ? 0 : dimensions[0],
                getUnixTimestamp(),
                dimensions == null ? 0 : dimensions.length);

        return BreinUtil.generateSignature(message, getConfig().getSecret());
    }
}
