package com.brein.domain.results.temporaldataparts;

import com.brein.util.JsonHelper;

import java.util.Arrays;
import java.util.Map;

public class BreinEventResult {
    private static final String NAME_KEY = "displayName";
    private static final String START_KEY = "startTime";
    private static final String END_KEY = "endTime";
    private static final String CATEGORY_KEY = "category";
    private static final String SIZE_KEY = "sizeEstimated";
    private final String name;
    private final Long start;
    private final Long end;
    private final EventCategory category;
    private final Integer size;
    public BreinEventResult(final Map<String, Object> result) {

        name = JsonHelper.getOr(result, NAME_KEY, null);
        start = JsonHelper.getOrLong(result, START_KEY);
        end = JsonHelper.getOrLong(result, END_KEY);
        final Long innerSize = JsonHelper.getOrLong(result, SIZE_KEY);

        size = innerSize == null || innerSize == -1 ? null : Math.toIntExact(innerSize);

        final String categoryName = JsonHelper.getOr(result, CATEGORY_KEY, "unknown")
                .replaceAll("eventCategory", "")
                .toUpperCase();

        category = Arrays.asList(EventCategory.values()).stream()
                .filter(e -> e.toString().equalsIgnoreCase(categoryName))
                .findAny()
                .orElse(EventCategory.UNKNOWN);
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

    public EventCategory getCategory() {
        return category;
    }

    /**
     * @return An estimated size for the event on a scale of 0-4, or null if we couldn't resolve a size
     */
    public Integer getSize() {
        return size;
    }

    public enum EventCategory {
        CONCERT,
        COMEDY,
        OTHERSHOW,
        POLITICAL,
        SPORTS,
        EDUCATIONAL,
        FITNESS,
        UNKNOWN
    }

}
