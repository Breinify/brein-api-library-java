package com.brein.api;


import com.brein.domain.*;
import com.brein.engine.BreinEngineType;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestStressTests {

    static int counter;
    static long stopTime;
    static long elapsedTime;

    /**
     * Test of recommendation
     */
    @Test
    public void testStressRecommendation() {

        final String apiKey = "CA8A-8D28-3408-45A8-8E20-8474-06C0-8548";
        final String secret = "lmcoj4k27hbbszzyiqamhg==";

        final BreinConfig breinConfig = new BreinConfig(apiKey, secret).setAndInitRestEngine(BreinEngineType.JERSEY_ENGINE);

        Breinify.setConfig(breinConfig);

        final BreinUser breinUser = new BreinUser()
                .setEmail("tester.breinify@email.com")
                .setSessionId("1133AADDDEEE");

        final int numberOfRecommendations = 3;
        final BreinRecommendation breinRecommendation = new BreinRecommendation(breinUser, numberOfRecommendations);

        final long startTime = System.currentTimeMillis();

        counter = 0;

        TestConcurrency.threadTesting(10, () -> {
            for (int loop = 0; loop <= 1000; loop++) {

                final BreinResult result = Breinify.recommendation(breinRecommendation);

                // result
                if (result.getStatus() == 200) {

                    System.out.println("Counter: "
                            + counter
                            + " Message from BreinRecommendation is: "
                            + result.getMessage());

                    final ArrayList arrayList = result.get("result");

                    //    arrayList.forEach((value) -> System.out.println("Item : " + value));
                }

                stopTime = System.currentTimeMillis();
                elapsedTime = (stopTime - startTime) / 1000;

                counter++;

                if (elapsedTime >= 60) {
                    System.out.println("Duration was: " + elapsedTime + " seconds for " + counter + " requests.");
                    break;
                }
            }
        });
    }


    @Test
    public void testStressPageVisit() {

        // set configuration
        final String apiKey = "41B2-F48C-156A-409A-B465-317F-A0B4-E0E8";
        final BreinConfig breinConfig = new BreinConfig(apiKey).setAndInitRestEngine(BreinEngineType.JERSEY_ENGINE);
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
        breinActivity.setTags(tagMap);
        breinActivity.setIpAddress("10.11.12.13");

        counter = 0;
        final long startTime = System.currentTimeMillis();

        TestConcurrency.threadTesting(10, () -> {
            for (int loop = 0; loop <= 1000; loop++) {

                Breinify.activity();

                System.out.println("Counter: " + counter);

                stopTime = System.currentTimeMillis();
                elapsedTime = (stopTime - startTime) / 1000;

                counter++;

                if (elapsedTime >= 60) {
                    System.out.println("Duration was: " + elapsedTime + " seconds for " + counter + " requests.");
                    break;
                }

            }
        });
    }

    @Test
    public void testPageVisit() {

        // set configuration
        final String apiKey = "41B2-F48C-156A-409A-B465-317F-A0B4-E0E8";
        final BreinConfig breinConfig = new BreinConfig(apiKey).setAndInitRestEngine(BreinEngineType.DUMMY_ENGINE);
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
        breinActivity.setTags(tagMap);
        breinActivity.setIpAddress("10.11.12.13");

        counter = 0;
        final long startTime = System.currentTimeMillis();

        for (int loop = 0; loop <= 10000000; loop++) {
            Breinify.activity();
        }
    }

}
