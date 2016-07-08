package com.brein.domain;

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
     * create a brein user with mandatory field email.
     * @param email of the user
     */
    public BreinUser(final String email) {
        setEmail(email);
    }

    /**
     *
     * setter & getter of the properties
     *
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(final String imei) {
        this.imei = imei;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(final String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }
}

