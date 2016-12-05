package com.brein.api;

import com.brein.domain.*;
import com.brein.engine.BreinEngine;
import com.brein.engine.BreinEngineType;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test of Breinify Java API (static option)
 */
public class TestJerseyApi {

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
    private final BreinUser breinUser = new BreinUser("user.name@email.com");

    /**
     * Contains the Category
     */
    private final String breinCategoryType = BreinCategoryType.HOME;

    /**
     * Contains the BreinActivityType
     */
    private final String breinActivityType = BreinActivityType.LOGIN;

    /**
     * Correct configuration
     */
    final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY ).setAndInitRestEngine(BreinEngineType.JERSEY_ENGINE);

    /**
     * indicator if the loops should be done forever :-)
     */
    boolean loopIndicator = true;

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
     * setup for each test
     */
    @Before
    public void setUp() {
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
            Thread.sleep(4000);
            Breinify.shutdown();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void invokeActivityCall(final BreinUser breinUser,
                                   final String breinActivityType,
                                   final String breinCategoryType,
                                   final String description) {


        final Function<String, Void> callback = message -> {
            fail(message);
            return null;
        };


        // invoke activity call
        Breinify.activity(breinUser,
                breinActivityType,
                breinCategoryType,
                description,
                callback);

    }

    @Test
    public void testLoginWithCallback() {

        // set configuration
        Breinify.setConfig(breinConfig);

        // additional optional user information
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        invokeActivityCall(breinUser, BreinActivityType.LOGIN, BreinCategoryType.HOME, "desc");
    }

    @Test
    public void test200LoginWithCallback() {
        // set configuration
        Breinify.setConfig(breinConfig);

        // additional optional user information
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        for (int index = 0; index < 200; index++) {
            System.out.println("index is: " + index);
            invokeActivityCall(breinUser, BreinActivityType.LOGIN, BreinCategoryType.HOME, "desc");
        }
    }

    /**
     * testcase how to use the activity api
     */
    @Test
    public void testLogin() {

        // set configuration
        Breinify.setConfig(breinConfig);

        // additional optional user information
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        // invoke activity call
        Breinify.activity(breinUser,
                breinActivityType,
                breinCategoryType,
                "Login-Description",
                null);
    }

    /**
     * Testcase with null value as apikey
     */
    @Test
    public void testLoginWithNullApiKey() {

        final String description = "Login-Description";
        final BreinConfig config = new BreinConfig();

        Breinify.setConfig(config);

        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        Breinify.activity(breinUser,
                breinActivityType,
                breinCategoryType,
                description,
                null);
    }

    /**
     * Testcase with null value as base url
     */
    @Test(expected = BreinInvalidConfigurationException.class)
    public void testLoginWithNullBaseUrl() {

        final String description = "Login-Description";
        final BreinConfig config = new BreinConfig(VALID_API_KEY).setBaseUrl(null);

        Breinify.setConfig(config);

        // additional user information
        breinUser.setFirstName("User")
                .setLastName("Name");

        // invoke activity call
        Breinify.activity(breinUser,
                breinActivityType,
                breinCategoryType,
                description,
                null);
    }

    /**
     * Testcase with null rest engine. This will throw an
     * exception.
     */
    @Test(expected = BreinException.class)
    public void testLoginWithNoRestEngine() {

        final String description = "Login-Description";
        final BreinConfig config = new BreinConfig(VALID_API_KEY).setAndInitRestEngine(BreinEngineType.NO_ENGINE);

        Breinify.setConfig(config);

        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        Breinify.activity(breinUser,
                breinActivityType,
                breinCategoryType,
                description,
                null);
    }

    /**
     * Test case with wrong endpoint configuration
     */
    @Test
    public void testWithWrongEndPoint() {

        final String description = "Login-Description";

        // set configuration
        final BreinConfig config = new BreinConfig(VALID_API_KEY);
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
                breinActivityType,
                breinCategoryType,
                description,
                null);


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
                null);
    }

    /**
     * test case with search call
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
                null);
    }

    /**
     * test case with addToCart call
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
                null);
    }

    /**
     * test case with removeFromCart call
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
                null);
    }

    /**
     * test case with selectProduct call
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
                null);
    }

    /**
     * test case with other call
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
                null);
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
         * set socket timeout to 25000 ms
         */
        breinConfig.setSocketTimeout(25000);

        /*
         * configure the engine
         */
        breinEngine.configure(breinConfig);
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

        // set configuration
        Breinify.setConfig(breinConfig);

        try {
            // invoke lookup
            final BreinResult response = Breinify.lookup(breinUser, breinDimension);

            showLookupResult(response);
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * helper method to show lookup result
     *
     * @param response contains the lookup data
     */
    public void showLookupResult(final BreinResult response) {
        if (response != null) {
            final Object dataFirstname = response.get("firstname");
            final Object dataGender = response.get("gender");
            final Object dataAge = response.get("age");
            final Object dataAgeGroup = response.get("agegroup");
            final Object dataDigitalFootprinting = response.get("digitalfootprint");
            final Object dataImages = response.get("images");
        }
    }

    /**
     * test case how to use the activity api
     */
    // @Test
    public void testLoginWithPauseToCheckWifiIssue() {

        // set configuration
        Breinify.setConfig(breinConfig);

        // additional optional user information
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        final Function<String, Void> errorCallback = s -> {
            loopIndicator = false;
            return null;
        };


        int index = 1;

        while (loopIndicator) {

            try {

                while (loopIndicator) {

                    // invoke activity call
                    Breinify.activity(breinUser,
                            breinActivityType,
                            breinCategoryType,
                            "Login-Description",
                            errorCallback);

                    try {
                        System.out.println("Waiting: " + Integer.toString(index++));
                        Thread.sleep(2000);

                    } catch (final Exception e) {
                        fail("Rest Exception is: " + e);
                    }

                }

            } catch (final BreinException e) {
                fail("Rest Exception is: " + e);
            }
        }

    }

    /**
     * Test a login activity with sign and correct secret
     */
    @Test
    public void testLoginWithSign() {

        // final String secret = "p3rqlab6m7/172pdgiq6ng==";
        final String secret = "lmcoj4k27hbbszzyiqamhg==";
        final String secretApiKey = "CA8A-8D28-3408-45A8-8E20-8474-06C0-8548";
        final BreinConfig breinConfig = new BreinConfig(secretApiKey, secret);

        // set config
        Breinify.setConfig(breinConfig);

        // invoke activity call
        invokeActivityCall(breinUser, BreinActivityType.LOGIN, BreinCategoryType.HOME, "Login-Desc");
    }


    /**
     * test case with extra fields on base level
     */
    @Test
    public void testActivityRequestWithExtraBaseMap() {

        final Map<String, Object> dataBaseLevelMap = new HashMap<>();
        dataBaseLevelMap.put("enhancement-base", "value");

        final BreinActivity breinActivity = Breinify.getBreinActivity();
        breinActivity.setBase(dataBaseLevelMap);

        callLoginWithStandardValues();
    }

    /**
     * test case with extra fields on activity level
     */
    @Test
    public void testActivityRequestWithExtraActivityMap() {

        final Map<String, Object> dataActivityMap = new HashMap<>();
        dataActivityMap.put("enhancement-activity-1", "value-1");
        dataActivityMap.put("enhancement-activity-2", "value-2");

        final BreinActivity breinActivity = Breinify.getBreinActivity();
        breinActivity.set(dataActivityMap);

        callLoginWithStandardValues();
    }

    /**
     * test case with extra fields on activity level
     */
    @Test
    public void testActivityRequestWithExtraActivityMapMap() {

        final Map<String, Object> dataActivityMap = new HashMap<>();
        dataActivityMap.put("enhancement-activity-1", "value-1");
        dataActivityMap.put("enhancement-activity-2", "value-2");
        final Map<String, Object> dataRootActivityMap = new HashMap<>();
        dataRootActivityMap.put("activityRoot", dataActivityMap);

        final BreinActivity breinActivity = Breinify.getBreinActivity();
        breinActivity.set(dataRootActivityMap);

        callLoginWithStandardValues();
    }

    /**
     * test case with extra fields on activity level
     */
    @Test
    public void testActivityRequestWithExtraActivityMapMapMap() {

        final Map<String, Object> dataActivityLeftMap = new HashMap<>();
        dataActivityLeftMap.put("enhancement-activity-Left-1", "value-1-l");
        dataActivityLeftMap.put("enhancement-activity-Left-2", "value-2-l");
        final Map<String, Object> dataRootLeftActivityMap = new HashMap<>();
        dataRootLeftActivityMap.put("activityRootLeft", dataActivityLeftMap);


        final Map<String, Object> dataActivityRightMap = new HashMap<>();
        dataActivityRightMap.put("enhancement-activity-Right-1", "value-1-r");
        dataActivityRightMap.put("enhancement-activity-Right-2", "value-2-r");
        final Map<String, Object> dataRootRightActivityMap = new HashMap<>();
        dataRootRightActivityMap.put("activityRootLeft", dataActivityRightMap);

        final Map<String, Object> dataRootMap = new HashMap<>();
        dataRootMap.put("Left", dataRootLeftActivityMap);
        dataRootMap.put("Right", dataRootRightActivityMap);

        final BreinActivity breinActivity = Breinify.getBreinActivity();
        breinActivity.set(dataRootMap);

        callLoginWithStandardValues();
    }


    /**
     * test case with extra fields on activity level
     */
    @Test
    public void testActivityRequestWithExtraUserMap() {

        Breinify.setConfig(breinConfig);

        final Map<String, Object> dataUserMap = new HashMap<>();
        dataUserMap.put("enhancement-user-1", "user-value-1");
        dataUserMap.put("enhancement-user-2", "user-value-2");

        final BreinUser localBreinUser = new BreinUser()
                .setEmail("fred.firestone@email.com")
                .set(dataUserMap);

        invokeActivityCall(localBreinUser,
                breinActivityType,
                breinCategoryType,
                "login");
    }

    /**
     * test case with extra fields on activity level
     */
    @Test
    public void testActivityRequestWithExtraUserAdditionalMap() {

        final Map<String, Object> dataAdditionalMap = new HashMap<>();
        dataAdditionalMap.put("enhancement-user-additional-1", "user-add-value-1");
        dataAdditionalMap.put("enhancement-user-additional-2", "user-add-value-2");

        final BreinUser localBreinUser = new BreinUser()
                .setEmail("fred.firestone@email.com")
                .setAdditional(dataAdditionalMap);

        Breinify.setConfig(breinConfig);

        invokeActivityCall(localBreinUser,
                breinActivityType,
                breinCategoryType,
                "login");
    }

    /**
     * Test case with user additional map containing a map as an object
     */
    @Test
    public void testActivityRequestWithLocationAdditionalMap() {

        final Map<String, Object> locationAdditionalMap = new HashMap<>();
        final Map<String, Object> locationValueMap = new HashMap<>();

        locationValueMap.put("latitude", "32.7157");
        locationValueMap.put("longitude", "-117.1611");
        locationAdditionalMap.put("location", locationValueMap);

        final BreinUser localBreinUser = new BreinUser()
                .setEmail("fred.firestone@email.com")
                .setAdditional(locationAdditionalMap);

        Breinify.setConfig(breinConfig);

        invokeActivityCall(localBreinUser,
                breinActivityType,
                breinCategoryType,
                "login");
    }

    /**
     * test case with extra fields on activity level
     */
    @Test
    public void testActivityRequestWithExtraUserMapMap() {

        final Map<String, Object> dataUserRootMap = new HashMap<>();
        final Map<String, Object> dataUserMap = new HashMap<>();
        dataUserMap.put("enhancement-user-1", "user-value-1");
        dataUserMap.put("enhancement-user-2", "user-value-2");
        dataUserRootMap.put("rootMap", dataUserMap);

        final BreinUser localBreinUser = new BreinUser()
                .setEmail("fred.firestone@email.com")
                .set(dataUserRootMap);

        Breinify.setConfig(breinConfig);

        invokeActivityCall(localBreinUser,
                breinActivityType,
                breinCategoryType,
                "login");
    }


    /**
     * helper method to call login
     */
    public void callLoginWithStandardValues() {

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
        invokeActivityCall(breinUser,
                breinActivityType,
                breinCategoryType,
                "selectProduct");
    }


    /**
     * Tests the lookup functionality
     */
    @Test
    public void testTemporalData() {

        // set configuration
        Breinify.setConfig(breinConfig);
        BreinResult response = null;

        final BreinUser user = new BreinUser()
                // important new fields
                .setIpAddress("74.115.209.58")
                .setTimezone("America/Los_Angeles")
                .setLocalDateTime("Sun Dec 25 2016 18:15:48 GMT-0800 (PST)");

        try {
            // invoke temporaldata
            response = Breinify.temporalData(user);
        } catch (final Exception e) {
            fail("REST exception is: " + e);
        }

        // show output
        showTemporalDataOutput(response);
    }


    /**
     * Tests the lookup functionality
     */
    @Test
    public void testTemporalDataWithAdditionalMap() {

        final Map<String, Object> locationAdditionalMap = new HashMap<>();
        final Map<String, Object> locationValueMap = new HashMap<>();

        locationValueMap.put("latitude", 32.7157);
        locationValueMap.put("longitude", -117.1611);
        locationAdditionalMap.put("location", locationValueMap);

        final BreinUser user = new BreinUser()
                .setAdditional(locationAdditionalMap);

        try {
            // set configuration
            Breinify.setConfig(breinConfig);

            // invoke temporaldata
            final BreinResult response = Breinify.temporalData(user);

            // show output
            showTemporalDataOutput(response);

        } catch (final Exception e) {
            fail("REST exception is: " + e);
        }
    }

    /**
     *
     */
    @Test
    public void testTemporalDataFromPhilipp() {

        final String API_KEY = "41B2-F48C-156A-409A-B465-317F-A0B4-E0E8";

        Breinify.setConfig(new BreinConfig(API_KEY).setAndInitRestEngine(BreinEngineType.JERSEY_ENGINE));

        final Map<String, Object> locationAdditionalMap = new HashMap<>();
        final Map<String, Object> locationValueMap = new HashMap<>();
        locationValueMap.put("latitude", Math.random() * 10 + 39 - 5);
        locationValueMap.put("longitude", Math.random() * 50 - 98 - 25);
        locationAdditionalMap.put("location", locationValueMap);

        final BreinUser user = new BreinUser()
                .setAdditional(locationAdditionalMap)
                .setIpAddress("127.0.0.1");

        for (int i=0; i < 100; i++) {
            final BreinResult response = Breinify.temporalData(user);

            // show output
            System.out.println("Index is: " + i);
            showTemporalDataOutput(response);
            try {
                Thread.sleep(300);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tests the lookup functionality
     */
    @Test
    public void testTemporalDataWithSign() {

        final String secret = "lmcoj4k27hbbszzyiqamhg==";
        final String apiKeyWithSecret = "CA8A-8D28-3408-45A8-8E20-8474-06C0-8548";

        final BreinConfig breinConfig = new BreinConfig(apiKeyWithSecret, secret);

        // set configuration
        Breinify.setConfig(breinConfig);
        BreinResult breinResult = null;

        final BreinTemporalData breinTemporalData = Breinify.getBreinTemporalData();

        // important new fields
        final BreinUser user = new BreinUser()
                // important new fields
                .setIpAddress("74.115.209.58")
                .setTimezone("America/Los_Angeles")
                .setLocalDateTime("Sun Dec 25 2016 18:15:48 GMT-0800 (PST)");

        try {
            // invoke temporaldata
            breinResult = Breinify.temporalData(user);
        } catch (final Exception e) {
            fail("REST exception is: " + e);
        }

        final Map<String, Object> resultMap = breinResult.getMap();


        final Map<String, Object> timeValues = breinResult.get("time");
        final Map<String, Object> weatherValues = breinResult.get("weather");
        final Map<String, Object> locationValues = breinResult.get("location");
        final Map<String, Object> holidayValues = breinResult.get("holiday");


        // show output
        showTemporalDataOutput(breinResult);
    }

    private void showTemporalDataOutput(final BreinResult response) {
        if (response != null) {
            final Object timeValues = response.get("time");
            assertTrue(timeValues != null);
            System.out.println(timeValues);

            final Object weatherValues = response.get("weather");
            final Object locationValues = response.get("location");
            final Object holidayValues = response.get("holiday");
        }
    }

}