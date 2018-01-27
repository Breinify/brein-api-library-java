package com.brein.domain.results;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TestBreinRecommendationResult {
    @Test
    public void testSimpleResult(){
        final String jsonRaw = "{" +
                "  \"result\": [{" +
                "    \"weight\": 0.94," +
                "    \"additionalData\": {" +
                "      \"productName\": \"item one\"" +
                "    }," +
                "    \"dataIdExternal\": \"11111\"" +
                "  }, {" +
                "    \"weight\": 0.933," +
                "    \"additionalData\": {" +
                "      \"productName\": \"item two\"" +
                "    }," +
                "    \"dataIdExternal\": \"22222\"" +
                "  }]" +
                "}";

        //noinspection unchecked
        final BreinRecommendationResult res = new BreinRecommendationResult(new Gson().fromJson(jsonRaw, Map.class));

        Assert.assertEquals(2, res.getRecommendedItems().size());

        Assert.assertEquals(0.94, res.getRecommendedItems().get(0).getResultWeight(), 0.00001);
        Assert.assertEquals("11111", res.getRecommendedItems().get(0).getResultId());
        Assert.assertEquals("item one", res.getRecommendedItems().get(0).getAdditionalData("productName"));
        Assert.assertNull(res.getRecommendedItems().get(0).getAdditionalData("bad key"));

        Assert.assertEquals(0.933, res.getRecommendedItems().get(1).getResultWeight(), 0.00001);
        Assert.assertEquals("22222", res.getRecommendedItems().get(1).getResultId());
        Assert.assertEquals("item two", res.getRecommendedItems().get(1).getAdditionalData("productName"));
        Assert.assertNull(res.getRecommendedItems().get(1).getAdditionalData("bad key"));
    }

}