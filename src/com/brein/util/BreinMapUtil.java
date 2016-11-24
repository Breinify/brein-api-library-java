package com.brein.util;

import com.brein.api.CheckFunction;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for map related functions
 */
public class BreinMapUtil {

    /**
     * Generic method to add an value from a data map if it contains a valid value
     *
     * @param dataMap  map of data
     * @param jsonData request
     */
    public static void fillMap(final Map<String, Object> dataMap,
                               final JsonObject jsonData) {

        dataMap.entrySet().forEach(entry -> {
            if (entry.getValue().getClass() == String.class) {
                jsonData.addProperty(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue().getClass() == Double.class ||
                    entry.getValue().getClass() == Integer.class) {
                jsonData.addProperty(entry.getKey(), (Number) entry.getValue());
            } else if (entry.getValue().getClass() == Boolean.class) {
                jsonData.addProperty(entry.getKey(), (Boolean) entry.getValue());
            } else if (entry.getValue().getClass() == HashMap.class) {
                // iterate over map elements
                final JsonObject extra = new JsonObject();
                @SuppressWarnings("unchecked") final Map<String, Object> innerMap = (Map<String, Object>) entry.getValue();
                BreinMapUtil.fillMap(innerMap, extra);

                if (extra.size() > 0) {
                    jsonData.add(entry.getKey(), extra);
                }
            }
        });
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
}
