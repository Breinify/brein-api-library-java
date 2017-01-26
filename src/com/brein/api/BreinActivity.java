package com.brein.api;

import com.brein.domain.BreinUser;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Sends an activity to the engine utilizing the API. The call is done asynchronously as a POST request. It is important
 * that a valid API-key is configured prior to using this function.
 */
public class BreinActivity extends BreinBase {


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
     * contains the tagsMap map
     */
    private Map<String, Object> tagsMap;

    /**
     * contains the fields that are part of the activity map
     */
    private Map<String, Object> activityMap;

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
     * @return self
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
     * @return self
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
     * @return self
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
    public Map<String, Object> getTags() {
        return tagsMap;
    }

    /**
     * sets the tagsMap
     *
     * @param tagsMap created map (e.g. HashMap)
     * @return self
     */
    public BreinActivity setTags(final Map<String, Object> tagsMap) {
        this.tagsMap = tagsMap;
        return this;
    }

    /**
     * sets the activity map
     *
     * @param dataActivityMap containing additional values
     * @return self
     */
    public BreinActivity set(final Map<String, Object> dataActivityMap) {
        if (dataActivityMap == null) {
            return this;
        }

        if (this.activityMap == null) {
            this.activityMap = new HashMap<>();
        }

        this.activityMap.putAll(dataActivityMap);
        return this;
    }

    /**
     * sets the activity map
     *
     * @param key contains the key
     * @param dataActivityMap containing additional values
     * @return self
     */
    public BreinActivity set(final String key, final Map<String, Object> dataActivityMap) {

        if (dataActivityMap == null) {
            return this;
        }

        return set(Collections.singletonMap(key, dataActivityMap));
    }

    /**
     * returns the activity map
     *
     * @return the activity map
     */
    public Map<String, Object> get() {
        return activityMap;
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
        activityMap = null;
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
     */
    public void activity(final BreinUser breinUser,
                         final String breinActivityType,
                         final String breinCategoryType,
                         final String description) {

        // set the values for further usage
        setBreinUser(breinUser);
        setBreinActivityType(breinActivityType);
        setBreinCategoryType(breinCategoryType);
        setDescription(description);

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

        final Map<String, Object> requestData = new HashMap<>();

        // call base class for base data
        super.prepareJsonRequest();

        // user data level and additional
        final BreinUser breinUser = getBreinUser();
        if (null != breinUser) {
            breinUser.prepareUserRequestData(requestData, breinUser);
        }

        // activity data
        final Map<String, Object> activityData = new HashMap<>();
        prepareActivityRequestData(activityData);
        if (activityData.size() > 0) {
            requestData.put("activity", activityData);
        }

        // base level data...
        prepareBaseRequestData(this, requestData);

        return getGson().toJson(requestData);
    }

    /**
     * Prepares the activity json structure
     *
     * @param activityData contains the created data structure
     */
    public void prepareActivityRequestData(final Map<String, Object> activityData) {

        // activity fields...
        if (BreinUtil.containsValue(getBreinActivityType())) {
            activityData.put("type", getBreinActivityType());
        }

        if (BreinUtil.containsValue(getDescription())) {
            activityData.put("description", getDescription());
        }

        if (BreinUtil.containsValue(getBreinCategoryType())) {
            activityData.put("category", getBreinCategoryType());
        }

        // add tagsMap map if configured
        if (tagsMap != null && tagsMap.size() > 0) {
            final Map<String, Object> tagsData = new HashMap<>();
            tagsData.putAll(tagsMap);
            activityData.put("tags", tagsData);
        }

        // check if there are further maps to add on base level
        if (activityMap != null && activityMap.size() > 0) {
            activityData.putAll(activityMap);
        }
    }

    /**
     * Used to create a clone of an activity. This is important in order to prevent
     * concurrency issues.
     *
     * @param sourceActivity contains the original activity object
     * @return the clone of the activity object
     */
    public static BreinActivity clone(final BreinActivity sourceActivity) {

        // create a new activity object
        final BreinActivity activity = new BreinActivity()
                .setBreinActivityType(sourceActivity.getBreinActivityType())
                .setBreinCategoryType(sourceActivity.getBreinCategoryType())
                .setDescription(sourceActivity.getDescription());

        // clone maps
        final Map<String, Object> activityMap = BreinMapUtil
                .copyMap(sourceActivity.get());
        activity.set(activityMap);

        final Map<String, Object> tagsMapCopy = BreinMapUtil
                .copyMap(sourceActivity.getTags());
        activity.setTags(tagsMapCopy);

        // clone from base class
        activity.cloneBase(sourceActivity);

        return activity;
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

