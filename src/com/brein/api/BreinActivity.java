package com.brein.api;

import com.brein.domain.BreinUser;
import com.brein.util.BreinUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Sends an activity to the engine utilizing the API. The call is done asynchronously as a POST request. It is important
 * that a valid API-key is configured prior to using this function.
 */
public class BreinActivity extends BreinBase implements ISecretStrategy {

    /**
     * ActivityType of the activity
     */
    private String breinActivityType;

    /**
     * Category of the activity
     */
    private String breinCategoryType;

    /**
     * Description of the activity
     */
    private String description;

    /**
     * contains the tags
     */
    private Map<String, Object> tagsMap;

    /**
     * returns activity type
     *
     * @return activity type
     */
    public String getBreinActivityType() {
        return breinActivityType;
    }

    /**
     * Sets activity type
     *
     * @param breinActivityType to set
     */
    public BreinActivity setBreinActivityType(final String breinActivityType) {
        this.breinActivityType = breinActivityType;
        return this;
    }

    /**
     * retrieves brein category. if it is empty or null then
     * the default category (if set) will be used.
     *
     * @return category object
     */
    public String getBreinCategoryType() {
        if (!BreinUtil.containsValue(breinCategoryType)) {
            // try default category
            return getConfig().getDefaultCategory();
        }
        return breinCategoryType;
    }

    /**
     * sets brein category
     *
     * @param breinCategoryType object
     */
    public BreinActivity setBreinCategoryType(final String breinCategoryType) {
        this.breinCategoryType = breinCategoryType;
        return this;
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
    public BreinActivity setDescription(final String description) {
        this.description = description;
        return this;
    }

    /**
     * retrieves the configured activity endpoint (e.g. \activity)
     *
     * @return endpoint
     */
    @Override
    public String getEndPoint() {
        return getConfig().getActivityEndpoint();
    }

    /**
     * retrieves the tagMap
     *
     * @return value
     */
    public Map<String, Object> getTagsMap() {
        return tagsMap;
    }

    /**
     * sets the tagsMap
     *
     * @param tagsMap created map (e.g. HashMap)
     * @return self
     */
    public BreinActivity setTagsMap(final Map<String, Object> tagsMap) {
        this.tagsMap = tagsMap;
        return this;
    }

    /**
     * initializes the values of this instance
     */
    public void init() {
        breinActivityType = "";
        breinCategoryType = "";
        description = "";
        tagsMap = null;
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
     * Sends an activity to the Breinify server.
     *
     * @param breinUser         the user-information
     * @param breinActivityType the type of activity
     * @param breinCategoryType the category (can be null or undefined)
     * @param description       the description for the activity
     * @param sign              true if a signature should be added (needs the secret to be configured - not recommended
     *                          in open systems), otherwise false (can be null or undefined)
     */
    public void activity(final BreinUser breinUser,
                         final String breinActivityType,
                         final String breinCategoryType,
                         final String description,
                         final boolean sign) {

        // set the values for further usage
        setBreinUser(breinUser);
        setBreinActivityType(breinActivityType);
        setBreinCategoryType(breinCategoryType);
        setDescription(description);
        setSign(sign);

        /*
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

        // call base class
        super.prepareJsonRequest();

        final JsonObject requestData = new JsonObject();

        // user data
        final BreinUser breinUser = getBreinUser();
        if (breinUser != null) {

            final JsonObject userData = new JsonObject();
            if (BreinUtil.containsValue(breinUser.getEmail())) {
                userData.addProperty("email", breinUser.getEmail());
            }

            if (BreinUtil.containsValue(breinUser.getFirstName())) {
                userData.addProperty("firstName", breinUser.getFirstName());
            }

            if (BreinUtil.containsValue(breinUser.getLastName())) {
                userData.addProperty("lastName", breinUser.getLastName());
            }

            if (BreinUtil.containsValue(breinUser.getDateOfBirth())) {
                userData.addProperty("dateOfBirth", breinUser.getDateOfBirth());
            }

            if (BreinUtil.containsValue(breinUser.getDeviceId())) {
                userData.addProperty("deviceId", breinUser.getDeviceId());
            }

            if (BreinUtil.containsValue(breinUser.getImei())) {
                userData.addProperty("imei", breinUser.getImei());
            }

            if (BreinUtil.containsValue(breinUser.getSessionId())) {
                userData.addProperty("sessionId", breinUser.getSessionId());
            }

            // additional part
            final JsonObject additional = new JsonObject();
            if (BreinUtil.containsValue(breinUser.getUserAgent())) {
                additional.addProperty("userAgent", breinUser.getUserAgent());
            }

            if (BreinUtil.containsValue(breinUser.getReferrer())) {
                additional.addProperty("referrer", breinUser.getReferrer());
            }

            if (BreinUtil.containsValue(breinUser.getUrl())) {
                additional.addProperty("url", breinUser.getUrl());
            }

            if (BreinUtil.containsValue(breinUser.getIpAddress())) {
                additional.addProperty("ipAddress", breinUser.getIpAddress());
            }

            if (additional.size() > 0) {
                userData.add("additional", additional);
            }

            requestData.add("user", userData);
        }

        /*
         * activity data
         */
        final JsonObject activityData = new JsonObject();
        if (BreinUtil.containsValue(getBreinActivityType())) {
            activityData.addProperty("type", getBreinActivityType());
        }
        if (BreinUtil.containsValue(getDescription())) {
            activityData.addProperty("description", getDescription());
        }
        if (BreinUtil.containsValue(getBreinCategoryType())) {
            activityData.addProperty("category", getBreinCategoryType());
        }

        // tags
        final Map<String, Object> tagsMap = getTagsMap();
        if (tagsMap != null && tagsMap.size() > 0) {
            final JsonObject tagsData = new JsonObject();

            tagsMap.entrySet().forEach(entry -> {
                if (entry.getValue().getClass() == String.class) {
                    tagsData.addProperty(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue().getClass() == Double.class ||
                        entry.getValue().getClass() == Integer.class) {
                    tagsData.addProperty(entry.getKey(), (Number) entry.getValue());
                } else if (entry.getValue().getClass() == Boolean.class) {
                    tagsData.addProperty(entry.getKey(), (Boolean) entry.getValue());
                }
            });

            activityData.add("tags", tagsData);
        }

        requestData.add("activity", activityData);

        /*
         * further data...
         */
        if (BreinUtil.containsValue(getConfig())) {
            if (BreinUtil.containsValue(getConfig().getApiKey())) {
                requestData.addProperty("apiKey", getConfig().getApiKey());
            }
        }

        requestData.addProperty("unixTimestamp", getUnixTimestamp());

        // if sign is active
        if (isSign()) {
            requestData.addProperty("signatureType", createSignature());
        }

        final Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        return gson.toJson(requestData);
    }

    /**
     * Generates the signature for the request
     *
     * @return full signature
     */
    @Override
    public String createSignature() {

        final String message = String.format("%s%d%d",
                getBreinActivityType() == null ? "" : getBreinActivityType(),
                getUnixTimestamp(), 1);
        // activities.size());

        return BreinUtil.generateSignature(message, getConfig().getSecret());
    }
}

