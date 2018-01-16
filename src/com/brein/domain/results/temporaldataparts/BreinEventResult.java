package com.brein.domain.results.temporaldataparts;

import com.brein.util.JsonHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BreinEventResult {
    private static final String NAME_KEY = "displayName";
    private static final String START_KEY = "startTime";
    private static final String END_KEY = "endTime";
    private static final String CATEGORY_KEY = "categories";
    private static final String SIZE_KEY = "sizeEstimated";
    private final String name;
    private final Long start;
    private final Long end;
    private final List<String> categories;
    private final EventSize size;

    public BreinEventResult(final Map<String, Object> result) {

        name = JsonHelper.getOr(result, NAME_KEY, null);
        start = JsonHelper.getOrLong(result, START_KEY);
        end = JsonHelper.getOrLong(result, END_KEY);

        final String sizeName = JsonHelper.getOr(result, SIZE_KEY, "unknown")
                .toUpperCase();

        categories = JsonHelper.getOr(result, CATEGORY_KEY, Collections.emptyList());

        size = Arrays.asList(EventSize.values()).stream()
                .filter(e -> e.toString().equalsIgnoreCase(sizeName))
                .findAny()
                .orElse(EventSize.UNKNOWN);
    }

    public String getName() {
        return name;
    }

    /**
     * @return The unix timestamp of when the event starts
     */
    public Long getStart() {
        return start;
    }

    /**
     * @return The unix timestamp of when the event is estimated to end
     */
    public Long getEnd() {
        return end;
    }

    public List<String> getCategories() {
        return categories;
    }

    /**
     * @return An estimated size for the event on a scale of 0-4, or null if we couldn't resolve a size
     */
    public EventSize getSize() {
        return size;
    }

    @Override
    public String toString() {
        return getName() + " from " + getStart() + " to " + getEnd();
    }

    public enum EventSize {
        MINOR,
        LOCAL,
        MAJOR,
        NATIONAL,
        INTERNATIONAL,
        UNKNOWN
    }

}
