package com.brein.engine;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Future;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class TestEngine {

    /**
     * Housekeeping...
     */
    @AfterClass
    public static void tearDown() {
        try {
            Unirest.shutdown();
        } catch (IOException e) {
            assertTrue(false);
        }
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
