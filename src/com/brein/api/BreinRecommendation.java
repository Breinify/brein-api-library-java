package com.brein.api;

import com.brein.domain.BreinUser;
import com.brein.util.BreinUtil;

import java.util.HashMap;
import java.util.Map;

public class BreinRecommendation extends BreinBase {

    /**
     * contains the number of recommendations - default is 3
     */
    private int numberOfRecommendations = 3;

    /**
     * contains the category for the recommendation
     */
    private String category;

    /**
     * empty ctor
     */
    BreinRecommendation() {
    }

    /**
     * Ctor with brein user
     * @param breinUser contains the brein user
     */
    BreinRecommendation(final BreinUser breinUser) {
        setBreinUser(breinUser);
    }

    /**
     * Ctor with full configuration
     * @param breinUser contains the brein user
     * @param numberOfRecommendations the number of recommendations
     */
    public BreinRecommendation(final BreinUser breinUser, final int numberOfRecommendations) {
        this(breinUser);
        this.numberOfRecommendations = numberOfRecommendations;
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
     * @return self
     */
    public BreinRecommendation setNumberOfRecommendations(final int numberOfRecommendations) {
        this.numberOfRecommendations = numberOfRecommendations;
        return this;
    }

    /**
     * get the recommendation category
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * set the recommendation category
     * @param category contains the category
     * @return self
     */
    public BreinRecommendation setCategory(final String category) {
        this.category = category;
        return this;
    }

    /**
     * retrieves the configured activity endpoint (e.g. \activity)
     *
     * @return endpoint
     */
    @Override
    public String getEndPoint() {
        return getConfig().getRecommendationEndpoint();
    }

    /**
     * prepares the json request
     * @return json string
     */
    @Override
    public String prepareJsonRequest() {

        final Map<String, Object> requestData = new HashMap<>();

        // call base class for base data
        super.prepareJsonRequest();

        // user data level and additional
        final BreinUser breinUser = getBreinUser();
        if (null != breinUser) {
            breinUser.prepareUserRequestData(requestData, breinUser);
        }

        // recommendation data
        final Map<String, Object> recommendationData = new HashMap<>();

        // check optional field(s)
        if (BreinUtil.containsValue(getCategory())) {
            recommendationData.put("recommendationCategory", getCategory());
        }

        // mandatory field
        recommendationData.put("numRecommendations", getNumberOfRecommendations());
        requestData.put("recommendation", recommendationData);

        // base level data...
        prepareBaseRequestData(this, requestData);

        return getGson().toJson(requestData);
    }

    /**
     * Used to create a clone of a recommendation. This is important in order to prevent
     * concurrency issues.
     *
     * @param sourceRecommendation contains the original recommendation object
     * @return the clone of the recommendation object
     */
    public static BreinRecommendation clone(final BreinRecommendation sourceRecommendation) {

        // create a new activity object
        final BreinRecommendation recommendation = new BreinRecommendation();
        recommendation.setNumberOfRecommendations(sourceRecommendation.getNumberOfRecommendations());
        recommendation.setCategory(sourceRecommendation.getCategory());

        recommendation.cloneBase(sourceRecommendation);
        return recommendation;
    }

    /**
     * Generates the signature for the request
     *
     * @return full signature
     */
    @Override
    public String createSignature() {

        final String message = String.format("%d", getUnixTimestamp());
        return BreinUtil.generateSignature(message, getConfig().getSecret());
    }


}
