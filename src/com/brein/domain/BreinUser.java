package com.brein.domain;

import com.brein.api.BreinBase;
import com.brein.api.BreinException;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * A plain object specifying the user information the activity belongs to
 */
public class BreinUser {
    public static final String USER_FIELD = "user";
    public static final String ADDITIONAL_FIELD = "additional";

    /**
     * This list may not be complete it just contains some values. For a complete list it is recommended to look at the
     * API documentation.
     */
    public enum UserField {
        FIRST_NAME("firstName"),
        LAST_NAME("lastName"),
        DATE_OF_BIRTH("dateOfBirth"),
        EMAIL("email"),
        MD5_EMAIL("md5Email"),
        SESSION_ID("sessionId"),
        USER_ID("userId");

        final String name;

        UserField(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void set(final BreinUser user, final Object value) {
            user.set(getName(), value);
        }
    }

    /**
     * contains further fields in the user additional section
     */
    private Map<String, Object> additionalMap;

    /**
     * contains further fields in the user section
     */
    private Map<String, Object> userMap;

    /**
     * get the email of the user
     *
     * @return email
     */
    public String getEmail() {
        return getUserField(UserField.EMAIL);
    }

    /**
     * sets the email of the user
     *
     * @param email to set (will not be checked)
     *
     * @return this
     */
    public BreinUser setEmail(final String email) {
        UserField.EMAIL.set(this, email);
        return this;
    }

    /**
     * Retrieves the first name of the user
     *
     * @return first name
     */
    public String getFirstName() {
        return getUserField(UserField.FIRST_NAME);
    }

    /**
     * set the first name of the user
     *
     * @param firstName name to set
     *
     * @return this
     */
    public BreinUser setFirstName(final String firstName) {
        UserField.FIRST_NAME.set(this, firstName);
        return this;
    }

    /**
     * Retrieves the last name of the user
     *
     * @return last name
     */
    public String getLastName() {
        return getUserField(UserField.LAST_NAME);
    }

    /**
     * set the last name of the user
     *
     * @param lastName last name
     *
     * @return thi
     */
    public BreinUser setLastName(final String lastName) {
        UserField.LAST_NAME.set(this, lastName);
        return this;
    }

    /**
     * returns the sessionId (if set)
     *
     * @return sessionId
     */
    public String getSessionId() {
        return getUserField(UserField.SESSION_ID);
    }

    /**
     * sets the sessionId
     *
     * @param sessionId value of the sessionId
     *
     * @return self
     */
    public BreinUser setSessionId(final String sessionId) {
        UserField.SESSION_ID.set(this, sessionId);
        return this;
    }

    /**
     * Returns the date of birth
     *
     * @return date of birth
     */
    public String getDateOfBirth() {
        return getUserField(UserField.DATE_OF_BIRTH);
    }

    /**
     * Set's the date of birth
     * There is no check if the month - day combination is valid, only
     * the range for day, month and year will be checked
     *
     * @param month (1..12)
     * @param day   (1..31)
     * @param year  (1900..2100)
     *
     * @return self
     */
    public BreinUser setDateOfBirth(final int month, final int day, final int year) {
        if ((month >= 1 && month <= 12) &&
                (day >= 1 && day <= 31) &&
                (year >= 1900 && year <= 2100)) {
            setDateOfBirth(Integer.toString(month)
                    + "/"
                    + Integer.toString(day)
                    + "/"
                    + Integer.toString(year));
        } else {
            setDateOfBirth(null);
        }

        return this;
    }

    /**
     * Sets the value of dateOfBirth as String. This is only used internally.
     *
     * @param dateOfBirthString contains the date of birth string
     *
     * @return self
     */
    public BreinUser setDateOfBirth(final String dateOfBirthString) {
        UserField.DATE_OF_BIRTH.set(this, dateOfBirthString);
        return this;
    }

    /**
     * Sets the users value and overrides any current value. Cannot used to override the {@code additional} field.
     *
     * @param key   the name of the value to be set
     * @param value the value to be set
     *
     * @return self
     */
    public BreinUser set(final String key, final Object value) {
        if (ADDITIONAL_FIELD.equalsIgnoreCase(key)) {
            throw new BreinException("The field '" + ADDITIONAL_FIELD + "' cannot be set, " +
                    "use the setAdditional method to do so.");
        } else if (this.userMap == null) {
            this.userMap = new HashMap<>();
        }

        this.userMap.put(key, value);
        return this;
    }

    public <T> T get(final String key) {
        return get(key, false);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final String key, final boolean additional) {
        if (additional) {
            return this.additionalMap == null ? null : (T) this.additionalMap.get(key);
        } else {
            return this.userMap == null ? null : (T) this.userMap.get(key);
        }
    }

    public <T> T getAdditional(final String key) {
        return get(key, true);
    }

    /**
     * Sets an additional value.
     *
     * @param key   the name of the additional value to be set
     * @param value the value to be set
     *
     * @return self
     */
    public BreinUser setAdditional(final String key, final Object value) {
        if (this.additionalMap == null) {
            this.additionalMap = new HashMap<>();
        }

        this.additionalMap.put(key, value);
        return this;
    }

    public void prepareRequestData(final BreinConfig config, final Map<String, Object> requestData) {
        final Map<String, Object> userRequestData = new HashMap<>();
        requestData.put(USER_FIELD, userRequestData);

        // add the user-data, if there is any
        if (this.userMap != null) {
            this.userMap.forEach((key, value) -> {
                if (BreinUtil.containsValue(value)) {
                    userRequestData.put(key, value);
                }
            });
        }

        // add the additional-data, if there is any
        if (this.additionalMap != null) {
            userRequestData.put(ADDITIONAL_FIELD, BreinMapUtil.copyMap(this.additionalMap));
        }
    }

    /**
     * provides a nicer output of the user details
     *
     * @return output
     */
    @Override
    public String toString() {
        final BreinConfig config = new BreinConfig(null);
        final Map<String, Object> requestData = new HashMap<>();

        prepareRequestData(config, requestData);
        return BreinBase.GSON.toJson(requestData);
    }

    protected <T> T getUserField(final UserField field) {
        return get(field.getName());
    }
}

