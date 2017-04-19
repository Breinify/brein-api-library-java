package com.brein.domain.results.temporaldataparts;

import com.brein.util.JsonHelpers;

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

        name = JsonHelpers.getOr(result, NAME_KEY, null);
        start = JsonHelpers.getOrLong(result, START_KEY);
        end = JsonHelpers.getOrLong(result, END_KEY);
        final Long innerSize = JsonHelpers.getOrLong(result, SIZE_KEY);

        size = innerSize == null || innerSize == -1 ? null : Math.toIntExact(innerSize);

        final String categoryName = JsonHelpers.getOr(result, CATEGORY_KEY, "unknown")
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

    public Long getStart() {
        return start;
    }

    public Long getEnd() {
        return end;
    }

    public EventCategory getCategory() {
        return category;
    }

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
