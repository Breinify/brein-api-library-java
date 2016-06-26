package com.brein.domain;

/**
 * A plain object specifying the user information the activity belongs to.
 */
public class BreinUser {

    /**
     * user data...
     */
    private String email;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private String imei;

    private String deviceId;

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

