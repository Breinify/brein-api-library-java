package com.brein.api;

import com.brein.domain.*;
import com.brein.engine.BreinEngine;
import com.brein.engine.BreinEngineType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Test of Breinify Java API (static option)
 */
public class TestApi {

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
    private final BreinCategoryType breinCategoryType = BreinCategoryType.HOME;

    /**
     * Correct configuration
     */
    final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
            BASE_URL,
            BreinEngineType.UNIREST_ENGINE);

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
     * Housekeeping...
     */
    @AfterClass
    public static void tearDown() {

        /*
         * we have to wait some time in order to allow the asynch rest processing
         */
        try {
            Thread.sleep(1000);
            Breinify.shutdown();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * testcase how to use the activity api
     */
    @Test
    public void testLogin() {

        /*
         * set configuration
         */
        Breinify.setConfig(breinConfig);

        /*
         * additional optional user information
         */
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        /*
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.LOGIN,
                BreinCategoryType.HOME,
                "Login-Description",
                false);
    }

    /**
     * Testcase with null value as apikey
     */
    @Test
    public void testLoginWithNullApiKey() {

        final String description = "Login-Description";
        final boolean sign = false;

        final BreinConfig config = new BreinConfig(null,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE);

        Breinify.setConfig(config);

        /*
         * additional user information
         */
        breinUser.setFirstName("User")
                .setLastName("Name");

        /*
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.LOGIN,
                breinCategoryType,
                description,
                sign);
    }

    /**
     * Testcase with null value as base url
     */
    @Test(expected = BreinInvalidConfigurationException.class)
    public void testWithNullBaseUrl() {

        final BreinConfig config = new BreinConfig(VALID_API_KEY,
                null,
                BreinEngineType.UNIREST_ENGINE);

        Breinify.setConfig(config);

    }

    /**
     * Testcase with null rest engine. This will throw an
     * exception.
     */
    @Test(expected = BreinException.class)
    public void testLoginWithDefaultRestEngine() {

        final String description = "Login-Description";
        final boolean sign = false;


        BreinConfig config = null;
        try {
            config = new BreinConfig(VALID_API_KEY,
                    BASE_URL,
                    BreinEngineType.NO_ENGINE);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        Breinify.setConfig(config);

        /*
         * additional user information
         */
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");


        /*
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.LOGIN,
                breinCategoryType,
                description,
                sign);

    }

    /**
     * Test case with wrong endpoint configuration
     */
    @Test
    public void testWithWrongEndPoint() {

        final String description = "Login-Description";

        /*
         * set configuration
         */
        final BreinConfig config = new BreinConfig(VALID_API_KEY,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE);
        config.setActivityEndpoint("/wrongEndPoint");

        Breinify.setConfig(config);

        /*
         * additional user information
         */
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        /*
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.LOGIN,
                breinCategoryType,
                description,
                false);
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
     * test case how to invoke logout activity
     */
    @Test
    public void testLogout() {

        final String description = "Logout-Description";

        /*
         * set configuration
         */
        Breinify.setConfig(breinConfig);

        /*
         * additional user information
         */
        breinUser.setDateOfBirth(12, 31, 2008);

        /*
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.LOGOUT,
                breinCategoryType,
                description,
                false);
    }

    /**
     * test case how to invoke search activity
     */
    @Test
    public void testSearch() {

        final String description = "Search-Description";

        /*
         * set configuration
         */
        Breinify.setConfig(breinConfig);

        /*
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.SEARCH,
                breinCategoryType,
                description,
                false);
    }

    /**
     * test case how to invoke addToCart activity
     */
    @Test
    public void testAddToCart() {

        final String description = "AddToCart-Description";

        /*
         * set configuration
         */
        Breinify.setConfig(breinConfig);

        /*
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.ADD_TO_CART,
                breinCategoryType,
                description,
                false);
    }

    /**
     * test case how to invoke removeFromCart activity
     */
    @Test
    public void testRemoveFromCart() {

        final String description = "RemoveFromCart-Description";

        /*
         * set configuration
         */
        Breinify.setConfig(breinConfig);

        /*
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.REMOVE_FROM_CART,
                breinCategoryType,
                description,
                false);
    }

    /**
     * test case how to invoke selectProduct activity
     */
    @Test
    public void testSelectProduct() {

        final String description = "Select-Product-Description";

        /*
         * set configuration
         */
        Breinify.setConfig(breinConfig);

        /*
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.SELECT_PRODUCT,
                breinCategoryType,
                description,
                false);
    }

    /**
     * test case how to invoke other activity
     */
    @Test
    public void testOther() {

        final String description = "Other-Description";

        /*
         * set configuration
         */
        Breinify.setConfig(breinConfig);

        /*
         * invoke activity call
         */
        Breinify.activity(breinUser,
                BreinActivityType.OTHER,
                breinCategoryType,
                description,
                false);
    }

    /**
     * test case containing additional information
     */
    @Test
    public void testPageVisit() {

        // set configuration
        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE);
        Breinify.setConfig(breinConfig);

        // user data
        final BreinUser breinUser = new BreinUser("User.Name@email.com")
                .setFirstName("Marco")
                .setLastName("Recchioni")
                .setDateOfBirth(11, 20, 1999)
                .setDeviceId("DD-EEEEE")
                .setImei("55544455333");

        final Map<String, String> tagMap = new HashMap<>();
        tagMap.put("t1", "0.0");
        tagMap.put("t2", "0.0");
        tagMap.put("t3", "0.0");
        tagMap.put("t4", "0.0");
        tagMap.put("nr", "1.0");
        tagMap.put("sortid", "1.0");

        final BreinActivity breinActivity = Breinify.getBreinActivity();

        breinActivity.setUnixTimestamp(Instant.now().getEpochSecond());
        breinActivity.setBreinUser(breinUser);
        breinActivity.setBreinCategoryType(BreinCategoryType.APPAREL);
        breinActivity.setBreinActivityType(BreinActivityType.PAGEVISIT);
        breinActivity.setDescription("your description");
        breinActivity.setSign(false);
        breinActivity.setTagsMap(tagMap);
        breinActivity.setIpAddress("11.222.333.444");
        breinActivity.setSessionId("r3V2kDAvFFL_-RBhuc_-Dg");
        breinActivity.setAdditionalUrl("https://sample.com.au/home");
        breinActivity.setReferrer("https://sample.com.au/track");
        breinActivity.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");

        Breinify.activity(breinActivity);
    }


    /**
     * test case containing additional information
     */
    @Test(expected = BreinException.class)
    public void testPageVisitWithException() {

        // set configuration
        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE);
        Breinify.setConfig(breinConfig);

        // user data
        final BreinUser breinUser = new BreinUser("User.Name@email.com");
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");
        breinUser.setDateOfBirth(11, 20, 1999);
        breinUser.setDeviceId("DD-EEEEE");
        breinUser.setImei("55544455333");

        final BreinActivity breinActivity = Breinify.getBreinActivity();

        breinActivity.setBreinCategoryType(BreinCategoryType.APPAREL);
        breinActivity.setBreinActivityType(BreinActivityType.PAGEVISIT);
        breinActivity.setDescription("your description");
        breinActivity.setSign(false);
        breinActivity.setIpAddress("11.222.333.444");
        breinActivity.setSessionId("r3V2kDAvFFL_-RBhuc_-Dg");
        breinActivity.setAdditionalUrl("https://sample.com.au/home");
        breinActivity.setReferrer("https://sample.com.au/track");
        breinActivity.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");

        // user not set -> exception expected
        Breinify.activity(breinActivity);
    }

    /**
     * simply demonstrate the configuration of the engine
     */
    @Test
    public void testConfiguration() {

        final BreinEngine breinEngine = breinConfig.getBreinEngine();

        /*
         * set connection timeout to 30000 ms
         */
        breinConfig.setConnectionTimeout(30000);

        /*
         * set socket timeoiut to 25000 ms
         */
        breinConfig.setSocketTimeout(25000);

        /*
         * configure the engine
         */
        breinEngine.configure(breinConfig);
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

        final BreinConfig breinConfigWithBadUrl = new BreinConfig(VALID_API_KEY,
                badUrl,
                BreinEngineType.UNIREST_ENGINE);

        Breinify.setConfig(breinConfigWithBadUrl);

        /*
         * invoke activity call, will cause an exception
         */
        Breinify.activity(breinUser,
                BreinActivityType.LOGIN,
                BreinCategoryType.HOME,
                "Login-Description",
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
                "digitalfootprint",
                "images"};

        final BreinDimension breinDimension = new BreinDimension(dimensions);
        final boolean sign = false;

        /*
         * set configuration
         */
        Breinify.setConfig(breinConfig);

        /*
         * invoke lookup
         */
        final BreinResult response = Breinify.lookup(breinUser, breinDimension, sign);

        final Object dataFirstname = response.get("firstname");
        final Object dataGender = response.get("gender");
        final Object dataAge = response.get("age");
        final Object dataAgeGroup = response.get("agegroup");
        final Object dataDigitalFootprinting = response.get("digitalfootprint");
        final Object dataImages = response.get("images");
    }

    /**
     * Test a login activity with sign and correct secret
     */
    @Test
    public void testLoginWithSign() {

        final String secret = "p3rqlab6m7/172pdgiq6ng==";
        final boolean sign = true;

        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE);

        // set secret
        breinConfig.setSecret(secret);

        // set config
        Breinify.setConfig(breinConfig);

        // invoke activity call
        Breinify.activity(breinUser,
                BreinActivityType.LOGIN,
                BreinCategoryType.HOME,
                "Login-Description",
                sign);

    }

    /**
     * Test a login activity with sign but wrong secret
     */
    @Test
    public void testLoginWithSignButWrongSecret() {

        final String wrongSecret = "ThisIsAWrongSecret";
        final boolean sign = true;

        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE);

        // set secret
        breinConfig.setSecret(wrongSecret);

        // set config
        Breinify.setConfig(breinConfig);

        // invoke activity call
        Breinify.activity(breinUser,
                BreinActivityType.LOGIN,
                BreinCategoryType.HOME,
                "Login-Description",
                sign);
    }

    /**
     * Test a lookup with sign and correct secret
     */
    @Test
    public void testLookupWithSign() {

        final String secret = "p3rqlab6m7/172pdgiq6ng==";
        final String[] dimensions = {"firstname",
                "gender",
                "age",
                "agegroup",
                "digitalfootprint",
                "images"};

        final BreinDimension breinDimension = new BreinDimension(dimensions);
        final boolean sign = true;

        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE);

        // set secret
        breinConfig.setSecret(secret);

        /*
         * set configuration
         */
        Breinify.setConfig(breinConfig);

        /*
         * invoke lookup
         */
        final BreinResult response = Breinify.lookup(breinUser, breinDimension, sign);

        final Object dataFirstname = response.get("firstname");
        final Object dataGender = response.get("gender");
        final Object dataAge = response.get("age");
        final Object dataAgeGroup = response.get("agegroup");
        final Object dataDigitalFootprinting = response.get("digitalfootprint");
        final Object dataImages = response.get("images");
    }

    /**
     * Test a lookup with sign and correct secret
     */
    @Test
    public void testLookupWithSignButWrongSecret() {

        final String thisIsAWrongSecret = "ThisIsAWrongSecret";
        final String[] dimensions = {"firstname",
                "gender",
                "age",
                "agegroup",
                "digitalfootprint",
                "images"};

        final BreinDimension breinDimension = new BreinDimension(dimensions);
        final boolean sign = true;

        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE);

        // set secret
        breinConfig.setSecret(thisIsAWrongSecret);

        /*
         * set configuration
         */
        Breinify.setConfig(breinConfig);

        /*
         * invoke lookup
         */
        final BreinResult response = Breinify.lookup(breinUser, breinDimension, sign);

        final Object dataFirstname = response.get("firstname");
        final Object dataGender = response.get("gender");
        final Object dataAge = response.get("age");
        final Object dataAgeGroup = response.get("agegroup");
        final Object dataDigitalFootprinting = response.get("digitalfootprint");
        final Object dataImages = response.get("images");
    }

}
