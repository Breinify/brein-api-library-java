package com.brein.domain;

import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A plain object specifying the user information the activity belongs to
 */
public class BreinUser {

    /**
     * user email
     */
    private String email;

    /**
     * user first name
     */
    private String firstName;

    /**
     * user last name
     */
    private String lastName;

    /**
     * user date of birth
     */
    private String dateOfBirth;

    /**
     * user imei number
     */
    private String imei;

    /**
     * user deviceId
     */
    private String deviceId;

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
    private String url;

    /**
     * contains the url in additional part
     */
    private String ipAddress;

    /**
     * contains the localDateTime value (for temporalData request)
     */
    private String localDateTime;

    /**
     * contains the timezone value (for temporalData request)
     */
    private String timezone;

    /**
     * contains further fields in the user additional section
     */
    private Map<String, Object> additionalMap;

    /**
     * contains further fields in the user section
     */
    private Map<String, Object> userMap;

    /**
     * create a brein user with field email.
     *
     * @param email of the user
     */
    public BreinUser(final String email) {
        setEmail(email);
    }

    public BreinUser() {
    }

    /**
     * setter & getter of the properties
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the email of the user
     *
     * @param email to set (will not be checked)
     * @return this -> allows chaining
     */
    public BreinUser setEmail(final String email) {
        this.email = email;
        return this;
    }

