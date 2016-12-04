package com.brein.domain;

import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;
import com.google.gson.JsonObject;

import java.util.Map;


public class BreinUserRequest {

    /**
     * contains further fields in the user additional section
     */
    private Map<String, Object> additionalMap;

    /**
     * contains further fields in the user section
     */
    private Map<String, Object> userMap;

    /**
     * returns the user map
     *
     * @return the user map
     */
    public Map<String, Object> getUserMap() {
        return userMap;
    }

    /**
     * sets the user map
     *
     * @param userMap map
     */
    public void setUserMap(final Map<String, Object> userMap) {
        this.userMap = userMap;
    }

    /**
     * returns the user additional map
     *
     * @return map
     */
    public Map<String, Object> getAdditionalMap() {
        return additionalMap;
    }

    /**
     * sets the user additional map
     *
     * @param additionalMap map
     */
    public void setAdditionalMap(final Map<String, Object> additionalMap) {
        this.additionalMap = additionalMap;
    }

    /**
     * Prepares the request on user level
     *
     * @param requestData contains the json request that is generated (top level)
     * @param breinUser   contains the brein user data
     */
    public void prepareUserRequestData(final JsonObject requestData,
                                       final BreinUser breinUser) {

        // user fields...
        final JsonObject userData = new JsonObject();
        prepareUserFields(breinUser, userData);

        // check if there are further maps to add on user level
        if (userMap != null && userMap.size() > 0) {
            BreinMapUtil.fillMap(userMap, userData);
        }

        // additional part
        final JsonObject additional = new JsonObject();
        prepareAdditionalFields(breinUser, additional);

        // check if there are further maps to add on user additional level
        if (additionalMap != null && additionalMap.size() > 0) {
            BreinMapUtil.fillMap(additionalMap, additional);
        }

        if (additional.size() > 0) {
            userData.add("additional", additional);
        }

        // add the data
        if (userData.size() > 0) {
            requestData.add("user", userData);
        }
    }

    /**
     * Prepares the fields that are part of the user section
     *
     * @param breinUser  contains the brein user object
     * @param jsonObject contains the json array
     */
    public void prepareUserFields(final BreinUser breinUser, final JsonObject jsonObject) {

        if (BreinUtil.containsValue(breinUser.getEmail())) {
            jsonObject.addProperty("email", breinUser.getEmail());
        }

        if (BreinUtil.containsValue(breinUser.getFirstName())) {
            jsonObject.addProperty("firstName", breinUser.getFirstName());
        }

        if (BreinUtil.containsValue(breinUser.getLastName())) {
            jsonObject.addProperty("lastName", breinUser.getLastName());
        }

        if (BreinUtil.containsValue(breinUser.getDateOfBirth())) {
            jsonObject.addProperty("dateOfBirth", breinUser.getDateOfBirth());
        }

        if (BreinUtil.containsValue(breinUser.getDeviceId())) {
            jsonObject.addProperty("deviceId", breinUser.getDeviceId());
        }

        if (BreinUtil.containsValue(breinUser.getImei())) {
            jsonObject.addProperty("imei", breinUser.getImei());
        }

        if (BreinUtil.containsValue(breinUser.getSessionId())) {
            jsonObject.addProperty("sessionId", breinUser.getSessionId());
        }

    }

    /**
     * Prepares the fields that are part of the user additional section
     *
     * @param breinUser  contains the brein user object
     * @param jsonObject contains the json array
     */
    public void prepareAdditionalFields(final BreinUser breinUser, final JsonObject jsonObject) {

        if (BreinUtil.containsValue(breinUser.getUserAgent())) {
            jsonObject.addProperty("userAgent", breinUser.getUserAgent());
        }

        if (BreinUtil.containsValue(breinUser.getReferrer())) {
            jsonObject.addProperty("referrer", breinUser.getReferrer());
        }

        if (BreinUtil.containsValue(breinUser.getUrl())) {
            jsonObject.addProperty("url", breinUser.getUrl());
        }

        if (BreinUtil.containsValue(breinUser.getLocalDateTime())) {
            jsonObject.addProperty("localDateTime", breinUser.getLocalDateTime());
        }

        if (BreinUtil.containsValue(breinUser.getTimezone())) {
            jsonObject.addProperty("timezone", breinUser.getTimezone());
        }

    }

    /**
     * Used to create a clone of a given BreinUserRequest
     *
     * @param breinUserRequest original brein user object
     * @return contains the clone
     */
    public static BreinUserRequest clone(final BreinUserRequest breinUserRequest) {

        final BreinUserRequest newBreinUserRequest = new BreinUserRequest();

        // a copy of all maps
        final Map<String, Object> userMap = breinUserRequest.getUserMap();
        final Map<String, Object> copyUserMap = BreinMapUtil.copyMap(userMap);
        newBreinUserRequest.setUserMap(copyUserMap);

        final Map<String, Object> additionalMap = breinUserRequest.getAdditionalMap();
        final Map<String, Object> copyAdditionalMap = BreinMapUtil.copyMap(additionalMap);
        newBreinUserRequest.setAdditionalMap(copyAdditionalMap);

        return newBreinUserRequest;
    }
}
