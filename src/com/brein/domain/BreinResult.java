package com.brein.domain;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Contains the result of an Brein Request when invoking a
 * lookup
 */
public class BreinResult {

    /**
     * contains the collected data
     */
    private final BreinResultContainer resultContainer;

     /**
     * creates a brein response object
     * @param jsonResponse as json string
     */
    @SuppressWarnings("unchecked")
    public BreinResult(final String jsonResponse) {

        final Map<String,Object> result = new Gson().fromJson(jsonResponse, Map.class);
        resultContainer = new BreinResultContainer(result);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        return (T) resultContainer.get(key);
    }

}
