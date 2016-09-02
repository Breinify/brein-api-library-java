package com.brein.activity;

import com.brein.api.BreinActivity;
import com.brein.domain.BreinActivityType;
import com.brein.domain.BreinCategoryType;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinUser;
import com.brein.engine.BreinEngineType;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * This test cases shows how to use the  activity
 */
public class TestActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestActivity.class);

    /**
     * Contains the BASE URL of the Breinify Backend
     */
    private static final String BASE_URL = "http://dev.breinify.com/api";

    /**
     * This has to be a valid api key
     */
    private static final String VALID_API_KEY = "A187-B1DF-E3C5-4BDB-93C4-4729-7B54-E5B1";

    /**
     * Contains the Breinify User
     */
    private final BreinUser breinUser = new BreinUser("User.Name@email.com");

    /**
     * Contains the Category
     */
    private final BreinCategoryType breinCategoryType = BreinCategoryType.SERVICES;

    /**
     * Sign parameter
     */
    private final boolean sign = false;

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

        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE);

        breinActivity.setConfig(breinConfig);
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
                breinCategoryType, description, sign);
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

        /*
         * additional user information
         */
        breinUser.setDateOfBirth(12, 31, 2008);

        /*
         * invoke activity call
         */
        breinActivity.activity(breinUser,
                BreinActivityType.LOGOUT,
                breinCategoryType, description, sign);
    }

    /**
     * test case how to invoke search activity
     */
    @Test
    public void testSearch() {

        final String description = "Search-Description";

        /*
         * invoke activity call
         */
        breinActivity.activity(breinUser,
                BreinActivityType.SEARCH,
                breinCategoryType, description, sign);
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
                breinCategoryType, description, sign);
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
                breinCategoryType, description, sign);
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
                breinCategoryType, description, sign);
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
                breinCategoryType, description, sign);
    }



}
