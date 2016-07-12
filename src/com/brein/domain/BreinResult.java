package com.brein.domain;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Contains the result of an Brein Request when invoking a
 * lookup
 */
public class BreinResult {

    /**
     * contains the collected data as map
     */
    private final Map<String, Object> map;

     /**
     * creates a brein result object
     * @param jsonResponse as json string
     */
    @SuppressWarnings("unchecked")
    public BreinResult(final String jsonResponse) {
        map = new Gson().fromJson(jsonResponse, Map.class);
    }

    /**
     * retrieves the object according to the requested key
     * @param key to look for
     * @param <T> Object
     * @return Object retrieved
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        return (T) map.get(key);
    }

    /**
     * checks if key exists in map
     * @param key to check
     * @return true or false
     */
    public boolean has(final String key) {
        return get(key) != null;
    }

}