    /**
     * Retrieves the first name of the user
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * set the first name of the user
     *
     * @param firstName name to set
     * @return this -> allows chaining
     */
    public BreinUser setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Retrieves the last name of the user
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * set the last name of the user
     *
     * @param lastName last name
     * @return thi -> allows chaining
     */
    public BreinUser setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * returns the sessionId (if set)
     *
     * @return sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * sets the sessionId
     *
     * @param sessionId value of the sessionId
     */
    public BreinUser setSessionId(final String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    /**
     * retrieves the additional userAgent value
     *
     * @return value
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * sets the additional user agent value
     *
     * @param userAgent value
     */
    public BreinUser setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    /**
     * retrieves the ipAddress (additional part)
     *
     * @return ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * sets the ipAddress
     *
     * @param ipAddress value
     */
    public BreinUser setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    /**
     * retrieves the additional referrer value
     *
     * @return value
     */
    public String getReferrer() {
        return referrer;
    }

    /**
     * sets the additional referrer value
     *
     * @param referrer value
     */
    public BreinUser setReferrer(final String referrer) {
        this.referrer = referrer;
        return this;
    }

    /**
     * retrieves the additional url
     *
     * @return value
     */
    public String getUrl() {
        return url;
    }

    /**
     * sets the additional url
     *
     * @param url value
     * @return self
     */
    public BreinUser setUrl(final String url) {
        this.url = url;
        return this;
    }

    /**
     * Returns the date of birth
     *
     * @return date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Set's the date of birth
     * There is no check if the month - day combination is valid, only
     * the range for day, month and year will be checked
     *
     * @param month (1..12)
     * @param day   (1..31)
     * @param year  (1900..2100)
     * @return self
     */
    public BreinUser setDateOfBirth(final int month, final int day, final int year) {

        if (month >= 1 && month <= 12) {
            if (day >= 1 && day <= 31) {
                if (year >= 1900 && year <= 2100) {
                    this.dateOfBirth = Integer.toString(month)
                            + "/"
                            + Integer.toString(day)
                            + "/"
                            + Integer.toString(year);
                }
            }
        }
        return this;
    }

    /**
     * Sets the value of dateOfBirth as String. This is only used internally.
     *
     * @param dateOfBirthString contains the date of birth string
     */
    private BreinUser setDateOfBirthString(final String dateOfBirthString) {
        this.dateOfBirth = dateOfBirthString;
        return this;
    }

    /**
     * resets the dateOfBirth to an empty value
     */
    public void resetDateOfBirth() {
        this.dateOfBirth = "";
    }

    /**
     * Retrieves imei (International Mobile Equipment Identity)
     *
     * @return serial number as string
     */
    public String getImei() {
        return imei;
    }

    /**
     * Sets the imei number
     *
     * @param imei number
     * @return self
     */
    public BreinUser setImei(final String imei) {
        this.imei = imei;
        return this;
    }

    /**
     * retrieves the device-id
     *
     * @return device id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * sets the device id
     *
     * @param deviceId the id of the device
     * @return self
     */
    public BreinUser setDeviceId(final String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    /**
     * get's the localDateTime
     *
     * @return localDateTime (e.g. "Sun 25 Dec 2016 18:15:48 GMT-0800 (PST)")
     */
    public String getLocalDateTime() {
        return localDateTime;
    }

    /**
     * set's the localDateTime
     *
     * @param localDateTime contains the localdate time
     * @return self
     */
    public BreinUser setLocalDateTime(final String localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    /**
     * gets the timezone
     *
     * @return timezone (e.g. "America/Los_Angeles")
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * set the timezone
     *
     * @param timezone timezone (e.g. "America/Los_Angeles"))
     * @return self
     */
    public BreinUser setTimezone(final String timezone) {
        this.timezone = timezone;
        return this;
    }

    /**
     * returns the user map
     *
     * @return the user map
     */
    public Map<String, Object> get() {
        return userMap;
    }

    /**
     * sets the user map
     *
     * @param userMap map
     */
    public BreinUser set(final Map<String, Object> userMap) {
        if (userMap == null) {
            return this;
        }
        if (this.userMap == null) {
            this.userMap = new HashMap<>();
        }
        this.userMap.putAll(userMap);
        return this;
    }

    /**
     * sets the user additional fields
     *
     * @param key     contains the key for the nested map
     * @param userMap map of fields
     */
    public BreinUser set(final String key, final Map<String, Object> userMap) {
        if (userMap == null) {
            return this;
        }
        return set(Collections.singletonMap(key, userMap));
    }

    /**
     * returns the user additional map
     *
     * @return map
     */
    public Map<String, Object> getAdditional() {
        return additionalMap;
    }

    /**
     * sets the user additional map
     *
     * @param additional map
     */
    public BreinUser setAdditional(final Map<String, Object> additional) {
        if (additional == null) {
            return this;
        }

        if (this.additionalMap == null) {
            this.additionalMap = new HashMap<>();
        }
        this.additionalMap.putAll(additional);
        return this;
    }

    /**
     * sets the user additional fields
     *
     * @param key        contains the key for the nested map
     * @param additional map of fields
     */
    public BreinUser setAdditional(final String key, final Map<String, Object> additional) {
        if (additional == null) {
            return this;
        }
        return setAdditional(Collections.singletonMap(key, additional));
    }

    /**
     * Prepares the request on user level
     *
     * @param requestData contains the json request that is generated (top level)
     * @param breinUser   contains the brein user data
     */
    public void prepareUserRequestData(final Map<String, Object> requestData,
                                       final BreinUser breinUser) {

        // user fields...
        final Map<String, Object> userData = new HashMap<>();
        prepareUserFields(breinUser, userData);

        // check if there are further maps to add on user level
        if (userMap != null && userMap.size() > 0) {
            requestData.putAll(userMap);
        }

        // additional part
        final Map<String, Object> additional = new HashMap<>();
        prepareAdditionalFields(breinUser, additional);

        // check if there are further maps to add on user additional level
        if (additionalMap != null && additionalMap.size() > 0) {
            additional.putAll(additionalMap);
        }

        if (additional.size() > 0) {
            userData.put("additional", additional);
        }

        // add the data
        if (userData.size() > 0) {
            requestData.put("user", userData);
        }
    }

    /**
     * Prepares the fields that are part of the user section
     *
     * @param breinUser  contains the brein user object
     * @param jsonObject contains the json array
     */
    public void prepareUserFields(final BreinUser breinUser, final Map<String, Object> jsonObject) {

        if (BreinUtil.containsValue(breinUser.getEmail())) {
            jsonObject.put("email", breinUser.getEmail());
        }

        if (BreinUtil.containsValue(breinUser.getFirstName())) {
            jsonObject.put("firstName", breinUser.getFirstName());
        }

        if (BreinUtil.containsValue(breinUser.getLastName())) {
            jsonObject.put("lastName", breinUser.getLastName());
        }

        if (BreinUtil.containsValue(breinUser.getDateOfBirth())) {
            jsonObject.put("dateOfBirth", breinUser.getDateOfBirth());
        }

        if (BreinUtil.containsValue(breinUser.getDeviceId())) {
            jsonObject.put("deviceId", breinUser.getDeviceId());
        }

        if (BreinUtil.containsValue(breinUser.getImei())) {
            jsonObject.put("imei", breinUser.getImei());
        }

        if (BreinUtil.containsValue(breinUser.getSessionId())) {
            jsonObject.put("sessionId", breinUser.getSessionId());
        }

    }

    /**
     * Prepares the fields that are part of the user additional section
     *
     * @param breinUser  contains the brein user object
     * @param jsonObject contains the json array
     */
    public void prepareAdditionalFields(final BreinUser breinUser, final Map<String, Object> jsonObject) {

        if (BreinUtil.containsValue(breinUser.getUserAgent())) {
            jsonObject.put("userAgent", breinUser.getUserAgent());
        }

        if (BreinUtil.containsValue(breinUser.getReferrer())) {
            jsonObject.put("referrer", breinUser.getReferrer());
        }

        if (BreinUtil.containsValue(breinUser.getUrl())) {
            jsonObject.put("url", breinUser.getUrl());
        }

        if (BreinUtil.containsValue(breinUser.getLocalDateTime())) {
            jsonObject.put("localDateTime", breinUser.getLocalDateTime());
        }

        if (BreinUtil.containsValue(breinUser.getTimezone())) {
            jsonObject.put("timezone", breinUser.getTimezone());
        }

    }

    /**
     * Creates a clone of a given BreinUser object
     *
     * @param sourceUser contains the original brein user
     * @return a copy of the original brein user
     */
    public static BreinUser clone(final BreinUser sourceUser) {

        // then a new user with the new created brein user request
        final BreinUser newUser = new BreinUser()
                .setEmail(sourceUser.getEmail())
                .setFirstName(sourceUser.getFirstName())
                .setLastName(sourceUser.getLastName())
                .setDateOfBirthString(sourceUser.getDateOfBirth())
                .setImei(sourceUser.getImei())
                .setDeviceId(sourceUser.getDeviceId())
                .setUrl(sourceUser.getUrl())
                .setSessionId(sourceUser.getSessionId())
                .setImei(sourceUser.getImei())
                .setUserAgent(sourceUser.getUserAgent())
                .setReferrer(sourceUser.getReferrer())
                .setIpAddress(sourceUser.getIpAddress())
                .setLocalDateTime(sourceUser.getLocalDateTime())
                .setTimezone(sourceUser.getTimezone());

        // copy maps
        final Map<String, Object> additionalMap = sourceUser.getAdditional();
        final Map<String, Object> copyOfAdditionalMap = BreinMapUtil.copyMap(additionalMap);
        newUser.setAdditional(copyOfAdditionalMap);

        final Map<String, Object> userMap = sourceUser.get();
        final Map<String, Object> copyOfUserMap = BreinMapUtil.copyMap(userMap);
        newUser.set(copyOfUserMap);

        return newUser;
    }

    /**
     * provides a nicer output of the user details
     *
     * @return output
     */
    @Override
    public String toString() {

        return "BreinUser details: "
                + "\n"
                + " name: "
                + (this.firstName == null ? "n/a" : this.firstName)
                + " "
                + (this.lastName == null ? "n/a" : this.lastName)
                + " email: "
                + (this.email == null ? "n/a" : this.email)
                + " dateOfBirth: "
                + (this.dateOfBirth == null ? "n/a" : this.dateOfBirth)
                + "\n"
                + " imei: "
                + (this.imei == null ? "n/a" : this.imei)
                + " deviceId: "
                + (this.deviceId == null ? "n/a" : this.deviceId)
                + "\n";
    }

}

