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
     *
     * @param email of the user
     */
    public BreinUser(final String email) {
        setEmail(email);
    }

    /**
     * setter & getter of the properties
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the email of the user
     * @param email to set (will not be checked)
     * @return this -> allows chaining
     */
   public BreinUser setEmail(final String email) {
        this.email = email;
        return this;
    }

    /**
     * Retrieves the first name of the user
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * set the first name of the user
     * @param firstName name to set
     * @return this -> allows chaining
     */
    public BreinUser setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Retrieves the last name of the user
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * set the last name of the user
     * @param lastName last name
     * @return thi -> allows chaining
     */
    public BreinUser setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Returns the date of birth
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
     * @return this -> allows chaining
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
     * Sets the date of birth
     * @param dateOfBirth a string containing the date of birth.
     *                    This content and format will not be checked
     * @return this -> allows chaining
     */
    public BreinUser setDateOfBirth(final String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    /**
     * Retrieves imei (International Mobile Equipment Identity)
     * @return serial number as string
     */
    public String getImei() {
        return imei;
    }

    /**
     * Sets the imei number
     * @param imei number
     * @return this -> allows chaining
     */
    public BreinUser setImei(final String imei) {
        this.imei = imei;
        return this;
    }

    /**
     * retrieves the deviceid
     * @return device id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * sets the device id
     * @param deviceId the id of the device
     * @return this -> allows chaining
     */
    public BreinUser setDeviceId(final String deviceId) {
        this.deviceId = deviceId;
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
    public BreinUser setSessionId(final String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    /**
     * provides a nices output of the user details
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
                + "\n"
                + " sessionId: "
                + (this.sessionId == null ? "n/a" : this.sessionId);

    }
}

