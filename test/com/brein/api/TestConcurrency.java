package com.brein.api;

import com.brein.domain.BreinActivityType;
import com.brein.domain.BreinCategoryType;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.engine.BreinEngineType;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import static org.junit.Assert.assertTrue;

@SuppressWarnings("Duplicates")
public class TestConcurrency {

    private static final String VALID_API_KEY = "41B2-F48C-156A-409A-B465-317F-A0B4-E0E8";

    @Test
    public void testTemporalConcurrency() {

        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY)
                .setAndInitRestEngine(BreinEngineType.JERSEY_ENGINE);

        Breinify.setConfig(breinConfig);

        threadTesting(100, () -> {
            for (int ct = 0; ct < 1000; ct++) {

                System.out.println("Invoking Thread: " + ct);

                final Map<String, Object> locationAdditionalMap = new HashMap<>();
                final Map<String, Object> locationValueMap = new HashMap<>();

                locationValueMap.put("latitude", Math.random() * 10 + 39 - 5);
                locationValueMap.put("longitude", Math.random() * 50 - 98 - 25);

                locationAdditionalMap.put("location", locationValueMap);

                final BreinUser user = new BreinUser()
                        // important new fields
                        .setIpAddress("74.115.209.58")
                        .setTimezone("America/Los_Angeles")
                        .setLocalDateTime("Sun Dec 25 2016 18:15:48 GMT-0800 (PST)");

                final BreinUser localBreinUser = new BreinUser()
                        .setEmail("fred.firestone@email.com")
                        .setIpAddress("74.115.209.58")
                        .setAdditional(locationAdditionalMap);

                // invoke temporalData
                final BreinResult response = Breinify.temporalData(localBreinUser);
                System.out.println(response);
                Thread.sleep(300);

                // Assert.assertNotNull(response);
            }
        });

    }

    @Test
    public void testPageVisitConcurrency() {

        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY)
                .setAndInitRestEngine(BreinEngineType.JERSEY_ENGINE);

        Breinify.setConfig(breinConfig);

        threadTesting(100, () -> {
            for (int ct = 0; ct < 1000; ct++) {

                System.out.println("Invoking Thread: " + ct);

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
                        .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like " +
                                "Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");

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

                Breinify.activity();

            }
        });

    }

    /**
     * Test case for recommendation with 1000 calls
     */
    @Test
    public void testRecommendationConcurrency() {

        final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
                "secret-here").setAndInitRestEngine(BreinEngineType.JERSEY_ENGINE);

        Breinify.setConfig(breinConfig);

        threadTesting(100, () -> {
            for (int ct = 0; ct < 1000; ct++) {

                System.out.println("Invoking Thread: " + ct);

                final BreinUser localBreinUser = new BreinUser()
                        .setEmail("fred.firestone@email.com")
                        .setSessionId("1133AADDDEEE");

                final int numberOfRecommendations = 10;
                final BreinRecommendation breinRecommendation = new BreinRecommendation(localBreinUser,
                        numberOfRecommendations);
                final BreinResult result = Breinify.recommendation(breinRecommendation);

                // result
                if (result.getStatus() == 200) {
                    System.out.println("Message from BreinRecommendation is: " + result.getMessage());

                    final ArrayList<Object> arrayList = result.get("result");
                    arrayList.forEach((value) -> System.out.println("Item : " + value));
                }
            }
        });

    }

    @FunctionalInterface
    public interface Command extends Supplier<Void> {

        @Override
        default Void get() {
            try {
                execute();
            } catch (final Throwable t) {
                handle(t);
            }
            return null;
        }

        void execute() throws Exception;
    }

    public static void threadTesting(final int nrOfThreadsPerSupplier, final Command... commands) {
        final int nrOfThreads = nrOfThreadsPerSupplier * commands.length;
        final ExecutorService executor = Executors.newFixedThreadPool(nrOfThreads);

        final List<Future<Void>> futures = new ArrayList<>(nrOfThreads);
        for (final Command command : commands) {
            for (int i = 0; i < nrOfThreadsPerSupplier; i++) {
                futures.add(executor.submit(command::get));
            }
        }

        for (final Future<Void> future : futures) {
            try {
                future.get();
            } catch (final ExecutionException e) {
                handle(e.getCause());
            } catch (final InterruptedException e) {
                assertTrue(e.getMessage(), false);
            }
        }

        executor.shutdown();
    }

    public static void handle(final Throwable t) {
        if (t instanceof AssertionError) {
            throw (AssertionError) t;
        } else if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        } else {
            throw new RuntimeException(t);
        }
    }
}
