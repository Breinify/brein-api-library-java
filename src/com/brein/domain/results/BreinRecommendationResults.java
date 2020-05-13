package com.brein.domain.results;

import com.brein.domain.BreinResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Wrapper class for multiple recommendation result objects
 */
public class BreinRecommendationResults extends BreinResult {
    private static final String RESULTS_FIELD = "results";
    private List<BreinRecommendationResult> results;

    public BreinRecommendationResults(final Map<String, Object> json) {
        super(json);
        results = new ArrayList<>();

        //noinspection unchecked
        for (final Map<String, Object> inner :
                (List<Map<String, Object>>) json.getOrDefault(RESULTS_FIELD, Collections.emptyList())) {
            results.add(new BreinRecommendationResult(inner));
        }
    }

    public int getNumberQueries() {
        return results.size();
    }

    public BreinRecommendationResult getMultiResponse(final int index) {
        return results.get(index);
    }
}
