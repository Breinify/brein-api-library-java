package com.brein.domain.results.temporaldataparts;

import com.brein.util.BreinMapUtil;

import java.util.Collections;
import java.util.Map;

public class BreinLocationResult {
    private static final String COUNTRY_KEY = "country";
    private static final String STATE_KEY = "state";
    private static final String CITY_KEY = "city";
    private static final String GRANULARITY_KEY = "granularity";
    private static final String GEOJSON_KEY = "geojson";

    private static final String LAT_KEY = "lat";
    private static final String LON_KEY = "lon";

    private final String country;
    private final String state;
    private final String city;
    private final String granularity;

    private final Map<String, Map<String, Object>> geojsons;

    private final double lat;
    private final double lon;

    public BreinLocationResult(final Map<String, Object> json) {
        if (json == null) {
            country = null;
            state = null;
            city = null;
            granularity = null;
            geojsons = Collections.emptyMap();

            //you're on null island now
            lat = 0;
            lon = 0;
        } else {
            country = BreinMapUtil.getNestedValue(json, COUNTRY_KEY);
            state = BreinMapUtil.getNestedValue(json, STATE_KEY);
            city = BreinMapUtil.getNestedValue(json, CITY_KEY);
            granularity = BreinMapUtil.getNestedValue(json, GRANULARITY_KEY);
            geojsons = BreinMapUtil.getNestedValue(json, GEOJSON_KEY);

            if (json.containsKey(LAT_KEY)) {
                lat = (double) json.get(LAT_KEY);
            } else {
                lat = 0;
            }

            if (json.containsKey(LON_KEY)) {
                lon = (double) json.get(LON_KEY);
            } else {
                lon = 0;
            }
        }
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    /**
     * @return The estimated accuracy of the resolved location
     */
    public String getGranularity() {
        return granularity;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public Map<String, Map<String, Object>> getGeoJsons() {
        return Collections.unmodifiableMap(this.geojsons);
    }

    public Map<String, Object> getGeoJson(final String type) {
        return this.geojsons.get(type);
    }

    public String toString() {
        return getCity() + ", " + getState() + " " + getCountry();
    }
}
