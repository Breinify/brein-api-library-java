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

    public static final int DEF_NUM_RESULTS = 3;

    public static final String ATTR_REC_CATEGORIES = "recommendationCategories";
    public static final String ATTR_REC_CATEGORIES_BLACKLISTED = "recommendationCategoriesBlacklist";
    public static final String ATTR_REC_SUB_RECOMMENDERS = "recommendationSubRecommenders";
    public static final String ATTR_REC_SUB_INHIBITORS = "recommendationSubInhibitors";
    public static final String ATTR_REC_SUB_BLOCKERS = "recommendationSubBlockers";
    public static final String ATTR_REC_AT_TIME = "recommendationAtTime";
    public static final String ATTR_REC_UNTIL_TIME = "recommendationUntilTime";
    public static final String ATTR_REC_DISABLE_CACHE = "recommendationDisableCache";
    public static final String ATTR_REC_FOR_ITEMS = "recommendationForItems";
    public static final String ATTR_REC_QUERY_NAME = "recommendationQueryName";
    public static final String ATTR_REC_MIN_QUANTITY = "recommendationMinQuantity";
    public static final String ATTR_REC_NUM_RESULTS = "numRecommendations";
    public static final String ATTR_REC_ADDITIONAL_PARAMETERS = "recommendationAdditionalParameters";

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
    private int numberOfRecommendations = -1;

    /**
     * contains the category for the recommendation
     */
    private List<String> categories = null;

    /**
     * contains the category for the recommendation
     */
    private List<String> categoriesBlacklist = null;

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
    private List<String> itemToItemRecs = null;

    /**
     * Used to keep track of different locations for requests of recommendations
     */
    private String recommendationQueryName = null;

    /**
     * Minimum number of items in stock for a returned recommendation
     */
    private Double minQuantity = null;

    /**
     * Additional parameters to be passed into the sub recommenders
     */
    private Map<String, Object> recommendationAdditionalParameters = null;

    /**
     * A base map for the recommendation-request (more specific values will override these parameters)
     */
    private Map<String, Object> recommendationRequest = null;

    public Map<String, Object> getRecommendationRequest() {
        return recommendationRequest;
    }

    public BreinRecommendation setRecommendationRequest(final Map<String, Object> recommendationRequest) {
        this.recommendationRequest = recommendationRequest;
        return this;
    }

    public BreinRecommendation addRecommendationRequest(final String name, final Object value) {
        if (this.recommendationRequest == null) {
            this.recommendationRequest = new HashMap<>();
        }
        this.recommendationRequest.put(name, value);
        return this;
    }

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
        return this.recommendationAdditionalParameters;
    }

    public BreinRecommendation setRecommendationAdditionalParameters(final Map<String, Object> params) {
        this.recommendationAdditionalParameters = params;
        return this;
    }

    @Override
    public String getEndPoint(final BreinConfig config) {
        return config.getRecommendationEndpoint();
    }

    @Override
    public void prepareRequestData(final BreinConfig config, final Map<String, Object> requestData) {

        // recommendation data
        final Map<String, Object> recommendation = new HashMap<>();
        if (this.recommendationRequest != null) {
            recommendation.putAll(this.recommendationRequest);
        }

        // check optional field
        if (BreinUtil.containsValue(getCategories())) {
            recommendation.put(ATTR_REC_CATEGORIES, getCategories());
        }

        if (BreinUtil.containsValue(getCategories())) {
            recommendation.put(ATTR_REC_CATEGORIES_BLACKLISTED, getCategoriesBlacklist());
        }

        if (BreinUtil.containsValue(getSubRecommenders())) {
            recommendation.put(ATTR_REC_SUB_RECOMMENDERS, getSubRecommenders());
        }

        if (BreinUtil.containsValue(getSubInhibitors())) {
            recommendation.put(ATTR_REC_SUB_INHIBITORS, getSubInhibitors());
        }

        if (BreinUtil.containsValue(getBlockers())) {
            recommendation.put(ATTR_REC_SUB_BLOCKERS, getBlockers());
        }

        if (getRecStartTime() >= 0) {
            recommendation.put(ATTR_REC_AT_TIME, getRecStartTime());
        }

        if (getRecEndTime() >= 0) {
            recommendation.put(ATTR_REC_UNTIL_TIME, getRecEndTime());
        }

        if (isDisableCaching()) {
            recommendation.put(ATTR_REC_DISABLE_CACHE, true);
        }

        if (getItemToItemRecs() != null && !getItemToItemRecs().isEmpty()) {
            recommendation.put(ATTR_REC_FOR_ITEMS, getItemToItemRecs());
        }

        if (getRecommendationQueryName() != null) {
            recommendation.put(ATTR_REC_QUERY_NAME, getRecommendationQueryName());
        }

        if (getMinQuantity() != null) {
            recommendation.put(ATTR_REC_MIN_QUANTITY, getMinQuantity());
        }

        // mandatory field
        if (getNumberOfRecommendations() > -1) {
            recommendation.put(ATTR_REC_NUM_RESULTS, getNumberOfRecommendations());
        } else if (!recommendation.containsKey(ATTR_REC_NUM_RESULTS)) {
            recommendation.put(ATTR_REC_NUM_RESULTS, DEF_NUM_RESULTS);
        }

        final Object objAdditional = recommendation.get(ATTR_REC_ADDITIONAL_PARAMETERS);
        final Map<String, Object> additional = Map.class.isInstance(objAdditional) ?
                Map.class.cast(objAdditional) : new HashMap<>();

        // next handle the additional
        if (BreinUtil.containsValue(getRecommendationAdditionalParameters())) {
            additional.putAll(getRecommendationAdditionalParameters());
        }
        recommendation.put(ATTR_REC_ADDITIONAL_PARAMETERS, getRecommendationAdditionalParameters());

        requestData.put("recommendation", recommendation);
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
