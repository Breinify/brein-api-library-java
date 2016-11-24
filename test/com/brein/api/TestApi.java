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
import java.util.function.Function;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test of Breinify Java API (static option)
 */
public class TestApi {

    /**
     * Contains the BASE URL of the Breinify Backend
     */
    private static final String BASE_URL = "https://api.breinify.com";

    /**
     * This has to be a valid api key
     */
    private static final String VALID_API_KEY = "772A-47D7-93A3-4EA9-9D73-85B9-479B-16C6";

    /**
     * Contains the Breinify User
     */
    private final BreinUser breinUser = new BreinUser("User.Name@email.com");

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
    final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY);

    /**
     * indicator if the loops should be done forever :-)
     */
    boolean loopIndicator = true;

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

        callLoginWithStandardValues();
    }

    /**
     * test case with fields on base level
     */
    @Test
    public void testActivityRequestWithBaseMap() {

        final Map<String, Object> dataBaseLevelMap = new HashMap<>();
        dataBaseLevelMap.put("enhancement-base", "value");

        final BreinActivity breinActivity = Breinify.getBreinActivity();
        breinActivity.setBaseMap(dataBaseLevelMap);

        callLoginWithStandardValues();
    }

    /**
     * test case with fields on activity level
     */
    @Test
    public void testActivityRequestWithActivityMap() {

        final Map<String, Object> dataActivityMap = new HashMap<>();
        dataActivityMap.put("enhancement-activity-1", "value-1");
        dataActivityMap.put("enhancement-activity-2", "value-2");

        final BreinActivity breinActivity = Breinify.getBreinActivity();
        breinActivity.setActivityMap(dataActivityMap);

        callLoginWithStandardValues();
    }

    /**
     * test case with fields on activity level
     */
    @Test
    public void testActivityRequestWithUserMap() {

        Breinify.setConfig(breinConfig);

        final Map<String, Object> dataUserMap = new HashMap<>();
        dataUserMap.put("enhancement-user-1", "user-value-1");
        dataUserMap.put("enhancement-user-2", "user-value-2");

        final BreinUser localBreinUser = new BreinUser()
                .setEmail("fred.firestone@email.com")
                .setUserMap(dataUserMap);

        invokeActivityCall(localBreinUser,
                breinActivityType,
                breinCategoryType,
                "login");
    }

    /**
     * test case with fields on activity level
     */
    @Test
    public void testActivityRequestWithUserMapMap() {

        final Map<String, Object> dataUserRootMap = new HashMap<>();
        final Map<String, Object> dataUserMap = new HashMap<>();
        dataUserMap.put("enhancement-user-1", "user-value-1");
        dataUserMap.put("enhancement-user-2", "user-value-2");
        dataUserRootMap.put("rootMap", dataUserMap);

        final BreinUser localBreinUser = new BreinUser()
                .setEmail("fred.firestone@email.com")
                .setUserMap(dataUserRootMap);

        Breinify.setConfig(breinConfig);

        invokeActivityCall(localBreinUser,
                breinActivityType,
                breinCategoryType,
                "login");
    }

    /**
     * test case with fields on activity level
     */
    @Test
    public void testActivityRequestWithUserAdditionalMap() {

        final Map<String, Object> dataAdditionalMap = new HashMap<>();
        dataAdditionalMap.put("enhancement-user-additional-1", "user-add-value-1");
        dataAdditionalMap.put("enhancement-user-additional-2", "user-add-value-2");

        final BreinUser localBreinUser = new BreinUser()
                .setEmail("fred.firestone@email.com")
                .setAdditionalMap(dataAdditionalMap);

        invokeActivityCall(localBreinUser,
                breinActivityType,
                breinCategoryType,
                "login");
    }

    /**
     * Test case with user additional map containing a map as an object
     */
    @Test
    public void testActivityRequestWithUserAdditionalMapMap() {

        final Map<String, Object> locationAdditionalMap = new HashMap<>();
        final Map<String, Object> locationValueMap = new HashMap<>();

        locationValueMap.put("latitude", 32.7157);
        locationValueMap.put("longitude", -117.1611);
        locationAdditionalMap.put("location", locationValueMap);

        final BreinUser localBreinUser = new BreinUser()
                .setEmail("fred.firestone@email.com")
                .setAdditionalMap(locationAdditionalMap);

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
                "login");
    }

    /**
     * helper method to call login
     *
     * @param breinUser         brein unser
     * @param breinActivityType activity type
     * @param breinCategoryType category type
     * @param description       description
     */
    public void callActivityWithStandardConfig(final BreinUser breinUser,
                                               final String breinActivityType,
                                               final String breinCategoryType,
                                               final String description) {

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
                description);
    }

    /**
     * Helper method. Invokes an activity call
     *
     * @param breinUser the brein user
     * @param breinActivityType the activity type
     * @param breinCategoryType the category type
     * @param description the description
     */
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
    public void testCallBack() {

        final Function<String, Void> callback = message -> {
            System.out.println(message);
            return null;
        };

        callTest(callback);
    }

    public void callTest(final Function<String, Void> callback) {

        callback.apply("Message from callback");
    }

    /**
     * testcase without category type
     */
    @Test
    public void testWithoutCategoryType() {

        callActivityWithStandardConfig(breinUser, breinActivityType, null, "login");
    }

    /**
     * Testcase with null value as apikey
     */
    @Test
    public void testLoginWithNullApiKey() {

        final String description = "Login-Description";

        final BreinConfig config = new BreinConfig();
        Breinify.setConfig(config);

        // additional user information
        breinUser.setFirstName("User")
                .setLastName("Name");

        invokeActivityCall(breinUser, breinActivityType, breinCategoryType, description);
    }

    /**
     * Testcase with null value as base url, should throw an exception
     */
    @Test(expected=BreinInvalidConfigurationException.class)
    public void testWithNullBaseUrl() {

        final BreinConfig config = new BreinConfig(VALID_API_KEY).setBaseUrl(null);
        Breinify.setConfig(config);
    }

    /**
     * Testcase with no rest engine set. This will throw an
     * exception.
     */
    @Test(expected=BreinException.class)
    public void testLoginWithNoRestEngine() {

        final String description = "Login-Description";
        final BreinConfig config = new BreinConfig(VALID_API_KEY).setAndInitRestEngine(BreinEngineType.NO_ENGINE);
        Breinify.setConfig(config);

        // additional user information
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        invokeActivityCall(breinUser, breinActivityType, breinCategoryType, description);
    }


    /**
     * Testcase with later set UNIREST_ENGINE
     *
     */
    @Test
    public void testLoginWithNoSetToUnirestEngine() {

        final String description = "Login-Description";
        final BreinConfig config = new BreinConfig(VALID_API_KEY).setAndInitRestEngine(BreinEngineType.UNIREST_ENGINE);
        Breinify.setConfig(config);

        // additional user information
        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        invokeActivityCall(breinUser, breinActivityType, breinCategoryType, description);
    }


    /**
     * Test case with wrong endpoint configuration
     */
    @Test
    public void testWithWrongEndPoint() {

        final String description = "Login-Description";

        final BreinConfig config = new BreinConfig(VALID_API_KEY);
        config.setActivityEndpoint("/wrongEndPoint");

        Breinify.setConfig(config);

        breinUser.setFirstName("User");
        breinUser.setLastName("Name");

        invokeActivityCall(breinUser, breinActivityType, breinCategoryType, description);
    }

    /**
     * Invoke a test call with 200 logins
     */
    // @Test
    public void testWith200Logins() {

        final int maxLogin = 200;
        for (int index = 0; index < maxLogin; index++) {
            System.out.println("index is: " + index);
            testLogin();
        }

    }

    /**
     * test case how to invoke logout activity
     */
    @Test
    public void testLogout() {

        final String description = "Logout-Description";

        // set config
        Breinify.setConfig(breinConfig);

        // additional user information
        breinUser.setDateOfBirth(12, 31, 2008);

        invokeActivityCall(breinUser, breinActivityType, breinCategoryType, description);
    }

    /**
     * test case how to invoke search activity
     */
    @Test
    public void testSearch() {

        final String description = "Search-Description";

        // set config
        Breinify.setConfig(breinConfig);

        // invoke activity call
        invokeActivityCall(breinUser, BreinActivityType.SEARCH, breinCategoryType, description);
    }

    @Test
    public void testWithNullValues() {
        // set config
        Breinify.setConfig(breinConfig);

        // invoke call
        invokeActivityCall(breinUser, null, null, null);

    }

    /**
     * test case how to invoke addToCart activity
     */
    @Test
    public void testAddToCart() {

        final String description = "AddToCart-Description";

        // set configuration
        Breinify.setConfig(breinConfig);

        // invoke activity call
        invokeActivityCall(breinUser, BreinActivityType.ADD_TO_CART, breinCategoryType, description);
    }

    /**
     * test case how to invoke removeFromCart activity
     */
    @Test
    public void testRemoveFromCart() {

        final String description = "RemoveFromCart-Description";

        // set configuration
        Breinify.setConfig(breinConfig);

        // invoke activity call
        invokeActivityCall(breinUser, BreinActivityType.REMOVE_FROM_CART, breinCategoryType, description);
    }

    /**
     * test case how to invoke selectProduct activity
     */
    @Test
    public void testSelectProduct() {

        final String description = "Select-Product-Description";

        // set configuration
        Breinify.setConfig(breinConfig);

        // invoke activity call
        invokeActivityCall(breinUser, BreinActivityType.SELECT_PRODUCT, breinCategoryType, description);
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
        invokeActivityCall(breinUser, BreinActivityType.OTHER, breinCategoryType, description);
    }

    /**
     * test case containing additional information
     */
    @Test
    public void testPageVisit() {

        // set configuration
        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY);
        Breinify.setConfig(breinConfig);

        // user data
        final BreinUser breinUser = new BreinUser("User.Name@email.com")
                .setFirstName("Marco")
                .setLastName("Recchioni")
                .setDateOfBirth(11, 20, 1999)
                .setDeviceId("DD-EEEEE")
                .setImei("55544455333")
                .setSessionId("r3V2kDAvFFL_-RBhuc_-Dg")
                .setUrl("https://sample.com.au/home")
                .setReferrer("https://sample.com.au/track")
                .setIpAddress("10.11.12.130")
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");


        final Map<String, Object> tagMap = new HashMap<>();
        tagMap.put("t1", 0.0);
        tagMap.put("t2", 5);
        tagMap.put("t3", "0.0");
        tagMap.put("t4", 5.0000);
        tagMap.put("nr", 3000);
        tagMap.put("sortid", "1.0");

        final BreinActivity breinActivity = Breinify.getBreinActivity();

        breinActivity.setUnixTimestamp(Instant.now().getEpochSecond());
        breinActivity.setBreinUser(breinUser);
        breinActivity.setBreinCategoryType(BreinCategoryType.APPAREL);
        breinActivity.setBreinActivityType(BreinActivityType.PAGEVISIT);
        breinActivity.setDescription("your description");
        breinActivity.setTagsMap(tagMap);
        breinActivity.setIpAddress("10.11.12.13");

        Breinify.activity();
    }

    /**
     * test case without having set the BreinUser.
     * This will lead to an Exception.
     */
    @Test(expected=BreinException.class)
    public void testPageVisitWithException() {

        // set configuration
        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY);
        Breinify.setConfig(breinConfig);

        // user data
        final BreinUser breinUser = new BreinUser("User.Name@email.com")
                .setFirstName("User")
                .setLastName("Name")
                .setDateOfBirth(11, 20, 1999)
                .setDeviceId("DD-EEEEE")
                .setImei("55544455333")
                .setSessionId("r3V2kDAvFFL_-RBhuc_-Dg")
                .setUrl("https://sample.com.au/home")
                .setReferrer("https://sample.com.au/track")
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");

        final BreinActivity breinActivity = Breinify.getBreinActivity();

        breinActivity.setBreinCategoryType(BreinCategoryType.APPAREL);
        breinActivity.setBreinActivityType(BreinActivityType.PAGEVISIT);
        breinActivity.setDescription("your description");

        // user not set -> exception expected
        Breinify.activity();
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

        // Just to ensure that the right config has been set
        final String badUrl = "www.beeeeeiiiniiify.com";

        final BreinConfig breinConfigWithBadUrl = new BreinConfig(VALID_API_KEY).setBaseUrl(badUrl);
        Breinify.setConfig(breinConfigWithBadUrl);

        // invoke activity call, will cause an exception
        invokeActivityCall(breinUser, BreinActivityType.LOGIN, BreinCategoryType.HOME, "Login-Description");
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
        BreinResult response = null;

        try {
            // invoke lookup
            response = Breinify.lookup(breinUser, breinDimension);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        showLookupOutput(response);
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
        // set secret
        breinConfig.setSecret(secret);

        // set config
        Breinify.setConfig(breinConfig);

        // invoke activity call
        invokeActivityCall(breinUser, BreinActivityType.LOGIN, BreinCategoryType.HOME, "Login-Desc");
    }

    /**
     * Test a login activity with sign but wrong secret
     */
    @Test
    public void testLoginWithSignButWrongSecret() {

        final String wrongSecret = "ThisIsAWrongSecret";
        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY, wrongSecret);
        // set secret
        breinConfig.setSecret(wrongSecret);

        // set config
        Breinify.setConfig(breinConfig);

        // invoke activity call
        invokeActivityCall(breinUser, BreinActivityType.LOGIN, BreinCategoryType.HOME, "Login-Desc");
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
        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY, secret);

        // set secret
        breinConfig.setSecret(secret);

        // set configuration
        Breinify.setConfig(breinConfig);

        try {
            //invoke lookup
            final BreinResult response = Breinify.lookup(breinUser, breinDimension);
            showLookupOutput(response);
        } catch (final Exception e) {
            e.printStackTrace();
        }


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
        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY, thisIsAWrongSecret);

        // set secret
        breinConfig.setSecret(thisIsAWrongSecret);

        // set configuration
        Breinify.setConfig(breinConfig);

        try {
            // invoke lookup
            final BreinResult response = Breinify.lookup(breinUser, breinDimension);
            showLookupOutput(response);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper to show output
     *
     * @param response contains the response from the request
     */
    public void showLookupOutput(final BreinResult response) {
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
     * test case where an activity is sent without having set
     * the category type for this particular activity object.
     * In this case the default category type has to be used.
     * If this is not set then the call needs to be rejected.
     */
    @Test
    public void testActivityWithoutCategory() {

        // create configuration
        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY).setDefaultCategory("DEF-CAT-TYPE");
        Breinify.setConfig(breinConfig);

        // create user
        final BreinUser breinUser = new BreinUser()
                .setSessionId("SESS-ID-IS-THIS");

        // send activity with all fields sets so far
        Breinify.activity(breinUser, "ACT-TYPE", "CAT-TYPE", "DESC", null);

        // send activity without CAT-TYPE => use default
        Breinify.activity(breinUser, "ACT-TYPE", "", "DESC", null);

        Breinify.activity(breinUser, "ACT-TYPE", null, "DESC", null);

        // send activity by setting the breinActivity methods
        final BreinActivity breinActivity = Breinify.getBreinActivity()
                .setBreinCategoryType(null)
                .setBreinActivityType("ACTI-TYPE")
                .setDescription("DESC");

        Breinify.activity();
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


        while (index <= 20) {

            // loopIndicator = true;

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
                        e.printStackTrace();
                    }

                }

            } catch (final BreinException e) {
                System.out.println("Exception is: " + e);
            }
        }
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
     * Test case supporting the following additional feature:
     * <p>
     * additional: {
     * location: {
     * city: 'Cupertino',
     * state: 'CA',
     * country: 'US'
     * }
     * }
     */
    @Test
    public void testTemporalDataWithAdditionalLocation() {

        // TODO: build testcase

        // set configuration
        Breinify.setConfig(breinConfig);

        final BreinUser user = new BreinUser()
                // important new fields
                .setIpAddress("74.115.209.58")
                .setTimezone("America/Los_Angeles")
                .setLocalDateTime("Sun Dec 25 2016 18:15:48 GMT-0800 (PST)");

        try {
            // invoke temporaldata
            final BreinResult response = Breinify.temporalData(user);
            // show output
            showTemporalDataOutput(response);
        } catch (final Exception e) {
            fail("REST exception is: " + e);
        }


    }

    private void showTemporalDataOutput(final BreinResult response) {
        if (response != null) {
            final Object timeValues = response.get("time");
            assertTrue(timeValues != null);
            System.out.println(timeValues);
        }
    }


    @Test
    public void testForDoc() {

    }

}