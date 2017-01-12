package com.brein.api;

import com.brein.domain.*;
import com.brein.engine.BreinEngineType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Properties;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test of Breinify Java API (static option)
 */
public class TestExecutor {

    /**
     * some constants for lookup
     */
    private static final String FIRSTNAME = "firstname";
    private static final String GENDER = "gender";
    private static final String AGE = "age";
    private static final String AGEGROUP = "agegroup";
    private static final String DIGITALFOOTPRINT = "digitalfootprint";
    private static final String IMAGES = "images";

    /**
     * Contains the BASE URL of the Breinify Backend
     */
    private static final String BASE_URL = "https://api.breinify.com";

    /**
     * This has to be a valid api key
     */
    private static final String VALID_API_KEY = "-HAS TO BE A VALID KEY-";

    /**
     * Contains the Breinify User
     */
    private final BreinUser breinUser = new BreinUser("User.Name@email.com");

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
            Thread.sleep(5000);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * testcase how to use the activity api
     */
    @Test
    public void testPageVisit() {

        breinUser.setFirstName("User");
        breinUser.setLastName("Name");
        breinUser.setDateOfBirth(11, 20, 1999);
        breinUser.setDeviceId("DD-EEEEE");
        breinUser.setImei("55544455333");
        breinUser.setSessionId("r3V2kDAvFFL_-RBhuc_-Dg");
        breinUser.setUrl("https://sample.com.au/home");
        breinUser.setReferrer("https://sample.com.au/track");
        breinUser.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");

        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setAndInitRestEngine(BreinEngineType.UNIREST_ENGINE)
                .build();

        final BreinActivity breinActivity = breinifyExecutor.getBreinActivity();

        breinActivity.setUnixTimestamp(Instant.now().getEpochSecond());
        breinActivity.setBreinUser(breinUser);
        breinActivity.setBreinCategoryType(BreinCategoryType.APPAREL);
        breinActivity.setBreinActivityType(BreinActivityType.PAGEVISIT);
        breinActivity.setDescription("your description");

        /*
         * invoke activity call
         */
        breinifyExecutor.activity();
    }

    /**
     * testcase how to use the activity api
     */
    @Test
    public void testLogin() {

        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setAndInitRestEngine(BreinEngineType.UNIREST_ENGINE)
                .build();

        final String description = "your description";

        // invoke activity call
        breinifyExecutor.activity(breinUser,
                BreinActivityType.LOGIN,
                BreinCategoryType.FOOD,
                description,
                null);
    }

    /**
     * testcase how to use the activity api
     */
    @Test
    public void testWitoutCategorySet() {

        // additional user information
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setBaseUrl(BASE_URL)
                .setAndInitRestEngine(BreinEngineType.UNIREST_ENGINE)
                .build();

        final String description = "your description";

        // invoke activity call
        breinifyExecutor.activity(breinUser,
                BreinActivityType.LOGIN,
                null,
                description,
                null);
    }

    /**
     * Tests the lookup functionality
     */
    @Test
    public void testLookup() {

        final String[] dimensions = {
                FIRSTNAME,
                GENDER,
                AGE,
                AGEGROUP,
                DIGITALFOOTPRINT,
                IMAGES
        };

        final BreinDimension breinDimension = new BreinDimension(dimensions);
        final boolean sign = false;

        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setBaseUrl(BASE_URL)
                .setAndInitRestEngine(BreinEngineType.UNIREST_ENGINE)
                .build();

        final BreinUser user = new BreinUser("user.name@email.com");

        // invoke lookup
        try {
            final BreinResult result = breinifyExecutor.lookup(user,
                    breinDimension);

            if (result != null) {
                final Object dataFirstname = result.get(FIRSTNAME);
                final Object dataGender = result.get(GENDER);
                final Object dataAge = result.get(AGE);
                final Object dataAgeGroup = result.get(AGEGROUP);
                final Object dataDigitalFootprinting = result.get(DIGITALFOOTPRINT);
                final Object dataImages = result.get(IMAGES);

            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Test activity call with bad url
     */
    @Test(expected = BreinInvalidConfigurationException.class)
    public void testLoginWithBadUrl() {

        final String badUrl = "www.beeeeeiiiniiify.com";
        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setBaseUrl(badUrl)
                .setAndInitRestEngine(BreinEngineType.UNIREST_ENGINE)
                .build();

        breinifyExecutor.activity(breinUser,
                BreinActivityType.LOGIN,
                BreinCategoryType.FOOD,
                "description",
                null);

    }


    /**
     * testcase how to use the activity api
     */
    @Test
    public void testTemporalData() {

        final BreinUser user = new BreinUser()
                // important new fields
                .setIpAddress("74.115.209.58")
                .setTimezone("America/Los_Angeles")
                .setLocalDateTime("Sun Dec 25 2016 18:15:48 GMT-0800 (PST)");

        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .build();

        try {
            // invoke temporaldata call
            final BreinResult response = breinifyExecutor.temporalData(user);
            final Object timeValues = response.get("time");
            assertTrue(timeValues != null);
            System.out.println(timeValues);
        } catch (final Exception e) {
            fail("REST exception is: " + e);
        }

    }

    /**
     * Testcase how to use recommendation
     */
    @Test
    public void testRecommendation() {

        final BreinUser user = new BreinUser()
                .setEmail("fred.firestone@email.com")
                .setSessionId("1133AADDDEEE");

        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey("XXXX-2506-68B1-45C3-8DCC-B8B8-32D4-9870")
                .setSecret("XXXXeprunt/kgkoe/3b0uw==")
                .build();

        final int numberOfRecommendations = 10;
        final BreinResult result = breinifyExecutor.recommendation(user, numberOfRecommendations);

        // result
        if (result.getStatus() == 200) {
            System.out.println("Message from BreinRecommendation is: " + result.getMessage());

            final ArrayList arrayList = result.get("result");
            assertTrue(arrayList != null);

            arrayList.forEach((value) -> System.out.println("Item : " + value));
        }
    }

    @Test
    public void testForQA() {

        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey("")
                .setAndInitRestEngine(BreinEngineType.UNIREST_ENGINE)
                .build();

    }

}
