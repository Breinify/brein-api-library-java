package com.brein.api;

import com.brein.domain.BreinCategoryType;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.engine.BreinEngineType;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class TestStressTests extends ApiTestBase {
    private final Logger LOG = LoggerFactory.getLogger(TestStressTests.class);

    private int counter;
    private long elapsedTime;

    /**
     * Test of recommendation
     */
    @Test
    public void testStressRecommendation() {
        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY_FOR_SECRET, VALID_SECRET)
                .setRestEngineType(BreinEngineType.JERSEY_ENGINE);
        Breinify.setConfig(breinConfig);

        final BreinUser breinUser = new BreinUser()
                .setEmail("tester.breinify@email.com")
                .setSessionId("1133AADDDEEE");

        final int numberOfRecommendations = 3;
        final BreinRecommendation recommendation = new BreinRecommendation()
                .setUser(breinUser)
                .setNumberOfRecommendations(numberOfRecommendations);

        final long startTime = System.currentTimeMillis();

        this.counter = 0;
        TestConcurrencyWithUniRest.threadTesting(10, () -> {
            for (int loop = 0; loop < 10000; loop++) {

                final BreinResult result = Breinify.recommendation(recommendation);
                Assert.assertEquals(200, result.getStatus());

                this.elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                this.counter++;

                if (this.elapsedTime >= 10) {
                    break;
                }
            }
        });

        LOG.info("Duration was: " + this.elapsedTime + " seconds for " + this.counter + " requests.");
        Assert.assertTrue(this.counter > 2000);
    }

    @Test
    public void testPageVisit() {

        // set configuration
        final String apiKey = "41B2-F48C-156A-409A-B465-317F-A0B4-E0E8";
        final BreinConfig breinConfig = new BreinConfig(apiKey);
        Breinify.setConfig(breinConfig);

        // user data
        final BreinUser breinUser = new BreinUser()
                .setEmail("User.Name@email.com")
                .setFirstName("Marco")
                .setLastName("Recchioni")
                .setDateOfBirth(11, 20, 1999)
                .setSessionId("r3V2kDAvFFL_-RBhuc_-Dg");

        final BreinActivity activity = new BreinActivity()
                .setUnixTimestamp(Instant.now().getEpochSecond())
                .setClientIpAddress("10.11.12.13")
                .setUser(breinUser)
                .setCategory(BreinCategoryType.APPAREL)
                .setActivityType("TestStressTests.testPageVisit")
                .setDescription("your description")
                .setTag("t1", 0.0)
                .setTag("t2", 5)
                .setTag("t3", "0.0")
                .setTag("t4", 5.0000)
                .setTag("nr", 3000)
                .setTag("sortid", "1.0");

        final long startTime = System.currentTimeMillis();
        for (int loop = 0; loop <= 100; loop++) {
            asyncTest(cb -> Breinify.activity(activity, res -> {
                assertEquals(200, res.getStatus());
                cb.set(true);
            }), 2000);
        }
    }

}
