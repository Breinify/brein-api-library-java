package com.brein.domain.results.temporaldataparts;

import com.brein.domain.BreinResult;
import com.brein.domain.results.BreinTemporalDataResult;
import com.brein.domain.results.CommonResultConstants;
import org.junit.Assert;
import org.junit.Test;

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
        final String json = "{\"location\": {\n" +
                "    \"country\": \"US\",\n" +
                "    \"city\": \"Houston\",\n" +
                "    \"granularity\": \"city\",\n" +
                "    \"lon\": -95.3632715,\n" +
                "    \"state\": \"TX\",\n" +
                "    \"lat\": 29.7632836\n" +
                "  }}";

        final BreinLocationResult res = new BreinTemporalDataResult(new BreinResult(json)).getLocation();

        Assert.assertEquals("US", res.getCountry());
        Assert.assertEquals("TX", res.getState());
        Assert.assertEquals("Houston", res.getCity());
        Assert.assertEquals("city", res.getGranularity());
        Assert.assertEquals(-95.363, res.getLon(), 0.01);
        Assert.assertEquals(29.763, res.getLat(), 0.01);
    }
}