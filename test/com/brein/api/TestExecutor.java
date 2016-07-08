package com.brein.api;

import com.brein.domain.*;
import com.brein.engine.BreinEngineType;
import org.junit.AfterClass;
import org.junit.Test;

/**
 * Test of Breinify Java API (static option)
 */
public class TestExecutor {

    /**
     * some constants for lookup
     */
    final static String FIRSTNAME = "firstname";
    final static String GENDER = "GENDER";
    final static String AGE = "AGE";
    final static String AGEGROUP = "AGEGROUP";
    final static String DIGITALFOOTPRING = "digitalfootpring";
    final static String IMAGES = "IMAGES";

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
    private final BreinCategoryType breinCategoryType = BreinCategoryType.OTHER;

    /**
     * Correct configuration
     */
    final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
            BASE_URL,
            BreinEngineType.UNIREST_ENGINE);

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

        BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setBaseUrl(BASE_URL)
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE)
                .build();

        /**
         * invoke activity call
         */
        breinifyExecutor.activity(breinUser,
                BreinActivityType.LOGIN,
                breinCategoryType,
                description,
                false);
    }

    /**
     * Tests the lookup functionality
     */
    @Test
    public void testLookup() {

       final String[] dimensions = {FIRSTNAME,
                GENDER,
                AGE,
                AGEGROUP,
                DIGITALFOOTPRING,
                IMAGES};

        final BreinDimension breinDimension = new BreinDimension(dimensions);
        final boolean sign = false;

        BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setBaseUrl(BASE_URL)
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE)
                .build();

        /**
         * use Philipp, so can get some data
         */
        final BreinUser user = new BreinUser("philipp@meisen.net");

        /**
         * invoke lookup
         */
        final BreinResult result = breinifyExecutor.lookup(user,
                breinDimension, sign);

        final Object dataFirstname = result.get(FIRSTNAME);
        final Object dataGender = result.get(GENDER);
        final Object dataAge = result.get(AGE);
        final Object dataAgeGroup = result.get(AGEGROUP);
        final Object dataDigitalFootprinting = result.get(DIGITALFOOTPRING);
        final Object dataImages = result.get(IMAGES);


        /**

        if (BreinUtil.containsValue(result.getResponse())) {
            System.out.println("Response is: " + result.getResponse());
        }
        assert (result.getResponse() != null);
         */
    }

}
