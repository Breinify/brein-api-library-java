package com.brein.domain;


import com.brein.api.BreinActivity;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test classes for the domain objects
 */
public class TestDomain {

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
        final BreinConfig breinConfig = new BreinConfig();
        final String validApiKey = "9D9C-C9E9-BC93-4D1D-9A61-3A0F-9BD9-CF14";
        breinConfig.setApiKey(validApiKey);

        final BreinUser breinUser = new BreinUser("toni.maroni@mail.com");
        breinUser.setFirstName("Toni");
        breinUser.setLastName("Maroni");

        final BreinActivity breinActivity = new BreinActivity();
        breinActivity.setConfig(breinConfig);
        breinActivity.setBreinUser(breinUser);
        breinActivity.setBreinActivityType(BreinActivityType.LOGIN);
        breinActivity.setDescription("Super-Description");
        breinActivity.setBreinCategoryType(BreinCategoryType.HOME);

        final String jsonOutput = breinActivity.prepareJsonRequest();
        assertTrue(jsonOutput.length() > 0);
    }

    /**
     * creates a brein request object that will be used within the body
     * of the request but with less data
     */
    @Test
    public void testBreinRequestWithLessData() {
        final BreinConfig breinConfig = new BreinConfig();
        final String validApiKey = "9D9C-C9E9-BC93-4D1D-9A61-3A0F-9BD9-CF14";
        breinConfig.setApiKey(validApiKey);

        final BreinUser breinUser = new BreinUser("m.recchioni@me.com");
        breinUser.setLastName("");

        final BreinActivity breinActivity = new BreinActivity();
        breinActivity.setConfig(breinConfig);
        breinActivity.setBreinUser(breinUser);
        breinActivity.setBreinActivityType(BreinActivityType.LOGIN);
        breinActivity.setDescription("Super-Description");
        breinActivity.setBreinCategoryType(BreinCategoryType.FOOD);

        final String jsonOutput = breinActivity.prepareJsonRequest();
        assertTrue(jsonOutput.length() > 0);
    }

    /**
     * Test the birthday settings
     */
    @Test
    public void testBirthday() {

        final BreinUser breinUser = new BreinUser("test.me@email.com");

        // set right values
        breinUser.setDateOfBirth("");          // empty value
        breinUser.setDateOfBirth(1, 22, 1966); // this is correct date
        assertFalse(breinUser.getDateOfBirth().isEmpty());

        // set wrong day
        breinUser.setDateOfBirth("");          // empty value
        breinUser.setDateOfBirth(1, 77, 1966); // this is wrong date
        assertTrue(breinUser.getDateOfBirth().isEmpty());

        // set wrong month
        breinUser.setDateOfBirth("");           // empty value
        breinUser.setDateOfBirth(13, 22, 1966); // this is correct date
        assertTrue(breinUser.getDateOfBirth().isEmpty());

        // set wrong year
        breinUser.setDateOfBirth("");  // empty value
        breinUser.setDateOfBirth(1, 22, 1700);
        assertTrue(breinUser.getDateOfBirth().isEmpty());

        // this can not be detected
        breinUser.setDateOfBirth("SuperDate");
        assertFalse(breinUser.getDateOfBirth().isEmpty());
    }

    /**
     * Tests all BreinUser Methods
     */
    @Test
    public void testBreinUserMethods() {

        final BreinUser breinUser = new BreinUser("user.anywhere@email.com")
                .setFirstName("User")
                .setLastName("Anyhere")
                .setImei("356938035643809")
                .setDateOfBirth(6, 20, 1985)
                .setDeviceId("AAAAAAAAA-BBBB-CCCC-1111-222222220000")
                .setSessionId("SID:ANON:w3.org:j6oAOxCWZh/CD723LGeXlf-01:034");

        assertFalse(breinUser.toString().isEmpty());
    }

    /**
     * Tests all BreinUser Methods
     */
    @Test
    public void testBreinUserWithNoMethods() {

        final BreinUser breinUser = new BreinUser("user.anywhere@email.com");
        assertFalse(breinUser.toString().isEmpty());
    }
}
