package com.brein.api;

import com.brein.domain.BreinUser;
import com.brein.util.BreinUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Sends an activity to the engine utilizing the API. The call is done asynchronously as a POST request. It is important
 * that a valid API-key is configured prior to using this function.
 */
public class BreinActivity extends BreinBase implements ISecretStrategy {

    /**
     * contains the map kind for the extra maps
     */
    enum MapKind {
        MK_BASE,
        MK_USER,
        MK_ACTIVITY,
        MK_USER_ADDITIONAL
    }

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
     * contains the tags map
     */
    private Map<String, Object> tagsMap;

    /**
     * contains the extra maps that are not
     * handled by the current dedicated setter and getter
     *
     * extraBaseMap     -> root (base) level
     * extraActivityMap -> activity level
     * extraUserMap     -> user level
     * extra UserAdditionalMap -> user-additional level
     */
    private Map<String, Object> extraBaseMap;
    private Map<String, Object> extraActivityMap;
    private Map<String, Object> extraUserMap;
    private Map<String, Object> extraUserAdditionalMap;

    /**
     * contains the function map for user data related functions
     */
    private Map<String, CheckFunction> requestUserDataFunctions = null;

    /**
     * contains the function map for user additional data related functions
     */
    private Map<String, CheckFunction> requestUserAdditionalDataFunctions = null;

    /**
     *  contains the function map for activity data related functions
     */
    private Map<String, CheckFunction> requestActivityFunctions = null;

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
     * Returns the extra map on each level (base, activity, user, additional)
     * @param kind contains the level
     * @return map of data to be added to the json structure
     */
    public Map<String, Object> getExtraMap(final MapKind kind) {

        switch (kind) {
            case MK_BASE:
                return extraBaseMap;
            case MK_ACTIVITY:
                return extraActivityMap;
            case MK_USER:
                return extraUserMap;
            case MK_USER_ADDITIONAL:
                return extraUserAdditionalMap;
            default:
                return null;
        }
    }

    /**
     * sets the extra map according to the level
     *
     * @param kind determines the level
     * @param extraMap contains the extra map
     * @return this
     */
    public BreinActivity setExtraMap(final MapKind kind, final Map<String, Object> extraMap) {

        if (kind == MapKind.MK_BASE) {
            extraBaseMap = extraMap;
        } else if (kind == MapKind.MK_ACTIVITY) {
            extraActivityMap = extraMap;
        } else if (kind == MapKind.MK_USER) {
            extraUserMap = extraMap;
        } else if (kind == MapKind.MK_USER_ADDITIONAL) {
            extraUserAdditionalMap = extraMap;
        }
        return this;
    }

    /**
     * initializes the values of this instance
     */
    @Override
    public void init() {
        breinActivityType = "";
        breinCategoryType = "";
        description = "";
        tagsMap = null;
        extraBaseMap = null;
        extraActivityMap = null;
        extraUserMap = null;
        extraUserAdditionalMap = null;
        requestUserDataFunctions = null;
        requestUserAdditionalDataFunctions = null;
        requestActivityFunctions = null;
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

        // invoke the request, "this" has all necessary information
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

        // call base class for base data
        super.prepareJsonRequest();

        // user data level and additional
        final BreinUser breinUser = getBreinUser();
        final JsonObject requestData = new JsonObject();
        if (breinUser != null) {
            final JsonObject userData = new JsonObject();
            prepareUserRequestData(requestData, breinUser, userData);
        }

        // activity data
        final JsonObject activityData = new JsonObject();
        prepareActivityRequestData(activityData);
        requestData.add("activity", activityData);

        // base level data...
        prepareBaseRequestData(requestData);

        final Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        return gson.toJson(requestData);
    }

    /**
     * Prepares the base request data
     *
     * @param requestData json structure
     */
    public void prepareBaseRequestData(final JsonObject requestData) {

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

        // check if there are further extra maps to add on base level
        if (extraBaseMap != null && extraBaseMap.size() > 0) {
            fillMap(extraBaseMap, requestData);
        }
    }

    /**
     * Prepares the activity json structure
     * @param activityData contains the created data structure
     */
    public void prepareActivityRequestData(final JsonObject activityData) {

        if (requestActivityFunctions == null) {
            requestActivityFunctions = new HashMap<>();

            // configure the map
            configureRequestActivityFunctionMap();
        }

        // execute the map
        executeMapFunctions(activityData, requestActivityFunctions);

        // add tags map if configured
        if (tagsMap != null && tagsMap.size() > 0) {
            final JsonObject tagsData = new JsonObject();
            fillMap(tagsMap, tagsData);
            activityData.add("tags", tagsData);
        }

        // check if there are further extra maps to add on base level
        if (extraActivityMap != null && extraActivityMap.size() > 0) {
            fillMap(extraActivityMap, activityData);
        }
    }

