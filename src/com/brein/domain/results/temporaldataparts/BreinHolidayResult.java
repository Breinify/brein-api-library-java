package com.brein.domain.results.temporaldataparts;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BreinHolidayResult {
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

    /**
     * @return A list of descriptions for the holiday. See
     */
    public List<HolidayType> getTypes() {
        return types;
    }

    /**
     * @return Where this holiday comes from
     */
    public HolidaySource getSource() {
        return source;
    }

    /**
     * @return A human readable name of the holiday
     */
    public String getName() {
        return name;
    }

    /**
     * @return if the holiday is generally considered a major holiday
     */
    public boolean isMajor() {
        return getTypes().contains(HolidayType.MAJOR);
    }

    @Override
    public String toString() {
        return getName() + " " + getTypes();
    }

    public enum HolidaySource {
        GOVERNMENT,
        UNITED_NATIONS,
        PUBLIC_INFORMATION,
        UNKNOWN
    }

    public enum HolidayType {
        FEDERAL,
        STATE,
        UN_SPECIAL_DAY,
        CIVIC,
        CIVIL_RIGHTS,
        CULTURAL,
        EDUCATIONAL,
        HALLMARK,
        HISTORIC,
        MAJOR,
        RELIGIOUS,
        SPECIAL_DAY
    }
}
