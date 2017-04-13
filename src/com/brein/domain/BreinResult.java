package com.brein.domain;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Contains the result of an Brein Request when invoking a
 * lookup
 */
public class BreinResult {

    private int status;

    /**
     * contains the collected data as map
     */
    private final Map<String, Object> map;

    /**
     * creates a brein result object
     *
     * @param jsonResponse as json string
     */
    @SuppressWarnings("unchecked")
    public BreinResult(final String jsonResponse) {
        map = new Gson().fromJson(jsonResponse, Map.class);
    }

    public BreinResult(final String jsonResponse, final int status) {
        this(jsonResponse);
        this.status = status;
    }

    public BreinResult(final Map<String, Object> json) {
        map = json;
    }

    /**
     * retrieves the object according to the requested key
     *
     * @param key to look for
     * @param <T> Object
     *
     * @return Object retrieved
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        return (T) map.get(key);
    }

    /**
     * checks if key exists in map
     *
     * @param key to check
     *
     * @return true or false
     */
    public boolean has(final String key) {
        return get(key) != null;
    }

    /**
     * provides the map containing the results
     *
     * @return map of String, Object
     */
    public Map<String, Object> getMap() {
        return map;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(final String key) {
        return map == null ? null : (T) map.get(key);
    }

    public boolean hasValue(final String key) {
        return map != null && map.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getNestedValue(final String... key) {
        if (map == null) {
            return null;
        }

        Map<String, Object> currentMap = map;

        Object value = null;
        int i = 0;
        for (; i < key.length; i++) {
            final String k = key[i];

            value = currentMap.get(k);
            if (value == null) {
                break;
            } else if (Map.class.isInstance(value)) {
                currentMap = Map.class.cast(value);
            } else if (i < key.length - 1) {
                break;
            }
        }

        if (i == key.length) {
            return (T) value;
        } else {
            return null;
        }
    }

    public boolean hasNestedValue(final String... key) {
        if (map == null) {
            return false;
        }

        Map<String, Object> currentMap = map;

        int i = 0;
        for (; i < key.length; i++) {
            final String k = key[i];

            final Object value = currentMap.get(k);
            if (value == null) {
                break;
            } else if (Map.class.isInstance(value)) {
                //noinspection unchecked
                currentMap = Map.class.cast(value);
            } else if (i < key.length - 1) {
                break;
            }
        }

        return i == key.length;
    }

    public String getMessage() {

        if (has("message")) {
            return get("message");
        }

        return null;
    }

    /**
     * returns the http request status
     *
     * @return valus like 200, 403...
     */
    public int getStatus() {
        return status;
    }

    /**
     * sets the http request status
     *
     * @param status contains the value
     */
    public void setStatus(final int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
