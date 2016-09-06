package com.brein.lookup;

import com.brein.api.BreinLookup;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinDimension;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.engine.BreinEngineType;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for lookup functionality
 */
public class TestLookup {

    /**
     * Contains the BASE URL of the Breinify Backend
     */
    // private static final String BASE_URL = "http://dev.breinify.com/api";

    private static final String BASE_URL = "https://api.breinify.com";

    /**
     * This has to be a valid api key
     */
    private static final String VALID_API_KEY = "A187-B1DF-E3C5-4BDB-93C4-4729-7B54-E5B1";

    /**
     * Contains the Breinify User
     */
    private final BreinUser breinUser = new BreinUser("user.name@email.com");

    /**
     * The Lookup itself
     */
    private final BreinLookup breinLookup = new BreinLookup();

    /**
     * Preparation of test case
     */
    @Before
    public void setUp() {

        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
                BASE_URL,
                BreinEngineType.UNIREST_ENGINE);

        breinLookup.setConfig(breinConfig);
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
        } catch (final InterruptedException e) {
            fail();
        }
    }

    /**
     * Tests the lookup functionality
     *
     */
    @Test
    public void testLookup() {

        final String[] dimensions = {"firstname", "gender",
                "age", "agegroup", "digitalfootprint", "images"};

        final BreinDimension breinDimension = new BreinDimension(dimensions);

        /**
         * invoke lookup
         */
        final BreinResult breinResult = breinLookup.lookUp(breinUser, breinDimension, false);

        final Object dataFirstname = breinResult.get("firstname");
        final Object dataGender = breinResult.get("gender");
        final Object dataAge = breinResult.get("age");
        final Object dataAgeGroup = breinResult.get("agegroup");
        final Object dataDigitalFootprinting = breinResult.get("digitalfootprint");
        final Object dataImages = breinResult.get("digitalfootprint");
    }

    /**
     * Testcase that proves that all attributes of class BreinLookup
     * are set to empty values
     */
    @Test
    public void testResetAllValues() {

        final String[] dimensions = {"firstname", "gender",
                "age", "agegroup", "digitalfootprint", "images"};
        final BreinDimension breinDimension = new BreinDimension(dimensions);
        final BreinLookup breinLookup = new BreinLookup();

        breinLookup.setBreinDimension(breinDimension);
        breinLookup.setBreinUser(new BreinUser("user.name@email.com"));
        breinLookup.setConfig(new BreinConfig("KEY",
                BreinConfig.DEFAULT_BASE_URL,
                BreinEngineType.UNIREST_ENGINE));

        // first check init
        breinLookup.init();
        assertTrue("breinDimention is not null", breinLookup.getBreinDimension() == null);

        // breinUser and breinConfig should be valid
        assertTrue("breinUser is null!", breinLookup.getBreinUser() != null);
        assertTrue("breinConfig is null!", breinLookup.getConfig() != null);

        // now invoke resetAllValues, this will delete breinUser and breinConfig as well
        breinLookup.resetAllValues();

        // breinUser and breinConfig should now be null
        assertTrue("breinUser is not null!", breinLookup.getBreinUser() == null);
        assertTrue("breinConfig is not null!", breinLookup.getConfig() == null);
    }

}
