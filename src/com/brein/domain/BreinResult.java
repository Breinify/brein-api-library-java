package com.brein.domain;

import com.brein.util.BreinMapUtil;

import java.util.Collections;
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

    public BreinResult(final String errorMsg, final int status) {
        this.map = Collections.singletonMap("errorMsg", errorMsg);
        this.status = status == 200 ? 500 : status;
    }

    public BreinResult(final Exception e, final int status) {
        this.map = Collections.singletonMap("error", e);
        this.status = status == 200 ? 500 : status;
    }

    public BreinResult(final Map<String, Object> error, final int status) {
        this.map = error;
        this.status = status == 200 ? 500 : status;
    }

    public BreinResult(final Map<String, Object> json) {
        this.map = json;
        this.status = 200;
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

    public <T> T getNestedValue(final String... keys) {
        return BreinMapUtil.getNestedValue(map, keys);
    }

    public boolean hasNestedValue(final String... keys) {
        return BreinMapUtil.hasNestedValue(map, keys);
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
