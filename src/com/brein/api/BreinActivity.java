package com.brein.api;

import com.brein.domain.BreinActivityType;
import com.brein.domain.BreinCategoryType;
import com.brein.domain.BreinUser;
import com.brein.util.BreinUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.time.Instant;

/**
 * Sends an activity to the engine utilizing the API.
 * The call is done asynchronously as a POST request.
 * It is important that a valid API-key is configured
 * prior to using this function.
 */
public class BreinActivity extends BreinBase {

    /**
     * ActivityType of the activity
     */
    private BreinActivityType breinActivityType;

    /**
     * Category of the activity
     */
    private BreinCategoryType breinCategoryType;

    /**
     * Description of the activity
     */
    private String description;

    /**
     * Sign
     * TODO: not really used yet
     */
    private boolean sign;

    /**
     * returns activity type
     *
     * @return activity type
     */
    public BreinActivityType getBreinActivityType() {
        return breinActivityType;
    }

    /**
     * Sets activity type
     *
     * @param breinActivityType to set
     */
    public void setBreinActivityType(final BreinActivityType breinActivityType) {
        this.breinActivityType = breinActivityType;
    }

    /**
     * retrieves brein category
     *
     * @return category object
     */
    public BreinCategoryType getBreinCategoryType() {
        return breinCategoryType;
    }

    /**
     * sets brein category
     *
     * @param breinCategoryType object
     */
    public void setBreinCategoryType(final BreinCategoryType breinCategoryType) {
        this.breinCategoryType = breinCategoryType;
    }

    /**
     * retrieves the description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description
     *
     * @param description string to set as description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * retrieves the sign flag
     *
     * @return flag (true / false)
     */
    public boolean isSign() {
        return sign;
    }

    /**
     * sets the sign flag
     *
     * @param sign value to set
     */
    public void setSign(final boolean sign) {
        this.sign = sign;
    }

    /**
     * retrieves the configured activity endpoint (e.g. \activitiy)
     *
     * @return endpoint
     */
    @Override
    public String getEndPoint() {
        return getConfig().getActivityEndpoint();
    }

    /**
     * Sends an activity to the Breinify server.
     *
     * @param breinUser         the user-information
     * @param breinActivityType the type of activity
     * @param breinCategoryType     the category (can be null or undefined)
     * @param description       the description for the activity
     * @param sign              true if a signature should be added (needs the secret to be configured -
     *                          not recommended in open systems), otherwise false (can be null or undefined)
     */
    public void activity(final BreinUser breinUser,
                         final BreinActivityType breinActivityType,
                         final BreinCategoryType breinCategoryType,
                         final String description,
                         final boolean sign) {

        /**
         * set the values for further usage
         */
        setBreinUser(breinUser);
        setBreinActivityType(breinActivityType);
        setBreinCategoryType(breinCategoryType);
        setDescription(description);
        setSign(sign);

        /**
         * invoke the request, "this" has all necessary information
         */
        if (null == getBreinEngine()) {
            throw new BreinException(BreinException.ENGINE_NOT_INITIALIZED);
        }
        getBreinEngine().sendActivity(this);
    }

    /**
     * creates the json request based on the necessary data
     *
     * @return json string
     */
    @Override
    public String prepareJsonRequest() {

        final long unixTimestamp = Instant.now().getEpochSecond();

        final JsonObject requestData = new JsonObject();

        /**
         * user data
         */
        final BreinUser breinUser = getBreinUser();
        if (breinUser != null) {

            final JsonObject userData = new JsonObject();
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
        final JsonObject activityData = new JsonObject();
        if (BreinUtil.containsValue(getBreinActivityType())) {
            activityData.addProperty("type", getBreinActivityType().getName());
        }
        if (BreinUtil.containsValue(getDescription())) {
            activityData.addProperty("description", getDescription());
        }
        if (BreinUtil.containsValue(getBreinCategoryType())) {
            activityData.addProperty("category", getBreinCategoryType().getName());
        }
        requestData.add("activity", activityData);

        /**
         * further data...
         */
        if (BreinUtil.containsValue(getConfig())) {
            if (BreinUtil.containsValue(getConfig().getApiKey())) {
                requestData.addProperty("apiKey", getConfig().getApiKey());
            }
        }

        requestData.addProperty("unixTimestamp", unixTimestamp);
        // requestData.addProperty("signatureType", breinActivity.getSignatureType));

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        return gson.toJson(requestData);
    }
}

