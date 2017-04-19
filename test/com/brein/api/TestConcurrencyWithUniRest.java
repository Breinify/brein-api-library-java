package com.brein.api;

import com.brein.domain.BreinCategoryType;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.domain.results.BreinTemporalDataResult;
import com.brein.engine.BreinEngineType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("Duplicates")
public class TestConcurrencyWithUniRest extends ApiTestBase {

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

    @Before
    public void setUp() {
        Breinify.setConfig(new BreinConfig(VALID_API_KEY)
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE));
    }

    @Test
    public void testTemporalConcurrency() {
        threadTesting(10, () -> {
            for (int ct = 0; ct < 100; ct++) {

                // invoke temporalData
                final BreinTemporalDataResult result = Breinify.temporalData(new BreinTemporalData()
                        .setUser("email", "fred.firestone@email.com")
                        .setClientIpAddress("74.115.209.58")
                        .setLongitude(Math.random() * 50 - 98 - 25)
                        .setLatitude(Math.random() * 10 + 39 - 5));

                Assert.assertEquals(200, result.getStatus());
                Assert.assertNotNull(result);
            }
        });
    }

    @Test
    public void testPageVisitConcurrency() {
        threadTesting(10, () -> {
            for (int ct = 0; ct < 100; ct++) {
                final BreinUser breinUser = new BreinUser()
                        .setEmail("User.Name@email.com")
                        .setFirstName("Marco")
                        .setLastName("Recchioni")
                        .setDateOfBirth(11, 20, 1999)
                        .set("deviceId", "DD-EEEEE")
                        .set("imei", "55544455333")
                        .setSessionId("r3V2kDAvFFL_-RBhuc_-Dg")
                        .setAdditional("url", "https://sample.com.au/home")
                        .setAdditional("refererr", "https://sample.com.au/track")
                        .setAdditional("userAgent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                                "(KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");

                final BreinActivity activity = new BreinActivity()
                        .setUnixTimestamp(Instant.now().getEpochSecond())
                        .setUser(breinUser)
                        .setCategory(BreinCategoryType.APPAREL)
                        .setActivityType("TestConcurrencyWithUniRest.testPageVisitConcurrency")
                        .setDescription("your description")
                        .setTag("t1", 0.0)
                        .setTag("t2", 5)
                        .setTag("t3", "String")
                        .setClientIpAddress("10.11.12.13");

                asyncTest(cb -> Breinify.activity(activity, res -> {
                    assertEquals(200, res.getStatus());
                    cb.set(true);
                }), 2000);
            }
        });
    }

    /**
     * Test case for recommendation with 1000 calls
     */
    @Test
    public void testRecommendationConcurrency() {
        threadTesting(10, () -> {
            for (int ct = 0; ct < 100; ct++) {
                final BreinUser localBreinUser = new BreinUser()
                        .setEmail("fred.firestone@email.com")
                        .setSessionId("1133AADDDEEE");

                final int numberOfRecommendations = 10;
                final BreinRecommendation data = new BreinRecommendation()
                        .setUser(localBreinUser)
                        .setNumberOfRecommendations(numberOfRecommendations);

                final BreinResult response = Breinify.recommendation(data);

                Assert.assertEquals(200, response.getStatus());
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
}
