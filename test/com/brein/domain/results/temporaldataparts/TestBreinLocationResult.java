package com.brein.domain.results.temporaldataparts;

import com.brein.domain.BreinResult;
import com.brein.domain.results.BreinTemporalDataResult;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TestBreinLocationResult {
    @Test
    public void testNull() {
        final BreinLocationResult res = new BreinLocationResult(null);
        Assert.assertNull(res.getCountry());
        Assert.assertNull(res.getState());
        Assert.assertNull(res.getCity());
        Assert.assertNull(res.getGranularity());

        Assert.assertEquals(0.0, res.getLat(), 0.0001);
        Assert.assertEquals(0.0, res.getLon(), 0.0001);
    }

    @Test
    public void testFull() {
        final String strJson = "{\"location\": {\n" +
                "    \"country\": \"US\",\n" +
                "    \"city\": \"Houston\",\n" +
                "    \"granularity\": \"city\",\n" +
                "    \"lon\": -95.3632715,\n" +
                "    \"state\": \"TX\",\n" +
                "    \"lat\": 29.7632836\n" +
                "  }}";

        @SuppressWarnings("unchecked")
        final Map<String, Object> json = new Gson().fromJson(strJson, Map.class);
        final BreinResult result = new BreinResult(json);

        final BreinLocationResult res = new BreinTemporalDataResult(result).getLocation();

        Assert.assertEquals("US", res.getCountry());
        Assert.assertEquals("TX", res.getState());
        Assert.assertEquals("Houston", res.getCity());
        Assert.assertEquals("city", res.getGranularity());
        Assert.assertEquals(-95.363, res.getLon(), 0.01);
        Assert.assertEquals(29.763, res.getLat(), 0.01);
    }
}