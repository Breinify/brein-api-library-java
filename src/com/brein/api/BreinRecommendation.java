package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.results.BreinRecommendationResult;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreinRecommendation extends BreinBase<BreinRecommendation>
        implements IExecutable<BreinRecommendationResult> {

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
     * Recommends items that have are ordered through some config-defined manner
     */
    public static final String SUB_RECOMMENDER_CUSTOM_SORT = "customSort";

    /**
     * contains the number of recommendations - default is 3
     */
    private int numberOfRecommendations = 3;

    /**
     * contains the category for the recommendation
     */
    private List<String> categories;

    /**
     * contains the category for the recommendation
     */
    private List<String> categoriesBlacklist;

    /**
     * an optional list of subrecommenders to run
     */
    private List<String> subRecommenders = null;

    /**
     * an optional list of subrecommenders to use to inhibit recs
     */
    private List<String> subInhibitors = null;

    /**
     * an optional list of blockers to use to block recs
     */
    private List<String> blockers = null;

    /**
     * Should result caching be disabled
     */
    private boolean disableCaching = false;

    /**
     * An optional start time for recommendations
     */
    private long recStartTime = -1;

    /**
     * An optional end time for recommendations
     */
    private long recEndTime = -1;

    /**
     * A list of items to get a recommendation for
     */
    private List<String> itemToItemRecs;

    /**
     * Used to keep track of different locations for requests of recommendations
     */
    private String recommendationQueryName;

    /**
     * Minimum number of items in stock for a returned recommendation
     */
    private Double minQuantity = null;

    /**
     * Additional parameters to be passed into the sub recommenders
     */
    private Map<String, Object> recommendationAdditionalParameters;

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
    public List<String> getCategories() {
        return categories;
    }

    /**
     * set the recommendation category
     *
     * @param category contains the category
     *
     * @return self
     */
    public BreinRecommendation setCategories(final String... category) {
        this.categories = Arrays.asList(category);
        return this;
    }

    /**
     * gets categories that should not be returned
     *
     * @return the blacklisted categories
     */
    public List<String> getCategoriesBlacklist() {
        return categoriesBlacklist;
    }

    /**
     * set the categories that should not be returned
     *
     * @param category the blacklisted categories
     *
     * @return self
     */
    public BreinRecommendation setCategoriesBlacklist(final String... category) {
        this.categoriesBlacklist = Arrays.asList(category);
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

    public BreinRecommendation setMinQuantity(final Double minQuantity) {
        this.minQuantity = minQuantity;
        return this;
    }

    public Double getMinQuantity() {
        return minQuantity;
    }

    /**
     * Should this request disable caching?
     *
     * @return if caching should be disabled
     */
    public boolean isDisableCaching() {
        return disableCaching;
    }

    /**
     * Set if we want to disable caching
     *
     * @param disableCaching the caching state
     */
    public BreinRecommendation setDisableCaching(final boolean disableCaching) {
        this.disableCaching = disableCaching;
        return this;
    }

    /**
     * @return When recommendations should start
     */
    public long getRecStartTime() {
        return recStartTime;
    }

    /**
     * @param recStartTime When recommendations should start
     */
    public BreinRecommendation setRecStartTime(final long recStartTime) {
        this.recStartTime = recStartTime;
        return this;
    }

    /**
     * @return When recommendations should end
     */
    public long getRecEndTime() {
        return recEndTime;
    }

    /**
     * @param recEndTime When recommendations should end
     */
    public BreinRecommendation setRecEndTime(final long recEndTime) {
        this.recEndTime = recEndTime;
        return this;
    }

    /**
     * @return Items for an item(s) to items recommendation
     */
    public List<String> getItemToItemRecs() {
        return itemToItemRecs;
    }

    /**
     * @param itemToItemRecs the item(s) that an item to item recommendation should be done for
     */
    public BreinRecommendation setItemToItemRecs(final List<String> itemToItemRecs) {
        this.itemToItemRecs = itemToItemRecs;
        return this;
    }

    public String getRecommendationQueryName() {
        return recommendationQueryName;
    }

    public BreinRecommendation setRecommendationQueryName(final String recommendationQueryName) {
        this.recommendationQueryName = recommendationQueryName;
        return this;
    }

    @Override
    public BreinRecommendationResult execute() {
        return Breinify.recommendation(this);
    }

    public List<String> getSubInhibitors() {
        return subInhibitors;
    }

    public BreinRecommendation setSubInhibitors(final List<String> subInhibitors) {
        this.subInhibitors = subInhibitors;
        return this;
    }

    public List<String> getBlockers() {
        return blockers;
    }

    public BreinRecommendation setBlockers(final List<String> blockers) {
        this.blockers = blockers;
        return this;
    }

    public Map<String, Object> getRecommendationAdditionalParameters() {
        return recommendationAdditionalParameters;
    }

    public BreinRecommendation setRecommendationAdditionalParameters(final Map<String, Object>
                                                                             recommendationAdditionalParameters) {
        this.recommendationAdditionalParameters = recommendationAdditionalParameters;
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

        // check optional field
        if (BreinUtil.containsValue(getCategories())) {
            recommendationData.put("recommendationCategories", getCategories());
        }

        if (BreinUtil.containsValue(getCategories())) {
            recommendationData.put("recommendationCategoriesBlacklist", getCategoriesBlacklist());
        }

        if (BreinUtil.containsValue(getSubRecommenders())) {
            recommendationData.put("recommendationSubRecommenders", getSubRecommenders());
        }

        if (BreinUtil.containsValue(getSubInhibitors())) {
            recommendationData.put("recommendationSubInhibitors", getSubInhibitors());
        }

        if (BreinUtil.containsValue(getBlockers())) {
            recommendationData.put("recommendationSubBlockers", getBlockers());
        }

        if (BreinUtil.containsValue(getRecommendationAdditionalParameters())) {
            recommendationData.put("recommendationAdditionalParameters", getRecommendationAdditionalParameters());
        }

        if (getRecStartTime() >= 0) {
            recommendationData.put("recommendationAtTime", getRecStartTime());
        }

        if (getRecEndTime() >= 0) {
            recommendationData.put("recommendationUntilTime", getRecEndTime());
        }

        if (isDisableCaching()) {
            recommendationData.put("recommendationDisableCache", true);
        }

        if (getItemToItemRecs() != null && !getItemToItemRecs().isEmpty()) {
            recommendationData.put("recommendationForItems", getItemToItemRecs());
        }

        if (getRecommendationQueryName() != null) {
            recommendationData.put("recommendationQueryName", getRecommendationQueryName());
        }

        if (getMinQuantity() != null) {
            recommendationData.put("recommendationMinQuantity", getMinQuantity());
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
}
