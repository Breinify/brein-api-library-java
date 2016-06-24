package com.brein.activity;

import com.brein.api.BreinActivity;
import com.brein.domain.ActivityType;
import com.brein.domain.BreinifyUser;
import com.brein.domain.Category;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * This test cases shows how to use the activity
 */
public class TestActivity {

    /**
     * User
     */
    private final BreinifyUser breinifyUser = new BreinifyUser("Marco.Recchioni@breinify.com");

    /**
     * Category
     */
    private final Category category = new Category("services");

    /**
     * Sign parameter
     */
    private final boolean sign = true;

    /**
     * Activity
     */
    private BreinActivity breinActivity = new BreinActivity();

    /**
     * Preparation of test case
     */
    @Before
    public void setUp() {

        final String validApiKey = "9D9C-C9E9-BC93-4D1D-9A61-3A0F-9BD9-CF14";
        breinActivity.setApiKey(validApiKey);
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
        breinifyUser.setFirstName("Marco");
        breinifyUser.setLastName("Recchioni");

        /**
         * invoke activity call
         */
        breinActivity.activity(breinifyUser,
                ActivityType.LOGIN,
                category, description, sign);
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
        breinifyUser.setDateOfBirth("12/31/2008");

        /**
         * invoke activity call
         */
        breinActivity.activity(breinifyUser,
                ActivityType.LOOKUP,
                category, description, sign);

    }

    /**
     *
     */
    @Test
    public void testLookup() {

    }

}
