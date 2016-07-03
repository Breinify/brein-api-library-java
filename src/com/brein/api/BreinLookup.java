package com.brein.api;

import com.brein.domain.BreinDimension;
import com.brein.domain.BreinResponse;
import com.brein.domain.BreinUser;
import com.brein.util.BreinUtil;
import com.google.gson.*;

/**
 * Provides the lookup functionality
 */
public class BreinLookup extends BreinBase {

    /**
     * used for lookup request
     */
    private BreinDimension breinDimension;

    /**
     * retrieves the brein dimension object
     * @return object
     */
    public BreinDimension getBreinDimension() {
        return breinDimension;
    }

    /**
     * sets the breindimension object - will be used for lookup
     * @param breinDimension object to set
     */
    public void setBreinDimension(BreinDimension breinDimension) {
        this.breinDimension = breinDimension;
    }

    /**
     * Lookup implementation. For a given user (BreinUser) a lookup
     * will be performed with the requested dimensions (BreinDimension)
     *
     * @param breinUser      contains the breinify user
     * @param breinDimension contains the dimensions to look after
     *
     * @return response from request or null if no data can be retrieved
     */
    public BreinResponse lookUp(final BreinUser breinUser,
                                final BreinDimension breinDimension) {

        setBreinUser(breinUser);
        setBreinDimension(breinDimension);

        return getBreinEngine().performLookUp(this);
    }

    /**
     * prepares a JSON request for a lookup
     *
     * @return well formed json request
     */
    public String prepareJsonRequest() {

        JsonObject requestData = new JsonObject();

        /**
         * user data
         * "user": {
               "email": "m.recchioni@me.com"
           },
         */
        final BreinUser breinUser = getBreinUser();
        if (breinUser != null) {
            JsonObject userData = new JsonObject();
            userData.addProperty("email", breinUser.getEmail());

            requestData.add("user", userData);
        }

        /**
         * Dimensions
         * "lookup": {
                "dimensions": ["firstname", "gender", "age", "agegroup", "digitalfootprint", "images"]
         },
         */
        if (BreinUtil.containsValue(getBreinDimension())) {
            JsonObject lookupData = new JsonObject();

            JsonArray dimensions = new JsonArray();
            for (String field : getBreinDimension().getDimensionFields()) {
                dimensions.add(field);
            }
            lookupData.add("dimensions", dimensions);

            requestData.add("lookup", lookupData);
        }

        /**
         * API key
         */
        if (BreinUtil.containsValue(getConfig())) {
            requestData.addProperty("apiKey", getConfig().getApiKey());
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        return gson.toJson(requestData);
    }

}
