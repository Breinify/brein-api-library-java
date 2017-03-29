package com.brein.domain.results;

import com.brein.domain.BreinResult;
import com.brein.domain.results.temporaldataparts.PrecipitationType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

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
}