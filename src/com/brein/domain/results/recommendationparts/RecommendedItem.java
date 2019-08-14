package com.brein.domain.results.recommendationparts;

import java.util.Collections;
import java.util.Map;

public class RecommendedItem {
    public static final String RESULT_ID = "dataIdExternal";
    public static final String RESULT_WEIGHT = "weight";
    public static final String RESULT_ADDITIONAL = "additionalData";

    private final String resultId;
    private final double resultWeight;
    private final Map<String, Object> data;

    /**
     * Constructor that uses a json from the api response
     *
     * @param json The map from the list of items from the api response
     */
    public RecommendedItem(final Map<String, Object> json) {
        resultId = (String) json.getOrDefault(RESULT_ID, "unknown");
        resultWeight = (double) json.getOrDefault(RESULT_WEIGHT, -1.0);
        //noinspection unchecked
        data = (Map<String, Object>) json.getOrDefault(RESULT_ADDITIONAL, Collections.emptyMap());
    }

    /**
     * Constructor usable for testing, all fields should be non-null
     *
     * @param resultId       The id of the item, generally a product id, cannot be {@code null}
     * @param resultWeight   The weight of the recommendation, bounded by 0 and 1
     * @param additionalData Additional fields for the recommended item, cannot be {@code null}
     */
    public RecommendedItem(final String resultId,
                           final double resultWeight,
                           final Map<String, Object> additionalData) {
        this.resultId = resultId;
        this.resultWeight = resultWeight;
        this.data = additionalData;
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

    public <T> T getAdditionalData(final String key, final Class<T> clazz, final T def) {
        final Object value = getAdditionalData().get(key);
        if (value == null) {
            return def;
        } else if (clazz.isAssignableFrom(value.getClass())) {
            return clazz.cast(value);
        } else {
            return def;
        }
    }

    @Override
    public String toString() {
        return getResultId() + " (" + getResultWeight() + ") " + getAdditionalData();
    }
}
