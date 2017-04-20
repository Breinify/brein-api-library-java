package com.brein.util;

import java.util.Map;

public class JsonHelper {

    private JsonHelper() {
        /*
         * Utility classes, which are a collection of static members,
         * are not meant to be instantiated.
         */
    }

    /**
     * Tries to grab an element from a json
     *
     * @param json         the parsed json
     * @param key          the key to look at
     * @param defaultValue the fallback value if we can't find the key
     * @param <T>          the class of the value
     *
     * @return the key value or defaultValue
     */
    public static <T> T getOr(final Map<String, Object> json, final String key, final T defaultValue) {
        if (json == null) {
            return defaultValue;
        } else if (json.containsKey(key)) {
            //noinspection unchecked
            return (T) json.get(key);
        } else {
            return defaultValue;
        }
    }

    /**
     * There isn't a clear difference between doubles and longs in jsons, so we have to specifically cast longs
     */
    public static Long getOrLong(final Map<String, Object> json, final String key) {
        if (json == null) {
            return null;
        } else if (json.containsKey(key)) {
            //noinspection unchecked
            return ((Double) json.get(key)).longValue();
        } else {
            return null;
        }
    }
}
