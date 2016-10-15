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
    final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
            BASE_URL,
            BreinEngineType.UNIREST_ENGINE);

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
     * test case with extra fields on base level
     *
     */
    @Test
    public void testActivityRequestWithExtraBaseMap() {

        final Map<String, Object> dataBaseLevelMap = new HashMap<>();
        dataBaseLevelMap.put("enhancement-base", "value");

        final BreinActivity breinActivity = Breinify.getBreinActivity();
        breinActivity.setExtraMap(BreinActivity.MapKind.MK_BASE, dataBaseLevelMap);

        callLoginWithStandardValues();
    }

    /**
     * test case with extra fields on activity level
     *
     */
    @Test
    public void testActivityRequestWithExtraActivityMap() {

        final Map<String, Object> dataActivityMap = new HashMap<>();
        dataActivityMap.put("enhancement-activity-1", "value-1");
        dataActivityMap.put("enhancement-activity-2", "value-2");

        final BreinActivity breinActivity = Breinify.getBreinActivity();
        breinActivity.setExtraMap(BreinActivity.MapKind.MK_ACTIVITY, dataActivityMap);

        callLoginWithStandardValues();
    }

    /**
     * test case with extra fields on activity level
     *
     */
    @Test
    public void testActivityRequestWithExtraUserMap() {

        final Map<String, Object> dataUserMap = new HashMap<>();
        dataUserMap.put("enhancement-user-1", "user-value-1");
        dataUserMap.put("enhancement-user-2", "user-value-2");

        final BreinActivity breinActivity = Breinify.getBreinActivity();
        breinActivity.setExtraMap(BreinActivity.MapKind.MK_USER, dataUserMap);

        callLoginWithStandardValues();
    }

    /**
     * test case with extra fields on activity level
     *
     */
    @Test
    public void testActivityRequestWithExtraUserAdditionalMap() {

        final Map<String, Object> dataAdditionalMap = new HashMap<>();
        dataAdditionalMap.put("enhancement-user-additional-1", "user-add-value-1");
        dataAdditionalMap.put("enhancement-user-additional-2", "user-add-value-2");

        final BreinActivity breinActivity = Breinify.getBreinActivity();
        breinActivity.setExtraMap(BreinActivity.MapKind.MK_USER_ADDITIONAL, dataAdditionalMap);

        callLoginWithStandardValues();
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
                "login",
                false);
    }

    /**
     * helper method to call login
     *
     * @param breinUser
     * @param breinActivityType
     * @param breinCategoryType
     * @param description
     * @param signFlag
     */
    public void callActivityWithStandardConfig(final BreinUser breinUser,
                                               final String breinActivityType,
                                               final String breinCategoryType,
                                               final String description,
                                               final boolean signFlag) {

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
                description,
                signFlag);
    }

    /**
     * @param breinUser
     * @param breinActivityType
     * @param breinCategoryType
     * @param description
     * @param signFlag
     */
    public void invokeActivityCall(final BreinUser breinUser,
                                   final String breinActivityType,
                                   final String breinCategoryType,
                                   final String description,
                                   final boolean signFlag) {



        Function<String, Void> callback = message -> {
            System.out.println(message);
            return null;
        };


        // invoke activity call
        Breinify.activity(breinUser,
                breinActivityType,
                breinCategoryType,
                description,
                signFlag,
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

    public void callTest(Function<String, Void> callback) {

        callback.apply("Message from callback");
    }

    /**
     * testcase without category type
     */
    @Test
    public void testWithoutCategoryType() {

        callActivityWithStandardConfig(breinUser, breinActivityType, null, "login", false);
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

        invokeActivityCall(breinUser, breinActivityType, breinCategoryType, description, sign);
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

        invokeActivityCall(breinUser, breinActivityType, breinCategoryType, description, sign);
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

        invokeActivityCall(breinUser, breinActivityType, breinCategoryType, description, false);
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

        invokeActivityCall(breinUser, breinActivityType, breinCategoryType, description, false);
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
        invokeActivityCall(breinUser, BreinActivityType.SEARCH, breinCategoryType, description, false);
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
        invokeActivityCall(breinUser, BreinActivityType.ADD_TO_CART, breinCategoryType, description, false);
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
        invokeActivityCall(breinUser, BreinActivityType.REMOVE_FROM_CART, breinCategoryType, description, false);
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
        invokeActivityCall(breinUser, BreinActivityType.SELECT_PRODUCT, breinCategoryType, description, false);
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
        invokeActivityCall(breinUser, BreinActivityType.OTHER, breinCategoryType, description, false);
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
        breinActivity.setSign(false);
        breinActivity.setTagsMap(tagMap);

        Breinify.activity();
    }

    /**
     * test case without having set the BreinUser.
     * This will lead to an Exception.
     */
    @Test(expected=BreinException.class)
    public void testPageVisitWithException() {

        // set configuration
        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE);
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
        breinActivity.setSign(false);

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

        final BreinConfig breinConfigWithBadUrl = new BreinConfig(VALID_API_KEY,
                badUrl,
                BreinEngineType.UNIREST_ENGINE);

        Breinify.setConfig(breinConfigWithBadUrl);

        // invoke activity call, will cause an exception
        invokeActivityCall(breinUser, BreinActivityType.LOGIN, BreinCategoryType.HOME, "Login-Description", false);
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

        // set configuration
        Breinify.setConfig(breinConfig);
        BreinResult response = null;

        try {
            // invoke lookup
            response = Breinify.lookup(breinUser, breinDimension, sign);
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
        invokeActivityCall(breinUser, BreinActivityType.LOGIN, BreinCategoryType.HOME, "Login-Desc", sign);
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
        invokeActivityCall(breinUser, BreinActivityType.LOGIN, BreinCategoryType.HOME, "Login-Desc", sign);
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
        BreinResult response = null;

        try {
            //invoke lookup
            response = Breinify.lookup(breinUser, breinDimension, sign);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        showLookupOutput(response);
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

        // set configuration
        Breinify.setConfig(breinConfig);
        BreinResult response = null;

        try {
            // invoke lookup
            response = Breinify.lookup(breinUser, breinDimension, sign);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        showLookupOutput(response);
    }

    /**
     * Helper to show output
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
        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE)
                .setDefaultCategory("DEF-CAT-TYPE");

        Breinify.setConfig(breinConfig);

        // create user
        final BreinUser breinUser = new BreinUser()
                .setSessionId("SESS-ID-IS-THIS");

        // send activity with all fields sets so far
        Breinify.activity(breinUser, "ACT-TYPE", "CAT-TYPE", "DESC", false, null);

        // send activity without CAT-TYPE => use default
        Breinify.activity(breinUser, "ACT-TYPE", "", "DESC", false, null);

        Breinify.activity(breinUser, "ACT-TYPE", null, "DESC", false, null);

        // send activity by setting the breinActivity methods
        BreinActivity breinActivity = Breinify.getBreinActivity()
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
                            false, errorCallback);

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


    @Test
    public void testForDoc() {


    }

}