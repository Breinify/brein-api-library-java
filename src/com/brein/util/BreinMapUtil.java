package com.brein.util;

import com.brein.api.CheckFunction;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for map related functions
 */
public class BreinMapUtil {

    public BreinMapUtil() {
    }

    /**
     * Executes the actions within the map. Checks if the value is valid and if this is
     * the case then the property will be added to the json structure.
     *
     * @param jsonObject  structure that will be added with a property
     * @param functionMap map of actions (aka methods)
     */
    public static void executeMapFunctions(final JsonObject jsonObject,
                                           final Map<String, CheckFunction> functionMap) {

        functionMap.entrySet().forEach(action -> {
            if (BreinUtil.containsValue(action.getValue().invoke())) {
                jsonObject.addProperty(action.getKey(), action.getValue().invoke());
            }
        });
    }

    /**
     * Map Helper method used to copy a hashmap of type <String, Object>
     *
     * @param source contains the original map
     * @return a copy of the map or null if source is null
     */
    public static Map<String, Object> copyMap(final Map<String, Object> source) {
        if (source == null) {
            return null;
        }

        final HashMap<String, Object> copyMap = new HashMap<>();

        for (final Map.Entry<String, Object> entry : source.entrySet()) {

            final String key = entry.getKey();
            final Object value = entry.getValue();

            if (value.getClass() == HashMap.class) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> newMap = BreinMapUtil.copyMap((Map<String, Object>) value);
                copyMap.put(key, newMap);
            } else {
                copyMap.put(key, value);
            }

        }

        return copyMap;
    }

}
