package com.brein.api;

import com.brein.config.BreinConfig;
import com.brein.domain.*;
import com.brein.engine.BreinEngineType;
import com.brein.util.BreinUtil;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * Test of Breinify Java API (static option)
 */
public class TestApi {

    /**
     * Contains the BASE URL of the Breinify Backend
     */
    final static String BASE_URL = "http://dev.breinify.com/api";

    /**
     * This has to be a valid api key
     */
    final static String VALID_API_KEY = "A187-B1DF-E3C5-4BDB-93C4-4729-7B54-E5B1";

    /**
     * Contains the Breinify User
     */
    private final BreinUser breinUser = new BreinUser("Marco.Recchioni@breinify.com");

    /**
     * Contains the Category
     */
    private final BreinCategory breinCategory = new BreinCategory("services");

    /**
     * Preparation of test case
     */
    @Before
    public void setUp() {
        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE);
        Breinify.setConfig(breinConfig);
    }

    /**
     * Housekeeping...
     */
    @AfterClass
    public static void tearDown() {
        /**
         * we have to wait some time in order to allow the asynch rest processing
         */
        try {
            /**
             * TODO...
             * Thread.sleep is not the best practice...
             *
             */
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * testcase how to use the activity api
     */
    @Test
    public void testLogin() {

        final String description = "Login-Description";
        final boolean sign = false;

        /**
         * additional user information
         */
        breinUser.setFirstName("Marco");
        breinUser.setLastName("Recchioni");

        /**
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.LOGIN,
                breinCategory,
                description,
                sign);
    }

    /**
     * Invoke a test call with 200 logins
     */
    @Test
    public void testWith200Logins() {

        final int maxLogin = 200;

        for (int index = 0; index < maxLogin; index++) {
            System.out.println("INDEX IS: " + index);
            testLogin();
        }

    }

    /**
     * test case how to invoke logout activity
     */
    @Test
    public void testLogout() {

        final String description = "Logout-Description";

        /**
         * additional user information
         */
        breinUser.setDateOfBirth("12/31/2008");

        /**
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.LOGOUT,
                breinCategory,
                description,
                false);
    }

    /**
     * TODO
     */
    @Test
    public void testSearch() {

        final String description = "Search-Description";

        /**
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.SEARCH,
                breinCategory,
                description,
                false);
    }

    /**
     * TODO
     */
    @Test
    public void testAddToCart() {

        final String description = "AddToCart-Description";

        /**
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.ADD_TO_CART,
                breinCategory,
                description,
                false);
    }

    /**
     * TODO
     */
    @Test
    public void testRemoveFromCart() {

        final String description = "RemoveFromCart-Description";

        /**
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.REMOVE_FROM_CART,
                breinCategory,
                description,
                false);
    }

    /**
     * TODO
     */
    @Test
    public void testSelectProduct() {

        final String description = "Select-Product-Description";

        /**
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.SELECT_PRODUCT,
                breinCategory,
                description,
                false);
    }

    /**
     * TODO
     */
    @Test
    public void testOther() {

        final String description = "Other-Description";

        /**
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.OTHER,
                breinCategory,
                description,
                false);
    }


    /**
     * Tests the lookup functionality
     */
    @Test
    public void testLookup() {

        final String[] dimensions = {"firstname",
                "gender",
                "age",
                "agegroup",
                "digitalfootpring",
                "images"};

        final BreinDimension breinDimension = new BreinDimension(dimensions);
        final boolean sign = false;

        /**
         * invoke lookup
         */
        final BreinResponse response = Breinify.lookup(breinUser, breinDimension, sign);
        if (BreinUtil.containsValue(response.getResponse())) {
            System.out.println("Response is: " + response.getResponse());
        }
        assert (response.getResponse() != null);
    }

}
