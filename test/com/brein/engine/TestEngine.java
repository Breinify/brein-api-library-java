package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.domain.BreinActivityType;
import com.brein.domain.BreinCategoryType;
import com.brein.domain.BreinUser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;



/**
 * Run some tests for the UNIREST API
 */
public class TestEngine {

    /**
     * this is my test api key
     */
    final static String API_KEY = "A187-B1DF-E3C5-4BDB-93C4-4729-7B54-E5B1";

    private CountDownLatch lock;
    private boolean status;

    /**
     * Preparation for the test cases
     */
    @Before
    public void setUp() {
        lock = new CountDownLatch(1);
        status = false;
    }

    /**
     * Housekeeping...
     */
    @AfterClass
    public static void tearDown() {

        try {
            /**
             * TODO: replace sleep
             */
            Thread.sleep(5000);
            Unirest.shutdown();
        } catch (Exception e) {
            assertTrue(false);
        }
    }


    /**
     * This is a simple post test.
     *
     * @throws JSONException
     * @throws UnirestException
     */
    @Test
    public void testRequests() throws JSONException, UnirestException {
        final HttpResponse<JsonNode> jsonResponse = Unirest.post("http://httpbin.org/post")
                .header("accept", "application/json")
                .field("param1", "value1")
                .field("param2", "bye").asJson();

        assertTrue(jsonResponse.getHeaders().size() > 0);
        assertTrue(jsonResponse.getBody().toString().length() > 0);
        assertFalse(jsonResponse.getRawBody() == null);
        assertEquals(200, jsonResponse.getStatus());

        final JsonNode json = jsonResponse.getBody();
        assertFalse(json.isArray());
        assertNotNull(json.getObject());
        assertNotNull(json.getArray());
        assertEquals(1, json.getArray().length());
        assertNotNull(json.getArray().get(0));
    }



    /**
     * This is simple get rest test.
     *
     * @throws JSONException
     * @throws UnirestException
     */
    @Test
    public void testGet() throws JSONException, UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("http://httpbin.org/get?name=mark").asJson();
        assertEquals(response.getBody().getObject().getJSONObject("args").getString("name"), "mark");

        response = Unirest.get("http://httpbin.org/get").queryString("name", "mark2").asJson();
        assertEquals(response.getBody().getObject().getJSONObject("args").getString("name"), "mark2");
    }


    /**
     * This is a simple UNIREST test
     * @throws JSONException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testAsync() throws JSONException, InterruptedException, ExecutionException {
        final Future<HttpResponse<JsonNode>> future = Unirest.post("http://httpbin.org/post")
                .header("accept", "application/json")
                .field("param1", "value1")
                .field("param2", "bye").asJsonAsync();

        assertNotNull(future);
        HttpResponse<JsonNode> jsonResponse = future.get();

        assertTrue(jsonResponse.getHeaders().size() > 0);
        assertTrue(jsonResponse.getBody().toString().length() > 0);
        assertFalse(jsonResponse.getRawBody() == null);
        assertEquals(200, jsonResponse.getStatus());

        JsonNode json = jsonResponse.getBody();
        assertFalse(json.isArray());
        assertNotNull(json.getObject());
        assertNotNull(json.getArray());
        assertEquals(1, json.getArray().length());
        assertNotNull(json.getArray().get(0));
    }

    /**
     * This is a simple asynch UNIREST test with callback
     *
     * @throws JSONException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testAsyncCallback() throws JSONException, InterruptedException, ExecutionException {
        Unirest.post("http://httpbin.org/post")
                .header("accept", "application/json")
                .field("param1", "value1")
                .field("param2", "bye")
                .asJsonAsync(new Callback<JsonNode>() {

            public void failed(UnirestException e) {
                fail();
            }

            public void completed(HttpResponse<JsonNode> jsonResponse) {
                assertTrue(jsonResponse.getHeaders().size() > 0);
                assertTrue(jsonResponse.getBody().toString().length() > 0);
                assertFalse(jsonResponse.getRawBody() == null);
                assertEquals(200, jsonResponse.getStatus());

                final JsonNode json = jsonResponse.getBody();
                assertFalse(json.isArray());
                assertNotNull(json.getObject());
                assertNotNull(json.getArray());
                assertEquals(1, json.getArray().length());
                assertNotNull(json.getArray().get(0));

                assertEquals("value1", json.getObject().getJSONObject("form").getString("param1"));
                assertEquals("bye", json.getObject().getJSONObject("form").getString("param2"));

                status = true;
                lock.countDown();
            }

            public void cancelled() {
                fail();
            }
        });

        lock.await(10, TimeUnit.SECONDS);
        assertTrue(status);
    }

    /**
     * Tests the Breinify Backend with one activity call
     *
     * @throws JSONException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testBreinAsyncCallback() throws JSONException, InterruptedException, ExecutionException {

        BreinUser breinUser = new BreinUser("m.recchioni@me.com");
        breinUser.setFirstName("Marco");
        breinUser.setLastName("Recchioni");

        BreinActivity breinActivity = new BreinActivity();
        breinActivity.setBreinUser(breinUser);
        breinActivity.setBreinActivityType(BreinActivityType.LOGIN);
        breinActivity.setDescription("Super-Description");
        breinActivity.setBreinCategoryType(BreinCategoryType.EDUCATION);

        final String requestBody = breinActivity.prepareJsonRequest();

        Unirest.post("http://dev.breinify.com/api/activity")
                .header("accept", "application/json")
                .body(requestBody)
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        fail();
                    }

                    public void completed(HttpResponse<JsonNode> jsonResponse) {

                        status = true;
                        lock.countDown();
                    }

                    public void cancelled() {
                        fail();
                    }
                });

        lock.await(10, TimeUnit.SECONDS);
        assertTrue(status);
    }

    /**
     * Invoke a normal post call to verify the functionality of the UNIREST API
     */
    @Test
    public void testUniRestEngine() {

        try {
            HttpResponse<JsonNode> postResponse = Unirest.post("http://httpbin.org/post")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .field("parameter", "value")
                    .asJson();
        } catch (UnirestException e) {
            assertTrue(false);
        }

    }

    /**
     * Invokes an asynch post call to verify the functionality of the UNIREST API
     */
    @Test
    public void testUniRestEngineAsynch() {

        final Future<HttpResponse<JsonNode>> future = Unirest.post("http://httpbin.org/post")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .field("parameter", "value")
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        System.out.println("failed");
                    }

                    public void completed(HttpResponse<JsonNode> response) {
                        int code = response.getStatus();
                        // Map<String, String> headers = response.getHeaders();
                        JsonNode body = response.getBody();
                        InputStream rawBody = response.getRawBody();
                        System.out.println("completed");

                    }

                    public void cancelled() {
                        System.out.println("cancelled");

                    }

                });

    }

    /**
     * This should run some tests for the jersey client api...
     */
    @Test
    public void testJerseyRestEngine() {

    }


}
