package com.brein.domain.results.recommendationparts;

import java.util.Collections;
import java.util.Map;

public class RecommendedItem {
    private static final String RESULT_ID = "dataIdExternal";
    private static final String RESULT_WEIGHT = "weight";
    private static final String RESULT_ADDITIONAL = "additionalData";

    private final String resultId;
    private final double resultWeight;
    private final Map<String, Object> data;

    public RecommendedItem(final Map<String, Object> json) {
        resultId = (String) json.getOrDefault(RESULT_ID, "unknown");
        resultWeight = (double) json.getOrDefault(RESULT_WEIGHT, -1.0);
        //noinspection unchecked
        data = (Map<String, Object>) json.getOrDefault(RESULT_ADDITIONAL, Collections.emptyMap());
    }

    public String getResultId() {
        return resultId;
    }

    public double getResultWeight() {
        return resultWeight;
    }

    public Map<String, Object> getAdditionalData() {
        return data;
    }

    public Object getAdditionalData(final String key) {
        return data.get(key);
    }

    @Override
    public String toString() {
        return getResultId() + " (" + getResultWeight() + ") " + getAdditionalData();
    }
}
