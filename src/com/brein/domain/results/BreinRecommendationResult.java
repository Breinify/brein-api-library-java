package com.brein.domain.results;

import com.brein.domain.BreinResult;
import com.brein.domain.results.recommendationparts.RecommendedItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BreinRecommendationResult extends BreinResult {
    private static final String RESULT_FIELD = "result";
    private final List<RecommendedItem> items;

    public BreinRecommendationResult(final Map<String, Object> json) {
        super(json);

        items = new ArrayList<>();
        //noinspection unchecked
        for (final Map<String, Object> inner :
                (List<Map<String, Object>>) json.getOrDefault(RESULT_FIELD, Collections.emptyList())) {
            items.add(new RecommendedItem(inner));
        }
    }

    public List<RecommendedItem> getRecommendedItems() {
        return items;
    }
}
