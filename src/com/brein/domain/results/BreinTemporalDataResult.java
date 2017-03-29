package com.brein.domain.results;

import com.brein.domain.BreinResult;
import com.brein.domain.results.temporaldataparts.BreinWeatherResult;

import java.util.Collections;
import java.util.Map;

public class BreinTemporalDataResult extends BreinResult {
    private static final String WEATHER_KEY = "weather";

    public BreinTemporalDataResult(final Map<String, Object> json) {
        super(json == null ? Collections.emptyMap() : json);
    }

    public BreinTemporalDataResult(final BreinResult result) {
        this(result.getMap());
    }

    public boolean hasWeather() {
        return getMap().containsKey(WEATHER_KEY);
    }

    public BreinWeatherResult getWeather() {
        //noinspection unchecked
        return new BreinWeatherResult((Map<String, Object>) getMap().get(WEATHER_KEY));
    }

    @Override
    public String toString() {
        return "Temporal results with " + (hasWeather() ? getWeather() : "no weather info");
    }
}
