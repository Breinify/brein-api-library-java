package com.brein.lookup;

import com.brein.api.BreinLookup;
import com.brein.config.BreinConfig;
import com.brein.domain.BreinDimension;
import com.brein.domain.BreinResponse;
import com.brein.domain.BreinUser;
import com.brein.engine.BreinEngineType;
import com.brein.util.BreinUtil;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;

/**
 * Test cases for lookup functionality
 */
public class TestLookup {

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
    private final BreinUser breinUser = new BreinUser("philipp@meisen.net");

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
            /**
             * TODO...
             * Thread.sleep is not the best practice...
             *
             */
            Thread.sleep(4000);
        } catch (InterruptedException e) {
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
                "age", "agegroup", "digitalfootpring", "images"};

        final BreinDimension breinDimension = new BreinDimension(dimensions);

        /**
         * invoke lookup
         */
        final BreinResponse breinResponse = breinLookup.lookUp(breinUser, breinDimension);
        if (BreinUtil.containsValue(breinResponse.getResponse())) {
            System.out.println("Response is: " + breinResponse.getResponse());
        }
        assert(breinResponse.getResponse() != null);
    }
}
