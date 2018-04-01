package com.brein.domain;

import com.brein.api.BreinActivity;
import com.brein.api.BreinTemporalData;
import com.brein.util.BreinMapUtil;
import com.google.gson.Gson;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Test classes for the domain objects
 */
public class TestDomain {
    private final String validApiKey = "9D9C-C9E9-BC93-4D1D-9A61-3A0F-9BD9-CF14";

    /**
     * Init part
     */
    @BeforeClass
    public static void setUp() {

        // set logging on
        final Properties props = System.getProperties();
        props.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
    }

    /**
     * creates a brein request object that will be used within the body
     * of the request
     */
    @Test
    public void testBreinRequest() {
        final BreinConfig config = new BreinConfig(validApiKey);

        final BreinActivity activity = new BreinActivity();
        activity.setUser(new BreinUser()
                .setEmail("toni.maroni@mail.com")
                .setFirstName("Toni")
                .setLastName("Maroni"));
        activity.setActivityType(BreinActivityType.LOGIN);
        activity.setDescription("Super-Description");
        activity.setCategory(BreinCategoryType.HOME);

        final String jsonOutput = activity.prepareRequestData(config);
        @SuppressWarnings("unchecked")
        final Map<String, Object> json = new Gson().fromJson(jsonOutput, Map.class);

        assertEquals("Toni", BreinMapUtil.getNestedValue(json, "user", "firstName"));
        assertEquals("Maroni", BreinMapUtil.getNestedValue(json, "user", "lastName"));
        assertEquals("toni.maroni@mail.com", BreinMapUtil.getNestedValue(json, "user", "email"));

        assertEquals("login", BreinMapUtil.getNestedValue(json, "activity", "type"));
        assertEquals("Super-Description", BreinMapUtil.getNestedValue(json, "activity", "description"));
        assertEquals("home", BreinMapUtil.getNestedValue(json, "activity", "category"));

        assertEquals(validApiKey, BreinMapUtil.getNestedValue(json, "apiKey"));
    }

    /**
     * creates a brein request object that will be used within the body
     * of the request but with less data
     */
    @Test
    public void testBreinRequestWithLessData() {
        final BreinConfig config = new BreinConfig(validApiKey);
        config.setDefaultCategory(BreinCategoryType.FOOD);

        final BreinActivity activity = new BreinActivity();
        activity.setActivityType(BreinActivityType.ADD_TO_CART);
        activity.setDescription("Super-Description");

        final String jsonOutput = activity.prepareRequestData(config);
        @SuppressWarnings("unchecked")
        final Map<String, Object> json = new Gson().fromJson(jsonOutput, Map.class);

        assertNull(BreinMapUtil.getNestedValue(json, "user"));

        assertEquals("addToCart", BreinMapUtil.getNestedValue(json, "activity", "type"));
        assertEquals("Super-Description", BreinMapUtil.getNestedValue(json, "activity", "description"));
        assertEquals("food", BreinMapUtil.getNestedValue(json, "activity", "category"));

        assertEquals(validApiKey, BreinMapUtil.getNestedValue(json, "apiKey"));
    }

    /**
     * Test the birthday settings
     */
    @Test
    public void testBirthday() {
        final BreinUser user = new BreinUser();

        // set right values
        user.setDateOfBirth(1, 22, 1966);
        assertFalse(user.getDateOfBirth().isEmpty());

        // set wrong day
        user.setDateOfBirth(1, 77, 1966);
        assertNull(user.getDateOfBirth());

        // set wrong month
        user.setDateOfBirth(13, 22, 1966);
        assertNull(user.getDateOfBirth());

        // set wrong year
        user.setDateOfBirth(1, 22, 1700);
        assertNull(user.getDateOfBirth());
    }

    /**
     * Tests all BreinUser Methods
     */
    @Test
    public void testBreinUserMethods() {

        final BreinUser user = new BreinUser()
                .setFirstName("User")
                .setLastName("Anyhere")
                .setDateOfBirth(6, 20, 1985);

        assertFalse(user.toString().isEmpty());
    }

    /**
     * Tests all BreinUser Methods
     */
    @Test
    public void testBreinUserWithNoMethods() {
        final BreinUser breinUser = new BreinUser();
        assertFalse(breinUser.toString().isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testTimeZoneAndLocalDateTime() {
        final String timeZone = "Europe/Berlin";
        final String localDateTime = "Mon Sep 28 2016 14:36:22 GMT+0200 (CET)";

        final BreinTemporalData data = new BreinTemporalData()
                .setUser("email", "user.anywhere@email.com")
                .setUser("firstName", "User")
                .setLocation("text", "Berlin")
                .setTimezone(TimeZone.getTimeZone(timeZone))
                .setAdditional("localDateTime", localDateTime);

        assertEquals("user.anywhere@email.com", data.getUser().get("email"));
        assertEquals("User", data.getUser().get("firstName"));
        assertEquals(localDateTime, data.getUser().getAdditional("localDateTime"));
        assertEquals(timeZone, data.getUser().getAdditional("timezone"));
        assertEquals("Berlin", Map.class.cast(data.getUser().getAdditional("location")).get("text"));

        final String jsonStr = data.prepareRequestData(new BreinConfig("4444-4444-4444-4444-4444"));

        final Map<String, Object> json = data.getGson().fromJson(jsonStr, Map.class);
        final Map<String, Object> userJson = (Map<String, Object>) json.get("user");
        final Map<String, Object> additionalJson = (Map<String, Object>) userJson.get("additional");
        final Map<String, Object> locationJson = (Map<String, Object>) additionalJson.get("location");

        assertEquals("4444-4444-4444-4444-4444", json.get("apiKey"));
        assertEquals("user.anywhere@email.com", userJson.get("email"));
        assertEquals("User", userJson.get("firstName"));
        assertEquals(timeZone, additionalJson.get("timezone"));
        assertEquals(localDateTime, additionalJson.get("localDateTime"));
        assertEquals("Berlin", locationJson.get("text"));

        assertEquals(3, json.size()); // user, apiKey, unixTimestamp
        assertEquals(3, userJson.size()); // firstName, email, additional
        assertEquals(3, additionalJson.size()); // localDateTime, timezone, location
        assertEquals(1, locationJson.size()); // text
    }
}
