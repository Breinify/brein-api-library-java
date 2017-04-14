package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;

import java.util.HashMap;
import java.util.Map;

public class BreinRecommendation extends BreinBase<BreinRecommendation> implements IExecutable<BreinResult> {

    /**
     * contains the number of recommendations - default is 3
     */
    private int numberOfRecommendations = 3;

    /**
     * contains the category for the recommendation
     */
    private String category;

    /**
     * get the number of recommendations
     *
     * @return number
     */
    public int getNumberOfRecommendations() {
        return numberOfRecommendations;
    }

    /**
     * set the number of recommendations
     *
     * @param numberOfRecommendations number of recommendations
     *
     * @return self
     */
    public BreinRecommendation setNumberOfRecommendations(final int numberOfRecommendations) {
        this.numberOfRecommendations = numberOfRecommendations;
        return this;
    }

    /**
     * get the recommendation category
     *
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * set the recommendation category
     *
     * @param category contains the category
     *
     * @return self
     */
    public BreinRecommendation setCategory(final String category) {
        this.category = category;
        return this;
    }

    @Override
    public String getEndPoint(final BreinConfig config) {
        return config.getRecommendationEndpoint();
    }

    @Override
    public void prepareRequestData(final BreinConfig config, final Map<String, Object> requestData) {

        // recommendation data
        final Map<String, Object> recommendationData = new HashMap<>();

        // check optional field(s)
        if (BreinUtil.containsValue(getCategory())) {
            recommendationData.put("recommendationCategory", getCategory());
        }

        // mandatory field
        recommendationData.put("numRecommendations", getNumberOfRecommendations());
        requestData.put("recommendation", recommendationData);
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

    @Override
    public BreinResult execute() {
        return Breinify.recommendation(this);
    }
}
