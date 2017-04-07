package com.brein.domain.results.temporaldataparts;

import java.util.Map;

public class BreinLocationResult {
    private static final String COUNTRY_KEY = "country";
    private static final String STATE_KEY = "state";
    private static final String CITY_KEY = "city";
    private static final String GRANULARITY_KEY = "granularity";

    private static final String LAT_KEY = "lat";
    private static final String LON_KEY = "lon";

    private final String country;
    private final String state;
    private final String city;
    private final String granularity;

    private final double lat;
    private final double lon;

    public BreinLocationResult(final Map<String, Object> theJson){
        if(theJson == null){
            country = null;
            state = null;
            city = null;
            granularity = null;

            //you're on null island now
            lat = 0;
            lon = 0;
        }else{
            country = (String) theJson.get(COUNTRY_KEY);
            state = (String) theJson.get(STATE_KEY);
            city = (String) theJson.get(CITY_KEY);
            granularity = (String) theJson.get(GRANULARITY_KEY);

            if(theJson.containsKey(LAT_KEY)){
                lat = (double) theJson.get(LAT_KEY);
            }else{
                lat = 0;
            }

            if(theJson.containsKey(LON_KEY)){
                lon = (double) theJson.get(LON_KEY);
            }else{
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

    public String getGranularity() {
        return granularity;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
