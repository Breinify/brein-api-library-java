package com.brein.domain;

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
     * contains the data structure for the user request part including additional
     */
    private final BreinUserRequest breinUserRequest;

    /**
     * create a brein user with field email.
     *
     * @param email of the user
     */
    public BreinUser(final String email) {
        this();
        setEmail(email);
    }

    /**
     * create a brein user
     */
    public BreinUser() {
        breinUserRequest = new BreinUserRequest();
    }

    /**
     * creates a brein user with a given brein user request.
     * This might come from a clone operation.
     *
     * @param breinUserRequest contains the brein user request object
     */
    public BreinUser(final BreinUserRequest breinUserRequest) {
        this.breinUserRequest = breinUserRequest;
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
     * returns the instance of BreinUserRequestData
     *
     * @return instance of BreinUserRequestData
     */
    public BreinUserRequest getBreinUserRequest() {
        return breinUserRequest;
    }

    /**
     * sets the additional map
     *
     * @param dataMap map containing the additional fields
     * @return self
     */
    public BreinUser setAdditionalMap(final Map<String, Object> dataMap) {
        getBreinUserRequest().setAdditionalMap(dataMap);
        return this;
    }

    /**
     * sets the user map
     *
     * @param dataMap map containing fields on user level
     * @return self
     */
    public BreinUser setUserMap(final Map<String, Object> dataMap) {
        getBreinUserRequest().setUserMap(dataMap);
        return this;
    }

    /**
     * returns the user field map
     *
     * @return user field map
     */
    public Map<String, Object> getUserMap() {
        return this.getBreinUserRequest().getUserMap();
    }

    /**
     * returns the map that is part of the addtional section
     *
     * @return addtional map
     */
    public Map<String, Object> getAdditionalMap() {
        return this.getBreinUserRequest().getAdditionalMap();
    }

    /**
     * Creates a clone of a given BreinUser object
     *
     * @param sourceUser contains the original brein user
     * @return a copy of the original brein user
     */
    public static BreinUser clone(final BreinUser sourceUser) {

        // firstly create a copy of the breinuser request
        final BreinUserRequest newBreinUserRequest = BreinUserRequest
                .clone(sourceUser.getBreinUserRequest());

        // then a new user with the new created brein user request
        final BreinUser newUser = new BreinUser(newBreinUserRequest)
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

