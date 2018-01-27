package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.results.BreinRecommendationResult;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreinRecommendation extends BreinBase<BreinRecommendation> implements
        IExecutable<BreinRecommendationResult> {

    /**
     * Recommends a user items that are similar to what they previously interacted with
     */
    public static final String SUB_RECOMMENDER_SIMILAR_ITEMS = "itemSearch";
    /**
     * Recommends items based on the currently popular items
     */
    public static final String SUB_RECOMMENDER_POPULAR_ITEMS = "topN";
    /**
     * Recommends items that similar users have bought
     */
    public static final String SUB_RECOMMENDER_USERS_LIKE_YOU = "usersLikeYou";
    /**
     * Recommends previously purchased items
     */
    public static final String SUB_RECOMMENDER_RECENTLY_PURCHASED = "purchaseHistory";
    /**
     * Recommends items that have historically trended during similar times
     */
    public static final String SUB_RECOMMENDER_TEMPORAL = "temporal";

    /**
     * contains the number of recommendations - default is 3
     */
    private int numberOfRecommendations = 3;

    /**
     * contains the category for the recommendation
     */
    private String category;

    /**
     * an optional list of subrecommenders to run
     */
    private List<String> subRecommenders = null;

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

    public BreinRecommendation setSubRecommenders(final List<String> subRecommenders) {
        this.subRecommenders = subRecommenders;
        return this;
    }

    public BreinRecommendation setSubRecommenders(final String... subRecomenders) {
        setSubRecommenders(Arrays.asList(subRecomenders));
        return this;
    }

    public List<String> getSubRecommenders() {
        return subRecommenders;
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

        if (BreinUtil.containsValue(getSubRecommenders())) {
            recommendationData.put("recommendationSubRecommenders", getSubRecommenders());
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
    public BreinRecommendationResult execute() {
        return Breinify.recommendation(this);
    }
}
