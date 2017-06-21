package com.brein.domain.results.temporaldataparts;

import com.brein.domain.BreinResult;
import com.brein.domain.results.BreinTemporalDataResult;
import com.brein.domain.results.temporaldataparts.BreinHolidayResult.HolidaySource;
import com.brein.domain.results.temporaldataparts.BreinHolidayResult.HolidayType;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TestBreinHolidayResult {

    @Test
    public void testHolidays() {
        final String strJson = "{" +
                " \"holidays\": [" +
                "{\n" +
                "      \"types\": [\n" +
                "        \"UN_SPECIAL_DAY\"\n" +
                "      ],\n" +
                "      \"source\": \"United Nations\",\n" +
                "      \"holiday\": \"World Cities Day\"\n" +
                "    },{" +
                "  \"types\": [" +
                "   \"MAJOR\"," +
                "   \"HALLMARK\"" +
                "  ]," +
                "  \"source\": \"Government\"," +
                "  \"holiday\": \"Halloween\"" +
                " }, {}, {" +
                "  \"types\": [" +
                "   \"MAJOR\"," +
                "   \"RELIGIOUS\"," +
                "   \"HALLMARK\"" +
                "  ]," +
                "  \"source\": \"Public Information\"," +
                "  \"holiday\": \"Christmas\"" +
                " }]" +
                "}";

        @SuppressWarnings("unchecked")
        final Map<String, Object> json = new Gson().fromJson(strJson, Map.class);
        final BreinResult result = new BreinResult(json);

        final List<BreinHolidayResult> results = new BreinTemporalDataResult(result).getHolidays();
        Assert.assertEquals(results.size(), 4);

        final BreinHolidayResult small = results.get(0);
        Assert.assertEquals("World Cities Day", small.getName());
        Assert.assertEquals(HolidaySource.UNITED_NATIONS, small.getSource());
        Assert.assertEquals(1, small.getTypes().size());
        Assert.assertEquals(HolidayType.UN_SPECIAL_DAY, small.getTypes().get(0));
        Assert.assertFalse(small.isMajor());

        final BreinHolidayResult halloween = results.get(1);
        Assert.assertEquals("Halloween", halloween.getName());
        Assert.assertEquals(HolidaySource.GOVERNMENT, halloween.getSource());
        Assert.assertEquals(2, halloween.getTypes().size());
        Assert.assertEquals(HolidayType.MAJOR, halloween.getTypes().get(0));
        Assert.assertEquals(HolidayType.HALLMARK, halloween.getTypes().get(1));
        Assert.assertTrue(halloween.isMajor());

        final BreinHolidayResult empty = results.get(2);
        Assert.assertNull(empty.getName());
        Assert.assertEquals(HolidaySource.UNKNOWN, empty.getSource());
        Assert.assertEquals(0, empty.getTypes().size());
        Assert.assertFalse(empty.isMajor());

        final BreinHolidayResult christmas = results.get(3);
        Assert.assertEquals("Christmas", christmas.getName());
        Assert.assertEquals(HolidaySource.PUBLIC_INFORMATION, christmas.getSource());
        Assert.assertEquals(3, christmas.getTypes().size());
        Assert.assertTrue(new HashSet<>(christmas.getTypes()).contains(HolidayType.MAJOR));
        Assert.assertTrue(new HashSet<>(christmas.getTypes()).contains(HolidayType.RELIGIOUS));
        Assert.assertTrue(new HashSet<>(christmas.getTypes()).contains(HolidayType.HALLMARK));
        Assert.assertTrue(christmas.isMajor());
    }
}
