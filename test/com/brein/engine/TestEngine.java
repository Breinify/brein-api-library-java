package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.domain.ActivityType;
import com.brein.domain.BreinRequest;
import com.brein.domain.BreinUser;
import com.brein.domain.Category;
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
 *
 */
public class TestEngine {

    private CountDownLatch lock;
    private boolean status;

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
            Thread.sleep(5000);
            Unirest.shutdown();
        } catch (Exception e) {
            assertTrue(false);
        }
    }


    /**
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
     *
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
        breinActivity.setApiKey("5d8b-064c-f007-4f92-a8dc-d06b-56b4-fad8");
        breinActivity.setActivityType(ActivityType.LOGIN);
        breinActivity.setDescription("Super-Description");
        breinActivity.setCategory(new Category("Super-Category"));

        final long unixTimestamp = System.currentTimeMillis() / 1000L;
        final BreinRequest breinRequest = new BreinRequest(breinActivity, unixTimestamp);
        final String requestBody = breinRequest.toJson();

        Unirest.post("https://api.breinify.com")
                .header("accept", "application/json")
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
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
     * Invoke a normal post call
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
     *
     */
    @Test
    public void testUniRestEngineAsych() {

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


    @Test
    public void testJerseyRestEngine() {

    }


}
