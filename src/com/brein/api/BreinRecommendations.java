package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.results.BreinRecommendationResults;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BreinRecommendations extends BreinBase<BreinRecommendations>
        implements IExecutable<BreinRecommendationResults> {

    public static final String ATTR_RECS = "recommendations";

    private final List<BreinRecommendation> recommendations = new ArrayList<>();

    public List<BreinRecommendation> getRecommendationRequests() {
        return recommendations;
    }

    public BreinRecommendations addRecommendation(final BreinRecommendation reqToAdd) {
        recommendations.add(reqToAdd);
        return this;
    }

    @Override
    public BreinRecommendationResults execute() {
        return Breinify.recommendation(this);
    }

    @Override
    public String getEndPoint(final BreinConfig config) {
        return config.getRecommendationEndpoint();
    }

    @Override
    public void prepareRequestData(final BreinConfig config, final Map<String, Object> requestData) {

        requestData.put("recommendations", recommendations.stream()
                .map(r -> {
                    final Map<String, Object> innerRes = new HashMap<>();
                    r.prepareRequestData(config, innerRes);
                    return innerRes.get("recommendation");
                })
                .collect(Collectors.toList()));
    }

    /**
     * Generates the signature for the request
     *
     * @return full signature
     */
    @Override
    public String createSignature(final BreinConfig config, final Map<String, Object> requestData) {
        final long unixTimestamp = BreinMapUtil.getNestedValue(requestData, UNIX_TIMESTAMP_FIELD);

        final String message = String.format("%d", unixTimestamp);
        return BreinUtil.generateSignature(message, config.getSecret());
    }
}
