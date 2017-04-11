package com.brein.domain.results;

import com.brein.domain.BreinResult;
import com.brein.domain.results.temporaldataparts.BreinHolidayResult;
import com.brein.domain.results.temporaldataparts.BreinLocationResult;
import com.brein.domain.results.temporaldataparts.BreinWeatherResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BreinTemporalDataResult extends BreinResult {
    private static final String WEATHER_KEY = "weather";
    private static final String TIME_KEY = "time";
    private static final String LOCAL_TIME_KEY = "localFormatIso8601";
    private static final String EPOCH_TIME_KEY = "epochFormatIso8601";
    private static final String LOCATION_KEY = "location";
    private static final String HOLIDAY_LIST_KEY = "holidays";

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

    public boolean hasLocalDateTime() {
        //noinspection unchecked
        return getMap().containsKey(TIME_KEY) &&
                ((Map<String, Object>) getMap().get(TIME_KEY)).containsKey(LOCAL_TIME_KEY);
    }

    public LocalDateTime getLocalDateTime() {
        //noinspection unchecked
        return LocalDateTime.parse(((Map<String, Object>) getMap().get(TIME_KEY)).get(LOCAL_TIME_KEY).toString(),
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public boolean hasEpochDateTime() {
        //noinspection unchecked
        return getMap().containsKey(TIME_KEY) &&
                ((Map<String, Object>) getMap().get(TIME_KEY)).containsKey(EPOCH_TIME_KEY);
    }

    public LocalDateTime getEpochDateTime() {
        //noinspection unchecked
        return LocalDateTime.parse(((Map<String, Object>) getMap().get(TIME_KEY)).get(EPOCH_TIME_KEY).toString(),
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public boolean hasLocation() {
        return getMap().containsKey(LOCATION_KEY);
    }

    public BreinLocationResult getLocation() {
        //noinspection unchecked
        return new BreinLocationResult((Map<String, Object>) getMap().get(LOCATION_KEY));
    }

    public boolean hasHolidays() {
        return getMap().containsKey(HOLIDAY_LIST_KEY);
    }

    public List<BreinHolidayResult> getHolidays() {
        //noinspection unchecked
        return ((List<Map<String, Object>>) getMap().get(HOLIDAY_LIST_KEY)).stream()
                .map(BreinHolidayResult::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Temporal results with " + (hasWeather() ? getWeather() : "no weather info");
    }

}
