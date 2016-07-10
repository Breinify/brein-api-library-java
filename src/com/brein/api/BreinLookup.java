package com.brein.api;

import com.brein.domain.BreinDimension;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.util.BreinUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
     * retrieves the brein dimension object
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
    public void setBreinDimension(BreinDimension breinDimension) {
        this.breinDimension = breinDimension;
    }

    /**
     * Lookup implementation. For a given user (BreinUser) a lookup will be performed with the requested dimensions
     * (BreinDimension)
     *
     * @param breinUser      contains the breinify user
     * @param breinDimension contains the dimensions to look after
     *
     * @return response from request or null if no data can be retrieved
     */
    public BreinResult lookUp(final BreinUser breinUser,
                              final BreinDimension breinDimension,
                              final boolean sign) {

        setBreinUser(breinUser);
        setBreinDimension(breinDimension);

        // TODO: 04.07.16  sign consideration
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

        final JsonObject requestData = new JsonObject();
        final BreinUser breinUser = getBreinUser();
        if (breinUser != null) {
            JsonObject userData = new JsonObject();
            userData.addProperty("email", breinUser.getEmail());
            requestData.add("user", userData);
        }

        /*
         * Dimensions
         */
        if (BreinUtil.containsValue(getBreinDimension())) {
            final JsonObject lookupData = new JsonObject();
            final JsonArray dimensions = new JsonArray();
            for (String field : getBreinDimension().getDimensionFields()) {
                dimensions.add(field);
            }
            lookupData.add("dimensions", dimensions);
            requestData.add("lookup", lookupData);
        }

        /*
         * API key
         */
        if (BreinUtil.containsValue(getConfig().getApiKey())) {
            requestData.addProperty("apiKey", getConfig().getApiKey());
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        return gson.toJson(requestData);
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

    @Override
    public String createSignature() {
        return null;
    }
}
