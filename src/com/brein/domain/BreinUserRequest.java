package com.brein.domain;

import com.brein.api.CheckFunction;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;


public class BreinUserRequest {

    /**
     * contains the function map for user data related functions
     */
    private Map<String, CheckFunction> requestUserDataFunctions = null;

    /**
     * contains the function map for user additional data related functions
     */
    private Map<String, CheckFunction> requestUserAdditionalDataFunctions = null;

    /**
     * contains extra fields in the user additional section
     */
    private Map<String, Object> extraUserAdditionalMap;

    /**
     * contains extra fields in the user section
     */
    private Map<String, Object> extraUserMap;

    /**
     * returns the extra user map
     * @return the extra user map
     */
    public Map<String, Object> getExtraUserMap() {
        return extraUserMap;
    }

    /**
     * sets the extra user map
     * @param extraUserMap map
     */
    public void setExtraUserMap(final Map<String, Object> extraUserMap) {
        this.extraUserMap = extraUserMap;
    }

    /**
     * returns the extra user additional map
     * @return map
     */
    public Map<String, Object> getExtraUserAdditionalMap() {
        return extraUserAdditionalMap;
    }

    /**
     * sets the extra user additional map
     * @param extraUserAdditionalMap map
     */
    public void setExtraUserAdditionalMap(final Map<String, Object> extraUserAdditionalMap) {
        this.extraUserAdditionalMap = extraUserAdditionalMap;
    }

    /**
     * configures the user additional part
     * @param breinUser contains the brein user
     */
    public void configureRequestUserAdditionalFunctionMap(final BreinUser breinUser) {
        requestUserAdditionalDataFunctions.put("userAgent", breinUser::getUserAgent);
        requestUserAdditionalDataFunctions.put("referrer", breinUser::getReferrer);
        requestUserAdditionalDataFunctions.put("url", breinUser::getUrl);
        requestUserAdditionalDataFunctions.put("localDateTime", breinUser::getLocalDateTime);
        requestUserAdditionalDataFunctions.put("timezone", breinUser::getTimezone);
    }

    /**
     * configures the user data function map
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
     * Prepares the request on user level
     * @param requestData contains the json request that is generated (top level)
     * @param breinUser contains the brein user data
     */
    public void prepareUserRequestData(final JsonObject requestData,
                                       final BreinUser breinUser) {

        final JsonObject userData = new JsonObject();

        // do it once
        if (requestUserDataFunctions == null) {
            requestUserDataFunctions = new HashMap<>();
            configureRequestUserDataFunctionMap(breinUser);
        }

        // Execute the functions and add it to userData
        executeMapFunctions(userData, requestUserDataFunctions);

        // check if there are further extra maps to add on user level
        if (extraUserMap != null && extraUserMap.size() > 0) {
            BreinMapUtil.fillMap(extraUserMap, userData);
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
            BreinMapUtil.fillMap(extraUserAdditionalMap, additional);
        }

        if (additional.size() > 0) {
            userData.add("additional", additional);
        }

        // add the data
        requestData.add("user", userData);
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
}
