package com.brein.api;

import com.brein.domain.BreinActivityType;
import com.brein.domain.BreinCategoryType;
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
     * contains the ipAddress
     */
    private String ipAddress;

    /**
     * user sessionId
     */
    private String sessionId;

    /**
     * contains the userAgent in additional part
     */
    private String userAgent;

    /**
     * contains the referrer in additional part
     */
    private String referrer;

    /**
     * contains the url in additional part
     */
    private String additionalUrl;

    /**
     * contains the tags
     */
    private Map<String, String> tagsMap;

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
    public BreinActivity setBreinActivityType(final BreinActivityType breinActivityType) {
        this.breinActivityType = breinActivityType;
        return this;
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
    public BreinActivity setBreinCategoryType(final BreinCategoryType breinCategoryType) {
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
     * retrieves the configured activity endpoint (e.g. \activitiy)
     *
     * @return endpoint
     */
    @Override
    public String getEndPoint() {
        return getConfig().getActivityEndpoint();
    }

    /**
     * IpAddress
     * @return configured ipaddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Set the ipAddress
     * @param ipAddress value of the ipaddress
     */
    public BreinActivity setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    /**
     * retrieves the session id
     * @return id of the session
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * sets the sessionid
     * @param sessionId id of the session
     * @return this -> allows chaining
     */
    public BreinActivity setSessionId(final String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    /**
     * retrieves the addtional userAgent value
     * @return value
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * sets the additional user agent value
     * @param userAgent value
     */
    public BreinActivity setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    /**
     * retrieves the additional referrer value
     * @return value
     */
    public String getReferrer() {
        return referrer;
    }

    /**
     * sets the additional referrer value
     * @param referrer value
     */
    public BreinActivity setReferrer(final String referrer) {
        this.referrer = referrer;
        return this;
    }

    /**
     * retrieves the additional url
     * @return value
     */
    public String getAdditionalUrl() {
        return additionalUrl;
    }

    /**
     * sets the additional url
     * @param additionalUrl value
     * @return self
     */
    public BreinActivity setAdditionalUrl(final String additionalUrl) {
        this.additionalUrl = additionalUrl;
        return this;
    }

    /**
     * retrieves the tagMap
     * @return value
     */
    public Map<String, String> getTagsMap() {
        return tagsMap;
    }

    /**
     * sets the tagsMap
     * @param tagsMap created map (e.g. HashMap)
     * @return self
     */
    public BreinActivity setTagsMap(final Map<String, String> tagsMap) {
        this.tagsMap = tagsMap;
        return this;
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
                         final BreinActivityType breinActivityType,
                         final BreinCategoryType breinCategoryType,
                         final String description,
                         final boolean sign) {

        /*
         * set the values for further usage
         */
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

        /*
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

            if (BreinUtil.containsValue(breinUser.getDateOfBirth())) {
                userData.addProperty("dateOfBirth", breinUser.getDateOfBirth());
            }

            if (BreinUtil.containsValue(breinUser.getDeviceId())) {
                userData.addProperty("deviceId", breinUser.getDeviceId());
            }

            if (BreinUtil.containsValue(breinUser.getImei())) {
                userData.addProperty("imei", breinUser.getImei());
            }

            if (BreinUtil.containsValue(getSessionId())) {
                userData.addProperty("sessionId", getSessionId());
            }

            // additional part
            final JsonObject additional = new JsonObject();
            if (BreinUtil.containsValue(getUserAgent())) {
                additional.addProperty("userAgent", getUserAgent());
            }

            if (BreinUtil.containsValue(getReferrer())) {
                additional.addProperty("referrer", getReferrer());
            }

            if (BreinUtil.containsValue(getAdditionalUrl())) {
                additional.addProperty("url", getAdditionalUrl());
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
            activityData.addProperty("type", getBreinActivityType().getName());
        }
        if (BreinUtil.containsValue(getDescription())) {
            activityData.addProperty("description", getDescription());
        }
        if (BreinUtil.containsValue(getBreinCategoryType())) {
            activityData.addProperty("category", getBreinCategoryType().getName());
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

        if (BreinUtil.containsValue(getIpAddress())) {
            requestData.addProperty("ipAddress", getIpAddress());
        }

        // tags
        final Map<String, String> tagsMap = getTagsMap();
        if (tagsMap != null && tagsMap.size() > 0) {
            final JsonObject tagsData = new JsonObject();
            tagsMap.forEach((key, value) -> tagsData.addProperty(key, value));
        }

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
                getBreinActivityType() == null ? "" : getBreinActivityType().getName(),
                getUnixTimestamp(), 1);
        // activities.size());

        return BreinUtil.generateSignature(message, getConfig().getSecret());
    }
}

