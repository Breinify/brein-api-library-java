package com.brein.domain;

import com.brein.api.BreinActivity;
import com.brein.util.BreinUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Creates a request for the breinify backend. Having the following format:
 * <p>
 * \"user\": {
 * \"email\": \"marco@wherever.com\",
 * \"firstName\": \"Marco\",
 * \"lastName\": \"Recchioni\",
 * \"dateOfBirth\": \"03/02/1967\",
 * \"sessionId\": \"Rg3vHJZnehYLjVg7qi3bZjzg\",
 * \"deviceId\": \"f07a13984f6d116a\",
 * \"imei\": \"990000862471854\"
 * },
 * <p>
 * \"activity\": {
 * \"type\": \"login\",
 * \"description\": \"login to amazon\",
 * \"category\": \"food, recipe, valid customer\"
 * },
 * <p>
 * \"apiKey\": \"5d8b-064c-f007-4f92-a8dc-d06b-56b4-fad8\",
 * \"unixTimestamp\": 1451962516,
 * \"signatureType\": \"HmacSHA256\"
 * }
 */
public class BreinRequest {

    /**
     * contains the activity
     */
    private final BreinActivity breinActivity;

    /**
     * contains the unixtimestamp
     */
    private final long unixTimeStamp;

    /**
     * Contains all data for the request body
     *
     * @param breinActivity activity data
     * @param unixTimeStamp timestamp
     */
    public BreinRequest(final BreinActivity breinActivity,
                        final long unixTimeStamp) {
        this.breinActivity = breinActivity;
        this.unixTimeStamp = unixTimeStamp;
    }

    /**
     * converts request to json data structure
     *
     * @return string in json format
     */
    public String toJson() {

        if (breinActivity != null) {
            JsonObject requestData = new JsonObject();

            /**
             * user data
             */
            final BreinUser breinUser = breinActivity.getBreinUser();
            if (breinUser != null) {

                JsonObject userData = new JsonObject();
                userData.addProperty("email", breinUser.getEmail());


                if (BreinUtil.containsValue(breinUser.getFirstName())) {
                    userData.addProperty("firstName", breinUser.getFirstName());
                }

                if (BreinUtil.containsValue(breinUser.getLastName())) {
                    userData.addProperty("lastName", breinUser.getLastName());
                }
                requestData.add("user", userData);
            }

            /**
             * activity data
             */
            JsonObject activityData = new JsonObject();
            activityData.addProperty("type", breinActivity.getBreinActivityType().toString());
            activityData.addProperty("description", breinActivity.getDescription());
            activityData.addProperty("category", breinActivity.getBreinCategory().getCategory());

            requestData.add("activity", activityData);

            /**
             * further data...
             */
            requestData.addProperty("apiKey", breinActivity.getBreinConfig().getApiKey());
            requestData.addProperty("unixTimestamp", unixTimeStamp);
            // requestData.addProperty("signatureType", breinActivity.getSignatureType));

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create();

            return gson.toJson(requestData);
        }
        return "";
    }

}
