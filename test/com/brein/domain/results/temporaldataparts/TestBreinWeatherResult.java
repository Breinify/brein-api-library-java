package com.brein.domain.results.temporaldataparts;

import com.brein.domain.BreinResult;
import com.brein.domain.results.BreinTemporalDataResult;
import com.brein.domain.results.CommonResultConstants;
import org.junit.Assert;
import org.junit.Test;

public class TestBreinWeatherResult {
    @Test
    public void testNull() {
        final BreinWeatherResult res = new BreinWeatherResult(null);

        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getCloudCover(), 0.001);
        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getTemperatureCelsius(), 0.001);
        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getTemperatureFahrenheit(), 0.001);
        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getTemperatureKelvin(), 0.001);
        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getWindStrength(), 0.001);
        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getPrecipitationAmount(), 0.001);

        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getMeasuredAt().getX(), 0.001);
        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getMeasuredAt().getY(), 0.001);

        Assert.assertEquals(CommonResultConstants.UNKNOWN_STRING, res.getDescription());
        Assert.assertEquals(CommonResultConstants.UNKNOWN_LONG, res.getLastMeasured(), 0.001);
        Assert.assertEquals(PrecipitationType.UNKNOWN, res.getPrecipitation());
    }

    @Test
    public void testFull() {
        final String json = "{" +
                "  \"weather\": {" +
                "    \"precipitation\": {" +
                "      \"precipitationType\": \"none\"," +
                "      \"precipitationAmount\": 0" +
                "    }," +
                "    \"windStrength\": 1.5," +
                "    \"lastMeasured\": 1490740500," +
                "    \"temperature\": 22.5," +
                "    \"cloudCover\": 40," +
                "    \"description\": \"scattered clouds\"," +
                "    \"measuredAt\": {" +
                "      \"lon\": -122.4195," +
                "      \"lat\": 37.7749" +
                "    }" +
                "  }}";

        final BreinWeatherResult res = new BreinTemporalDataResult(new BreinResult(json)).getWeather();

        Assert.assertEquals(40.0, res.getCloudCover(), 0.001);
        Assert.assertEquals(22.5, res.getTemperatureCelsius(), 0.001);
        Assert.assertEquals(72.5, res.getTemperatureFahrenheit(), 0.001);
        Assert.assertEquals(295.65, res.getTemperatureKelvin(), 0.001);
        Assert.assertEquals(1.5, res.getWindStrength(), 0.001);
        Assert.assertEquals(0, res.getPrecipitationAmount(), 0.001);

        Assert.assertEquals(-122.4195, res.getMeasuredAt().getY(), 0.001);
        Assert.assertEquals(37.7749, res.getMeasuredAt().getX(), 0.001);

        Assert.assertEquals("scattered clouds", res.getDescription());
        Assert.assertEquals(1490740500, res.getLastMeasured(), 0.001);
        Assert.assertEquals(PrecipitationType.NONE, res.getPrecipitation());
    }

    @Test
    public void testPartial() {
        final String json = "{" +
                "  \"weather\": {" +
                "    \"precipitation\": {" +
                "      \"precipitationAmount\": 2.5" +
                "    }," +
                "    \"windStrength\": 1.5," +
                "    \"lastMeasured\": 1490740500," +
                "    \"cloudCover\": 40," +
                "    \"description\": \"scattered clouds\"," +
                "    \"measuredAt\": {" +
                "    }" +
                "  }}";

        final BreinWeatherResult res = new BreinTemporalDataResult(new BreinResult(json)).getWeather();

        Assert.assertEquals(40.0, res.getCloudCover(), 0.001);
        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getTemperatureCelsius(), 0.001);
        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getTemperatureFahrenheit(), 0.001);
        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getTemperatureKelvin(), 0.001);
        Assert.assertEquals(1.5, res.getWindStrength(), 0.001);
        Assert.assertEquals(2.5, res.getPrecipitationAmount(), 0.001);

        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getMeasuredAt().getY(), 0.001);
        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getMeasuredAt().getX(), 0.001);

        Assert.assertEquals("scattered clouds", res.getDescription());
        Assert.assertEquals(1490740500, res.getLastMeasured(), 0.001);
        Assert.assertEquals(PrecipitationType.UNKNOWN, res.getPrecipitation());
    }

    @Test
    public void testPrecip() {
        for (final boolean allcaps : new boolean[]{true, false}) {
            for (final PrecipitationType type : PrecipitationType.values()) {
                final String typeString;
                switch (type) {
                    case NONE:
                        typeString = "none";
                        break;
                    case RAIN:
                        typeString = "rain";
                        break;
                    case SNOW:
                        typeString = "snow";
                        break;
                    case UNKNOWN:
                        typeString = "unknown";
                        break;
                    default:
                        typeString = null;
                        Assert.fail("Unknown precipitation type " + type);
                }
                final String json = "{" +
                        "  \"weather\": {" +
                        "    \"precipitation\": {" +
                        "      \"precipitationType\": \"" + (allcaps ? typeString.toUpperCase() : typeString) + "\"" +
                        "    }" +
                        "  }}";

                final BreinWeatherResult res = new BreinTemporalDataResult(new BreinResult(json)).getWeather();

                Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getCloudCover(), 0.001);
                Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getTemperatureCelsius(), 0.001);
                Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getTemperatureFahrenheit(), 0.001);
                Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getTemperatureKelvin(), 0.001);
                Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getWindStrength(), 0.001);
                Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getPrecipitationAmount(), 0.001);

                Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getMeasuredAt().getX(), 0.001);
                Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getMeasuredAt().getY(), 0.001);

                Assert.assertEquals(CommonResultConstants.UNKNOWN_STRING, res.getDescription());
                Assert.assertEquals(CommonResultConstants.UNKNOWN_LONG, res.getLastMeasured(), 0.001);

                Assert.assertEquals(type, res.getPrecipitation());
            }
        }
    }

    @Test
    public void testMissingPrecipitation() {
        final String json = "{" +
                "  \"weather\": {" +
                "    \"windStrength\": 1.5," +
                "    \"lastMeasured\": 1490740500," +
                "    \"temperature\": 22.5," +
                "    \"cloudCover\": 40," +
                "    \"description\": \"scattered clouds\"," +
                "    \"measuredAt\": {" +
                "      \"lon\": -122.4195," +
                "      \"lat\": 37.7749" +
                "    }" +
                "  }}";

        final BreinWeatherResult res = new BreinTemporalDataResult(new BreinResult(json)).getWeather();

        Assert.assertEquals(40.0, res.getCloudCover(), 0.001);
        Assert.assertEquals(22.5, res.getTemperatureCelsius(), 0.001);
        Assert.assertEquals(72.5, res.getTemperatureFahrenheit(), 0.001);
        Assert.assertEquals(295.65, res.getTemperatureKelvin(), 0.001);
        Assert.assertEquals(1.5, res.getWindStrength(), 0.001);
        Assert.assertEquals(CommonResultConstants.UNKNOWN_DOUBLE, res.getPrecipitationAmount(), 0.001);

        Assert.assertEquals(-122.4195, res.getMeasuredAt().getY(), 0.001);
        Assert.assertEquals(37.7749, res.getMeasuredAt().getX(), 0.001);

        Assert.assertEquals("scattered clouds", res.getDescription());
        Assert.assertEquals(1490740500, res.getLastMeasured(), 0.001);
        Assert.assertEquals(PrecipitationType.UNKNOWN, res.getPrecipitation());
    }
}