package com.brein.api;

import com.brein.domain.BreinUser;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Sends an activity to the engine utilizing the API. The call is done asynchronously as a POST request. It is important
 * that a valid API-key is configured prior to using this function.
 */
public class BreinActivity extends BreinBase {

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
     * contains the extra fields that are part of the activity map
     */
    private Map<String, Object> extraActivityMap;

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
                return getBreinBaseRequest().getExtraBaseMap();
            case MK_ACTIVITY:
                return extraActivityMap;
            case MK_USER:
                return getBreinUserRequest().getExtraUserMap();
            case MK_USER_ADDITIONAL:
                return getBreinUserRequest().getExtraUserAdditionalMap();
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
    public BreinActivity setExtraMap(final MapKind kind,
                                     final Map<String, Object> extraMap) {

        if (kind == MapKind.MK_BASE) {
            getBreinBaseRequest().setExtraBaseMap(extraMap);
        } else if (kind == MapKind.MK_ACTIVITY) {
            extraActivityMap = extraMap;
        } else if (kind == MapKind.MK_USER) {
            getBreinUserRequest().setExtraUserMap(extraMap);
        } else if (kind == MapKind.MK_USER_ADDITIONAL) {
            getBreinUserRequest().setExtraUserAdditionalMap(extraMap);
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
        extraActivityMap = null;
        requestActivityFunctions = null;
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

        final JsonObject requestData = new JsonObject();

        // call base class for base data
        super.prepareJsonRequest();

        // user data level and additional
        final BreinUser breinUser = getBreinUser();
        if (null != breinUser) {
            getBreinUserRequest().prepareUserRequestData(requestData, breinUser);
        }

        // activity data
        final JsonObject activityData = new JsonObject();
        prepareActivityRequestData(activityData);
        requestData.add("activity", activityData);

        // base level data...
        getBreinBaseRequest().prepareBaseRequestData(this, requestData, isSign());

        return getGson().toJson(requestData);
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
        BreinMapUtil.executeMapFunctions(activityData, requestActivityFunctions);

        // add tags map if configured
        if (tagsMap != null && tagsMap.size() > 0) {
            final JsonObject tagsData = new JsonObject();
            BreinMapUtil.fillMap(tagsMap, tagsData);
            activityData.add("tags", tagsData);
        }

        // check if there are further extra maps to add on base level
        if (extraActivityMap != null && extraActivityMap.size() > 0) {
            BreinMapUtil.fillMap(extraActivityMap, activityData);
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

