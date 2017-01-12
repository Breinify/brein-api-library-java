package com.brein.activity;

import com.brein.api.BreinActivity;
import com.brein.domain.BreinActivityType;
import com.brein.domain.BreinCategoryType;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinUser;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

/**
 * This test cases shows how to use the  activity
 */
public class TestActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestActivity.class);

    /**
     * Contains the BASE URL of the Breinify Backend
     */
    // private static final String BASE_URL = "http://dev.breinify.com/api";

    private static final String BASE_URL = "https://api.breinify.com";

    /**
     * This has to be a valid api key
     */
    private static final String VALID_API_KEY = "772A-47D7-93A3-4EA9-9D73-85B9-479B-16C6";

    /**
     * Contains the Breinify User
     */
    private final BreinUser breinUser = new BreinUser("User.Name@email.com");

    /**
     * Contains the Category
     */
    private final String breinCategoryType = BreinCategoryType.SERVICES;

    /**
     * The Activity itself
     */
    private final BreinActivity breinActivity = new BreinActivity();

    /**
     * Init part
     */
    @BeforeClass
    public static void init() {

        // set logging on
        final Properties props = System.getProperties();
        props.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");

    }

    /**
     * Preparation of test case
     */
    @Before
    public void setUp() {

        // set logging on
        final Properties props = System.getProperties();
        props.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");

        breinActivity.setConfig(new BreinConfig(VALID_API_KEY));
    }

    /**
     * Housekeeping...
     */
    @AfterClass
    public static void tearDown() {
        /*
         * we have to wait some time in order to allow the asynch rest processing
         */
        try {

            Thread.sleep(4000);

        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * testcase how to use the activity api
     */
    @Test
    public void testLogin() {

        final String description = "Login-Description";

        /*
         * additional user information
         */
        breinUser.setFirstName("Marco");
        breinUser.setLastName("Recchioni");

        /*
         * invoke activity call
         */
        breinActivity.activity(breinUser,
                BreinActivityType.LOGIN,
                breinCategoryType, description);
    }

    /**
     * test case without category type
     */
    @Test
    public void testWithoutCategoryTypeSet() {

        final String description = "Login-Description";

        /*
         * additional user information
         */
        breinUser.setFirstName("Marco");
        breinUser.setLastName("Recchioni");

        /*
         * invoke activity call
         */
        breinActivity.activity(breinUser,
                BreinActivityType.LOGIN,
                null, description);
    }


    /**
     * Invoke a test call with 200 logins
     */
    @Test
    public void testWith200Logins() {

        final int maxLogin = 200;

        for (int index = 0; index < maxLogin; index++) {
            testLogin();
        }

    }

    /**
     * Invoke a test call with 2000 logins
     */
    @Test
    public void testWith2000Logins() {

        final int maxLogin = 2000;

        for (int index = 0; index < maxLogin; index++) {
            testLogin();
        }

    }

    /**
     * test case how to invoke logout activity
     */
    @Test
    public void testLogout() {

        final String description = "Logout-Description";

        // additional user information
        breinUser.setDateOfBirth(12, 31, 2008);

        // invoke activity call
        breinActivity.activity(breinUser,
                BreinActivityType.LOGOUT,
                breinCategoryType, description);
    }

    /**
     * test case how to invoke search activity
     */
    @Test
    public void testSearch() {

        final String description = "Search-Description";

        // invoke activity call
        breinActivity.activity(breinUser,
                BreinActivityType.SEARCH,
                breinCategoryType, description);
    }

    /**
     * test case how to invoke add-to-cart activity
     */
    @Test
    public void testAddToCart() {

        final String description = "AddToCart-Description";

        /*
         * invoke activity call
         */
        breinActivity.activity(breinUser,
                BreinActivityType.ADD_TO_CART,
                breinCategoryType, description);
    }

    /**
     * test case how to invoke remove-from-cart activity
     */
    @Test
    public void testRemoveFromCart() {

        final String description = "RemoveFromCart-Description";

        /*
         * invoke activity call
         */
        breinActivity.activity(breinUser,
                BreinActivityType.REMOVE_FROM_CART,
                breinCategoryType, description);
    }

    /**
     * test case how to invoke select product
     */
    @Test
    public void testSelectProduct() {

        final String description = "Select-Product-Description";

        /*
         * invoke activity call
         */
        breinActivity.activity(breinUser,
                BreinActivityType.SELECT_PRODUCT,
                breinCategoryType, description);
    }

    /**
     * test case how to invoke other
     */
    @Test
    public void testOther() {

        final String description = "Other-Description";

        /*
         * invoke activity call
         */
        breinActivity.activity(breinUser,
                BreinActivityType.OTHER,
                breinCategoryType, description);
    }

    /**
     * Testcase that proves that all attributes of class BreinActivity
     * are set to empty values
     */
    @Test
    public void testResetAllValues() {

        final BreinActivity breinActivity = new BreinActivity();
        breinActivity.setBreinActivityType(BreinActivityType.ADD_TO_CART);
        breinActivity.setBreinCategoryType(BreinCategoryType.EDUCATION);
        breinActivity.setDescription("Description");

        final Map<String, Object> tagMap = new HashMap<>();
        tagMap.put("t1", "0.0");
        breinActivity.setTags(tagMap);
        breinActivity.setBreinUser(new BreinUser("user.name@email.com"));
        breinActivity.setConfig(new BreinConfig("KEY"));

        // first check init
        breinActivity.init();
        assertTrue("breinActivityType is not empty", breinActivity.getBreinActivityType().equals(""));
        assertTrue("breinCategoryType is not empty", breinActivity.getBreinCategoryType().equals(""));
        assertTrue("description is not empty", breinActivity.getDescription().equals(""));
        assertTrue("tags lib is not null", breinActivity.getTags() == null);

        // breinUser and breinConfig should be valid
        assertTrue("breinUser is null!", breinActivity.getBreinUser() != null);
        assertTrue("breinConfig is null!", breinActivity.getConfig() != null);

        // now invoke resetAllValues, this will delete breinUser and breinConfig as well
        breinActivity.resetAllValues();

        // breinUser and breinConfig should now be null
        assertTrue("breinUser is not null!", breinActivity.getBreinUser() == null);
        assertTrue("breinConfig is not null!", breinActivity.getConfig() == null);
    }
}
