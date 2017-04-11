package com.brein.domain.results;

import com.brein.domain.BreinResult;
import com.brein.domain.results.temporaldataparts.BreinHolidayResult;
import com.brein.domain.results.temporaldataparts.BreinHolidayResult.HolidaySource;
import com.brein.domain.results.temporaldataparts.BreinHolidayResult.HolidayType;
import com.brein.domain.results.temporaldataparts.PrecipitationType;
import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class TestBreinTemporalDataResult {
    @Test
    public void testWeather() {
        //just a stub json, further weather testing is done in `TestBreinWeatherResult`
        final String json = "{" +
                "  \"weather\": {" +
                "    \"precipitation\": {" +
                "      \"precipitationType\": \"rain\"," +
                "      \"precipitationAmount\": 1234" +
                "    }" +
                "  }}";

        Assert.assertFalse(new BreinTemporalDataResult(Collections.emptyMap()).hasWeather());

        final BreinTemporalDataResult withWeather = new BreinTemporalDataResult(new BreinResult(json));
        Assert.assertTrue(withWeather.hasWeather());
        Assert.assertEquals(PrecipitationType.RAIN, withWeather.getWeather().getPrecipitation());
        Assert.assertEquals(1234.0, withWeather.getWeather().getPrecipitationAmount(), 0.001);
    }

    @Test
    public void testLocalTime() {
        final String json = "{ \"time\": {\"localFormatIso8601\": \"2017-04-07T12:02:46-05:00\"}}";

        Assert.assertFalse(new BreinTemporalDataResult(Collections.emptyMap()).hasLocalDateTime());

        final BreinTemporalDataResult withTime = new BreinTemporalDataResult(new BreinResult(json));
        Assert.assertTrue(withTime.hasLocalDateTime());
        final LocalDateTime time = withTime.getLocalDateTime();

        Assert.assertEquals(2017, time.getYear());
        Assert.assertEquals(4, time.getMonthValue());
        Assert.assertEquals(7, time.getDayOfMonth());
        Assert.assertEquals(DayOfWeek.FRIDAY, time.getDayOfWeek());

        Assert.assertEquals(12, time.getHour());
        Assert.assertEquals(2, time.getMinute());
        Assert.assertEquals(46, time.getSecond());
    }

    @Test
    public void testEpochTime() {
        final String json = "{ \"time\": {\"epochFormatIso8601\": \"2017-04-07T17:02:46+00:00\"}}";

        Assert.assertFalse(new BreinTemporalDataResult(Collections.emptyMap()).hasEpochDateTime());

        final BreinTemporalDataResult withTime = new BreinTemporalDataResult(new BreinResult(json));
        Assert.assertTrue(withTime.hasEpochDateTime());
        final LocalDateTime time = withTime.getEpochDateTime();

        Assert.assertEquals(2017, time.getYear());
        Assert.assertEquals(4, time.getMonthValue());
        Assert.assertEquals(7, time.getDayOfMonth());
        Assert.assertEquals(DayOfWeek.FRIDAY, time.getDayOfWeek());

        Assert.assertEquals(17, time.getHour());
        Assert.assertEquals(2, time.getMinute());
        Assert.assertEquals(46, time.getSecond());
    }

    @Test
    public void testHoliday() {
        final String json = "{ \"holidays\": [{\"types\": [\"HALLMARK\"], \"source\":\"UNITED_NATIONS\"," +
                " \"holiday\": \"aaa\"}]}";

        Assert.assertFalse(new BreinTemporalDataResult(Collections.emptyMap()).hasHolidays());

        final BreinTemporalDataResult withHoliday = new BreinTemporalDataResult(new BreinResult(json));
        Assert.assertTrue(withHoliday.hasHolidays());
        final List<BreinHolidayResult> holidays = withHoliday.getHolidays();

        Assert.assertEquals(1, holidays.size());
        Assert.assertEquals(1, holidays.get(0).getTypes().size());
        Assert.assertEquals(HolidayType.HALLMARK, holidays.get(0).getTypes().get(0));
        Assert.assertEquals(HolidaySource.UNITED_NATIONS, holidays.get(0).getSource());
        Assert.assertEquals("aaa", holidays.get(0).getName());
    }
}