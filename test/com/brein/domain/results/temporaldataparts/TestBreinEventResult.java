package com.brein.domain.results.temporaldataparts;

import com.brein.domain.results.BreinTemporalDataResult;
import com.brein.domain.results.temporaldataparts.BreinEventResult.EventSize;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class TestBreinEventResult {
    @Test
    public void testEvents() {
        final String strJson = "{\n" +
                " \"events\": [{\n" +
                "      \"displayName\": \"Full Event\",\n" +
                "      \"timezone\": \"America/Los_Angeles\",\n" +
                "      \"genderSplit\": 0.47965145111083984,\n" +
                "      \"ages\": [\n" +
                "        \"young-adults\",\n" +
                "        \"middle-age\"\n" +
                "      ],\n" +
                "      \"location\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"zipCode\": \"94102\",\n" +
                "        \"address\": \"401 Mason Street\",\n" +
                "        \"city\": \"San Francisco\",\n" +
                "        \"granularity\": \"exact\",\n" +
                "        \"lon\": -122.410141,\n" +
                "        \"state\": \"CA\",\n" +
                "        \"lat\": 37.787295\n" +
                "      },\n" +
                "      \"startTime\": 123,\n" +
                "      \"endTime\": 1234,\n" +
                "      \"categories\": [\n" +
                "        \"Concerts\"\n" +
                "      ],\n" +
                "      \"sizeEstimated\": \"major\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": \"Sleeptalkers\",\n" +
                "      \"timezone\": \"America/Los_Angeles\",\n" +
                "      \"genderSplit\": 0.37785571813583374,\n" +
                "      \"ages\": [\n" +
                "        \"young-adults\",\n" +
                "        \"middle-age\"\n" +
                "      ],\n" +
                "      \"location\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"zipCode\": \"94107\",\n" +
                "        \"address\": \"1233 17th St\",\n" +
                "        \"city\": \"San Francisco\",\n" +
                "        \"granularity\": \"exact\",\n" +
                "        \"lon\": -122.3963595,\n" +
                "        \"state\": \"CA\",\n" +
                "        \"lat\": 37.7650548\n" +
                "      },\n" +
                "      \"startTime\": 12345,\n" +
                "      \"endTime\": 12346,\n" +
                "      \"categories\": [\n" +
                "        \"Concerts\", \"Other\"\n" +
                "      ],\n" +
                "      \"sizeEstimated\": \"minor\"\n" +
                "    },{}]\n" +
                "}";

        @SuppressWarnings("unchecked")
        final Map<String, Object> json = new Gson().fromJson(strJson, Map.class);
        final BreinTemporalDataResult result = new BreinTemporalDataResult(json);

        final List<BreinEventResult> results = result.getEvents();
        Assert.assertEquals(results.size(), 3);

        final BreinEventResult full = results.get(0);

        Assert.assertEquals("Full Event", full.getName());
        Assert.assertEquals(123L, full.getStart(), 0.001);
        Assert.assertEquals(1234L, full.getEnd(), 0.001);
        Assert.assertEquals(EventSize.MAJOR, full.getSize());
        Assert.assertEquals("Concerts", full.getCategories().get(0));

        final BreinEventResult badCategory = results.get(1);

        Assert.assertEquals("Sleeptalkers", badCategory.getName());
        Assert.assertEquals(12345, badCategory.getStart(), 0.001);
        Assert.assertEquals(12346, badCategory.getEnd(), 0.001);
        Assert.assertEquals(EventSize.MINOR, badCategory.getSize());
        Assert.assertArrayEquals(new String[]{"Concerts", "Other"}, badCategory.getCategories().toArray());

        final BreinEventResult nullCategory = results.get(2);

        Assert.assertNull(nullCategory.getName());
        Assert.assertNull(nullCategory.getStart());
        Assert.assertNull(nullCategory.getEnd());
        Assert.assertEquals(EventSize.UNKNOWN, nullCategory.getSize());
    }
}
