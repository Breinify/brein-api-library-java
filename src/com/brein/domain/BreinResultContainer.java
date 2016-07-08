package com.brein.domain;

import java.util.Map;

/**
 * contains the json data
 */
public class BreinResultContainer {

    private final Map<String, Object> map;

    public BreinResultContainer(final Map<String, Object> map) {
        this.map = map;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T get(final String attr) {
        return (T) map.get(attr);
    }

    public boolean has(final String attr) {
        return get(attr) != null;
    }

}
