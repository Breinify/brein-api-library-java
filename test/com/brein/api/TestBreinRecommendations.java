package com.brein.api;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBreinRecommendations {

    @Test
    public void prepareRequestData() {
        final BreinRecommendations recommendations = new BreinRecommendations()
                .addRecommendation(new BreinRecommendation().setNumberOfRecommendations(3))
                .addRecommendation(new BreinRecommendation().setNumberOfRecommendations(4))
                .addRecommendation(new BreinRecommendation().setItemToItemRecs(Collections.singletonList("a")));

        final Map<String, Object> result = new HashMap<>();

        recommendations.prepareRequestData(null, result);

        //noinspection unchecked
        final List<Map<String, Object>> recs = (List<Map<String, Object>>) result.get("recommendations");

        Assert.assertNotNull(recs);

        Assert.assertEquals(3, recs.size());

        Assert.assertEquals(3, recs.get(0).get("numRecommendations"));
        Assert.assertEquals(4, recs.get(1).get("numRecommendations"));
        Assert.assertEquals(Collections.singletonList("a"), recs.get(2).get("recommendationForItems"));
    }
}