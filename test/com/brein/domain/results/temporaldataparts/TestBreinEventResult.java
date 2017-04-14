package com.brein.domain.results.temporaldataparts;

import com.brein.domain.results.BreinTemporalDataResult;
import com.brein.domain.results.temporaldataparts.BreinEventResult.EventCategory;
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
                "   \"displayName\": \"Full Event\",\n" +
                "   \"startTime\": 123,\n" +
                "   \"location\": {\n" +
                "    \"country\": \"US\",\n" +
                "    \"zipCode\": \"94109\",\n" +
                "    \"city\": \"San Francisco\",\n" +
                "    \"granularity\": \"city\",\n" +
                "    \"state\": \"CA\"\n" +
                "   },\n" +
                "   \"endTime\": 1234,\n" +
                "   \"sizeEstimated\": 3,\n" +
                "   \"category\": \"eventCategoryConcert\"\n" +
                "  },\n" +
                "  {\n" +
                "   \"displayName\": \"Bad category\",\n" +
                "   \"startTime\": 123,\n" +
                "   \"location\": {\n" +
                "    \"country\": \"US\",\n" +
                "    \"zipCode\": \"94109\",\n" +
                "    \"city\": \"San Francisco\",\n" +
                "    \"granularity\": \"city\",\n" +
                "    \"state\": \"CA\"\n" +
                "   },\n" +
                "   \"endTime\": 1234,\n" +
                "   \"sizeEstimated\": 3,\n" +
                "   \"category\": \"badCategory\"\n" +
                "  },\n" +
                "  {\n" +
                "   \"displayName\": \"Partial Event\",\n" +
                "   \"startTime\": 234,\n" +
                "   \"location\": {\n" +
                "    \"country\": \"US\",\n" +
                "    \"city\": \"San Francisco\",\n" +
                "    \"granularity\": \"city\",\n" +
                "    \"state\": \"CA\"\n" +
                "   },\n" +
                "   \"endTime\": 2345,\n" +
                "   \"sizeEstimated\": -1\n" +
                "  },\n" +
                "  {}\n" +
                " ]\n" +
                "}";

        @SuppressWarnings("unchecked")
        final Map<String, Object> json = new Gson().fromJson(strJson, Map.class);
        final BreinTemporalDataResult result = new BreinTemporalDataResult(json);

        final List<BreinEventResult> results = result.getEvents();
        Assert.assertEquals(results.size(), 4);

        final BreinEventResult full = results.get(0);

        Assert.assertEquals("Full Event", full.getName());
        Assert.assertEquals(123L, full.getStart(), 0.001);
        Assert.assertEquals(1234L, full.getEnd(), 0.001);
        Assert.assertEquals(3L, full.getSize(), 0.001);
        Assert.assertEquals(EventCategory.CONCERT, full.getCategory());

        final BreinEventResult badCategory = results.get(1);

        Assert.assertEquals("Bad category", badCategory.getName());
        Assert.assertEquals(123, badCategory.getStart(), 0.001);
        Assert.assertEquals(1234, badCategory.getEnd(), 0.001);
        Assert.assertEquals(3, badCategory.getSize(), 0.001);
        Assert.assertEquals(EventCategory.UNKNOWN, badCategory.getCategory());

        final BreinEventResult partial = results.get(2);

        Assert.assertEquals("Partial Event", partial.getName());
        Assert.assertEquals(234, partial.getStart(), 0.001);
        Assert.assertEquals(2345, partial.getEnd(), 0.001);
        Assert.assertNull(partial.getSize());
        Assert.assertEquals(EventCategory.UNKNOWN, partial.getCategory());

        final BreinEventResult nullCategory = results.get(3);

        Assert.assertNull(nullCategory.getName());
        Assert.assertNull(nullCategory.getStart());
        Assert.assertNull(nullCategory.getEnd());
        Assert.assertNull(nullCategory.getSize());
        Assert.assertEquals(EventCategory.UNKNOWN, nullCategory.getCategory());
    }
}
