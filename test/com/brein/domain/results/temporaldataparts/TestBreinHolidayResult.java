package com.brein.domain.results.temporaldataparts;

import com.brein.domain.BreinResult;
import com.brein.domain.results.BreinTemporalDataResult;
import com.brein.domain.results.temporaldataparts.BreinHolidayResult.HolidaySource;
import com.brein.domain.results.temporaldataparts.BreinHolidayResult.HolidayType;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

public class TestBreinHolidayResult {

    @Test
    public void testHolidays() {
        final String json = "{" +
                " \"holidays\": [{" +
                "  \"types\": [" +
                "   \"NATIONAL_FEDERAL\"" +
                "  ]," +
                "  \"source\": \"Government\"," +
                "  \"holiday\": \"Halloween\"" +
                " }, {}, {" +
                "  \"types\": [" +
                "   \"RELIGIOUS\"," +
                "   \"HALLMARK\"" +
                "  ]," +
                "  \"source\": \"Public Information\"," +
                "  \"holiday\": \"Christmas\"" +
                " }]" +
                "}";

        final List<BreinHolidayResult> results = new BreinTemporalDataResult(new BreinResult(json)).getHolidays();
        Assert.assertEquals(results.size(), 3);

        final BreinHolidayResult halloween = results.get(0);
        Assert.assertEquals("Halloween", halloween.getName());
        Assert.assertEquals(HolidaySource.GOVERNMENT, halloween.getSource());
        Assert.assertEquals(1, halloween.getTypes().size());
        Assert.assertEquals(HolidayType.NATIONAL_FEDERAL, halloween.getTypes().get(0));

        final BreinHolidayResult empty = results.get(1);
        Assert.assertEquals(BreinHolidayResult.UNKNOWN, empty.getName());
        Assert.assertEquals(HolidaySource.UNKNOWN, empty.getSource());
        Assert.assertEquals(0, empty.getTypes().size());

        final BreinHolidayResult multiType = results.get(2);
        Assert.assertEquals("Christmas", multiType.getName());
        Assert.assertEquals(HolidaySource.PUBLIC_INFORMATION, multiType.getSource());
        Assert.assertEquals(2, multiType.getTypes().size());
        Assert.assertTrue(new HashSet<>(multiType.getTypes()).contains(HolidayType.RELIGIOUS));
        Assert.assertTrue(new HashSet<>(multiType.getTypes()).contains(HolidayType.HALLMARK));
    }
}
