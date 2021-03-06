package com.brein.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Helper class for building Maps of type <String,T>
 * @param <T>
 */
public class MapBuilder<T> {
    protected final Map<String, T> map = new HashMap<>();

    public static <T> MapBuilder<T> fromMap(final Map<String, T> map) {
        final MapBuilder<T> result = new MapBuilder<>();
        return result.set(map);
    }

    public MapBuilder<T> set(final String key, final T value) {
        map.put(key, value);

        return this;
    }

    @SuppressWarnings("unchecked")
    public MapBuilder<T> set(final Map map) {
        map.forEach((key, value) -> set(String.valueOf(key), (T) value));
        return this;
    }

    public MapBuilder<T> set(final MapBuilder map) {
        return set(map.map);
    }

    public Map<String, T> asMap() {
        return map;
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public void forEach(final BiConsumer<String, T> action) {
        map.forEach(action);
    }
}