    /**
     * configures the activity related functions
     */
    public void configureRequestActivityFunctionMap() {
        requestActivityFunctions.put("type", this::getBreinActivityType);
        requestActivityFunctions.put("description", this::getDescription);
        requestActivityFunctions.put("category", this::getBreinCategoryType);
    }

    /**
     * Generic method to add an value from a data map if it contains a valid value
     *
     * @param dataMap map of data
     * @param jsonData request
     */
    public void fillMap(final Map<String, Object> dataMap,
                        final JsonObject jsonData) {

        dataMap.entrySet().forEach(entry -> {
            if (entry.getValue().getClass() == String.class) {
                jsonData.addProperty(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue().getClass() == Double.class ||
                    entry.getValue().getClass() == Integer.class) {
                jsonData.addProperty(entry.getKey(), (Number) entry.getValue());
            } else if (entry.getValue().getClass() == Boolean.class) {
                jsonData.addProperty(entry.getKey(), (Boolean) entry.getValue());
            }
        });
    }

    /**
     * Prepares the request on user level
     * @param requestData contains the json request that is generated (top level)
     * @param breinUser contains the brein user data
     * @param userData contains the json request on user level
     */
    public void prepareUserRequestData(final JsonObject requestData,
                                       final BreinUser breinUser,
                                       final JsonObject userData) {

        // do it once
        if (requestUserDataFunctions == null) {
            requestUserDataFunctions = new HashMap<>();
            configureRequestUserDataFunctionMap(breinUser);
        }

        // Execute the functions and add it to userData
        executeMapFunctions(userData, requestUserDataFunctions);

        // check if there are further extra maps to add on user level
        if (extraUserMap != null && extraUserMap.size() > 0) {
            fillMap(extraUserMap, userData);
        }

        // additional part
        if (requestUserAdditionalDataFunctions == null) {
            requestUserAdditionalDataFunctions = new HashMap<>();
            configureRequestUserAdditionalFunctionMap(breinUser);
        }

        // additional part
        final JsonObject additional = new JsonObject();

        // Execute the functions and add it to userData
        executeMapFunctions(additional, requestUserAdditionalDataFunctions);

        // check if there are further extra maps to add on user additional level
        if (extraUserAdditionalMap != null && extraUserAdditionalMap.size() > 0) {
            fillMap(extraUserAdditionalMap, additional);
        }

        if (additional.size() > 0) {
            userData.add("additional", additional);
        }

        // add the data
        requestData.add("user", userData);
    }

    /**
     * configures the user additional part
     * @param breinUser contains the brein user
     */
    public void configureRequestUserAdditionalFunctionMap(final BreinUser breinUser) {
        requestUserAdditionalDataFunctions.put("userAgent", breinUser::getUserAgent);
        requestUserAdditionalDataFunctions.put("referrer", breinUser::getReferrer);
        requestUserAdditionalDataFunctions.put("url", breinUser::getUrl);
        requestUserAdditionalDataFunctions.put("ipAddress", breinUser::getIpAddress);
    }

    /**
     * configures the user data fucntion map
     * @param breinUser contains the brein user
     */
    public void configureRequestUserDataFunctionMap(final BreinUser breinUser) {
        // configure the map
        requestUserDataFunctions.put("email", breinUser::getEmail);
        requestUserDataFunctions.put("firstName", breinUser::getFirstName);
        requestUserDataFunctions.put("lastName", breinUser::getLastName);
        requestUserDataFunctions.put("dateOfBirth", breinUser::getDateOfBirth);
        requestUserDataFunctions.put("deviceId", breinUser::getDeviceId);
        requestUserDataFunctions.put("imei", breinUser::getImei);
        requestUserDataFunctions.put("sessionId", breinUser::getSessionId);
    }

    /**
     * Executes the actions within the map. Checks if the value is valid and if this is
     * the case then the property will be added to the json structure.
     *
     * @param jsonObject structure that will be added with a property
     * @param functionMap map of actions (aka methods)
     */
    public void executeMapFunctions(final JsonObject jsonObject,
                                    final Map<String, CheckFunction> functionMap) {

        functionMap.entrySet().forEach(action -> {
            if (BreinUtil.containsValue(action.getValue().invoke())) {
                jsonObject.addProperty(action.getKey(), action.getValue().invoke());
            }
        });
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

        return BreinUtil.generateSignature(message, getConfig().getSecret());
    }
}

