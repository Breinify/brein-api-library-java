package com.brein.api;

import com.brein.domain.BreinActivityType;
import com.brein.domain.BreinCategoryType;
import com.brein.domain.BreinUser;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static org.junit.Assert.fail;

public class ApiTestBase {
    protected static final String VALID_API_KEY = "41B2-F48C-156A-409A-B465-317F-A0B4-E0E8";
    protected static final String VALID_API_KEY_FOR_SECRET = "CA8A-8D28-3408-45A8-8E20-8474-06C0-8548";
    protected static final String VALID_SECRET = "lmcoj4k27hbbszzyiqamhg==";

    /**
     * Contains the Breinify User
     */
    protected final BreinUser user = new BreinUser().setEmail("User.Name@email.com");

    /**
     * Contains the Category
     */
    protected final String category = BreinCategoryType.HOME;

    /**
     * Contains the BreinActivityType
     */
    protected final String activityType = BreinActivityType.LOGIN;

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
     * Preparation of test case
     */
    @Before
    public void setUp() {
        Breinify.setConfig(VALID_API_KEY);
    }

    @After
    public void cleanUp() {
        Breinify.shutdown();
    }

    protected void asyncTest(final Consumer<AtomicBoolean> test, final long timeoutInMs) {
        final AtomicBoolean runner = new AtomicBoolean(false);
        test.accept(runner);

        final long start = System.currentTimeMillis();
        while (!runner.get()) {
            if (System.currentTimeMillis() - start > timeoutInMs) {
                fail("asyncTest timed out.");
                break;
            }
        }
    }
}
