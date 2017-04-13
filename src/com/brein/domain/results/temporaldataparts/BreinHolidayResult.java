package com.brein.domain.results.temporaldataparts;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BreinHolidayResult {
    public enum HolidaySource {
        GOVERNMENT,
        UNITED_NATIONS,
        PUBLIC_INFORMATION,
        UNKNOWN
    }

    public enum HolidayType {
        NATIONAL_FEDERAL,
        STATE_FEDERAL,
        LEGAL,
        CIVIC,
        SPECIAL_DAY,
        EDUCATIONAL,
        HALLMARK,
        CULTURAL,
        RELIGIOUS
    }

    private static final String HOLIDAY_TYPE_KEY = "types";
    private static final String HOLIDAY_SOURCE_KEY = "source";
    private static final String HOLIDAY_NAME_KEY = "holiday";

    private final List<HolidayType> types;
    private final HolidaySource source;
    private final String name;

    public BreinHolidayResult(final Map<String, Object> result) {

        if (result == null || result.isEmpty()) {
            types = Collections.emptyList();
            source = HolidaySource.UNKNOWN;
            name = null;
        } else {
            if (result.containsKey(HOLIDAY_NAME_KEY)) {
                name = result.get(HOLIDAY_NAME_KEY).toString();
            } else {
                name = null;
            }

            if (result.containsKey(HOLIDAY_SOURCE_KEY)) {
                source = HolidaySource.valueOf(result.get(HOLIDAY_SOURCE_KEY)
                        .toString()
                        .replace(' ', '_')
                        .toUpperCase());
            } else {
                source = HolidaySource.UNKNOWN;
            }

            if (result.containsKey(HOLIDAY_TYPE_KEY)) {
                //noinspection unchecked
                types = ((List<String>) result.get(HOLIDAY_TYPE_KEY)).stream()
                        .map(String::toString)
                        .map(HolidayType::valueOf)
                        .collect(Collectors.toList());
            } else {
                types = Collections.emptyList();
            }
        }
    }

    public List<HolidayType> getTypes() {
        return types;
    }

    public HolidaySource getSource() {
        return source;
    }

    public String getName() {
        return name;
    }
}
