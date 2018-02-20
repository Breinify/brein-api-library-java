package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.util.BreinUtil;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TestBreinActivity {
    @Test
    public void testGettingFields() {
        final String expectedMessage = String.format("%s%d%d", "activityType", 11111, 1);
        final String expectedSignature = BreinUtil.generateSignature(expectedMessage, "secret");

        final BreinActivity activity =  new BreinActivity()
                .setUnixTimestamp(11111)
                .setActivityType("activityType");

        final String json = activity.prepareRequestData(new BreinConfig("apikey", "secret"));

        //noinspection unchecked
        final Map<String, Object> jsonParsed = new Gson().fromJson(json, Map.class);

        Assert.assertEquals(11111, activity.setUnixTimestamp(11111).getUnixTimestamp());
        Assert.assertEquals(11111, activity.getUnixTimestamp());

        Assert.assertEquals(11111.0, (Double) jsonParsed.get("unixTimestamp"), 0.0001);
        Assert.assertEquals("apikey", jsonParsed.get("apiKey"));
        Assert.assertEquals(expectedSignature, jsonParsed.get("signature"));
    }

}