package com.brein.api;

import com.brein.domain.*;
import com.brein.engine.BreinEngineType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertNotEquals;

/**
 * Test of Breinify Java API (static option)
 */
public class TestExecutor {

    /**
     * some constants for lookup
     */
    final static String FIRSTNAME = "firstname";
    final static String GENDER = "gender";
    final static String AGE = "age";
    final static String AGEGROUP = "agegroup";
    final static String DIGITALFOOTPRINT = "digitalfootprint";
    final static String IMAGES = "images";

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
     * Init part
     */
    @BeforeClass
    public static void init() {

        // set logging on
        final Properties props = System.getProperties();
        props.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");

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
            /*
             * TODO...
             * Thread.sleep is not the best practice...
             */
            Thread.sleep(10000);
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

        /*
         * additional user information
         */
        breinUser.setFirstName("Marco");
        breinUser.setLastName("Recchioni");

        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setBaseUrl(BASE_URL)
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE)
                .build();

        /*
         * invoke activity call
         */
        breinifyExecutor.activity(breinUser,
                BreinActivityType.LOGIN,
                BreinCategoryType.FOOD,
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
                DIGITALFOOTPRINT,
                IMAGES};

        final BreinDimension breinDimension = new BreinDimension(dimensions);
        final boolean sign = false;

        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setBaseUrl(BASE_URL)
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE)
                .build();

        /*
         * use Philipp, so can get some data
         */
        final BreinUser user = new BreinUser("philipp@meisen.net");

        /*
         * invoke lookup
         */
        final BreinResult result = breinifyExecutor.lookup(user,
                breinDimension, sign);

        final Object dataFirstname = result.get(FIRSTNAME);
        final Object dataGender = result.get(GENDER);
        final Object dataAge = result.get(AGE);
        final Object dataAgeGroup = result.get(AGEGROUP);
        final Object dataDigitalFootprinting = result.get(DIGITALFOOTPRINT);
        final Object dataImages = result.get(IMAGES);

        assertNotEquals(null, dataFirstname);
    }

    /**
     * Test activity call with bad url
     */
    @Test(expected = BreinInvalidConfigurationException.class)
    public void testLoginWithBadUrl() {

        /*
         * Just to ensure that the right config has been set
         */
        final String badUrl = "www.beeeeeiiiniiify.com";

        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setBaseUrl(badUrl)
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE)
                .build();

        breinifyExecutor.activity(breinUser,
                BreinActivityType.LOGIN,
                BreinCategoryType.FOOD,
                "description",
                false);

    }

}
