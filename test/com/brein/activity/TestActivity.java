package com.brein.activity;

import com.brein.api.BreinActivity;
import com.brein.domain.BreinActivityType;
import com.brein.domain.BreinUser;
import com.brein.domain.BreinCategory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * This test cases shows how to use the activity
 */
public class TestActivity {

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
     * Sign parameter
     */
    private final boolean sign = true;

    /**
     * The Activity itself
     */
    private final BreinActivity breinActivity = new BreinActivity();

    /**
     * Preparation of test case
     */
    @Before
    public void setUp() {

        breinActivity.setApiKey(VALID_API_KEY);
        breinActivity.setBaseUrl(BASE_URL);
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

        /**
         * additional user information
         */
        breinUser.setFirstName("Marco");
        breinUser.setLastName("Recchioni");

        /**
         * invoke activity call
         */
        breinActivity.activity(breinUser,
                BreinActivityType.LOGIN,
                breinCategory, description, sign);
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
        breinActivity.activity(breinUser,
                BreinActivityType.LOOKUP,
                breinCategory, description, sign);

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
        breinActivity.activity(breinUser,
                BreinActivityType.SEARCH,
                breinCategory, description, sign);
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
        breinActivity.activity(breinUser,
                BreinActivityType.ADD_TO_CART,
                breinCategory, description, sign);
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
        breinActivity.activity(breinUser,
                BreinActivityType.REMOVE_FROM_CART,
                breinCategory, description, sign);
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
        breinActivity.activity(breinUser,
                BreinActivityType.SELECT_PRODUCT,
                breinCategory, description, sign);
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
        breinActivity.activity(breinUser,
                BreinActivityType.OTHER,
                breinCategory, description, sign);
    }

    /**
     * TODO
     *
     * SAMPLE:
        {
     "user": {
     "email": "philipp@meisen.net"
     },

     "lookup": {
     "dimensions": ["firstname", "gender", "age", "agegroup", "digitalfootprint", "images"]
     },

     "apiKey": "{{lookupApiKey}}"
     }
     *
     *
     */
    @Test
    public void testLookup() {

    }

}
