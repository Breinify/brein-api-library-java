package com.brein.api;

import com.brein.domain.*;
import com.brein.engine.BreinEngineType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;
import java.util.Properties;

import static org.junit.Assert.assertNotEquals;

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
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * testcase how to use the activity api
     */
    @Test
    public void testPageVisit() {

        /*
         * additional user information
         */
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");
        breinUser.setDateOfBirth(11, 20, 1999);
        breinUser.setDeviceId("DD-EEEEE");
        breinUser.setImei("55544455333");

        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setBaseUrl(BASE_URL)
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE)
                .build();

        final BreinActivity breinActivity = breinifyExecutor.getBreinActivity();

        breinActivity.setUnixTimestamp(Instant.now().getEpochSecond());
        breinActivity.setBreinUser(breinUser);
        breinActivity.setBreinCategoryType(new BreinCategoryType(BreinCategoryType.APPAREL));
        breinActivity.setBreinActivityType(new BreinActivityType(BreinActivityType.PAGEVISIT));
        breinActivity.setDescription("your description");
        breinActivity.setSign(false);
        breinActivity.setIpAddress("11.222.333.444");
        breinActivity.setSessionId("r3V2kDAvFFL_-RBhuc_-Dg");
        breinActivity.setAdditionalUrl("https://sample.com.au/home");
        breinActivity.setReferrer("https://sample.com.au/track");
        breinActivity.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");

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

        /*
         * additional user information
         */
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setBaseUrl(BASE_URL)
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE)
                .build();

        final String description = "your description";
        /*
         * invoke activity call
         */
        breinifyExecutor.activity(breinUser,
                new BreinActivityType(BreinActivityType.LOGIN),
                new BreinCategoryType(BreinCategoryType.FOOD),
                description,
                false);
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
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE)
                .build();

        /*
         * use Philipp, so can get some data
         */
        final BreinUser user = new BreinUser("user.name@email.com");

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
                new BreinActivityType(BreinActivityType.LOGIN),
                new BreinCategoryType(BreinCategoryType.FOOD),
                "description",
                false);

    }

}
