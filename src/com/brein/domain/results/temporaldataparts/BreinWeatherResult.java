package com.brein.domain.results.temporaldataparts;

import com.brein.util.JsonHelpers;

import java.awt.geom.Point2D;
import java.util.Map;

import static com.brein.domain.results.CommonResultConstants.UNKNOWN_DOUBLE;
import static com.brein.domain.results.CommonResultConstants.UNKNOWN_LONG;
import static com.brein.domain.results.CommonResultConstants.UNKNOWN_STRING;

public class BreinWeatherResult {
    private static final String DESCRIPTION_KEY = "description";
    private static final String TEMPERATURE_KEY = "temperature";
    private static final String PRECIPITATION_KEY = "precipitation";
    private static final String PRECIPITATION_TYPE_KEY = PRECIPITATION_KEY + "Type";
    private static final String PRECIPITATION_AMOUNT_KEY = PRECIPITATION_KEY + "Amount";
    private static final String WIND_STRENGTH_KEY = "windStrength";
    private static final String LAST_MEASURED_KEY = "lastMeasured";
    private static final String CLOUD_COVER_KEY = "cloudCover";
    private static final String MEASURED_LOCATION_KEY = "measuredAt";
    private static final String LATITUDE_KEY = "lat";
    private static final String LONGITUDE_KEY = "lon";

    private final String description;
    private final double temperature;
    private final PrecipitationType precipitation;
    private final double precipitationAmount;
    private final double windStrength;
    private final long lastMeasured;
    private final double cloudCover;
    private final double lat;
    private final double lon;

    public BreinWeatherResult(final Map<String, Object> theJson) {
        if (theJson == null) {
            description = UNKNOWN_STRING;
            temperature = UNKNOWN_DOUBLE;
            precipitation = PrecipitationType.UNKNOWN;
            precipitationAmount = UNKNOWN_DOUBLE;
            windStrength = UNKNOWN_DOUBLE;
            lastMeasured = UNKNOWN_LONG;
            cloudCover = UNKNOWN_DOUBLE;
            lat = UNKNOWN_DOUBLE;
            lon = UNKNOWN_DOUBLE;
        } else {
            description = JsonHelpers.getOr(theJson, DESCRIPTION_KEY, UNKNOWN_STRING);
            temperature = JsonHelpers.getOr(theJson, TEMPERATURE_KEY, UNKNOWN_DOUBLE);
            windStrength = JsonHelpers.getOr(theJson, WIND_STRENGTH_KEY, UNKNOWN_DOUBLE);
            lastMeasured = JsonHelpers.getOrLong(theJson, LAST_MEASURED_KEY);
            cloudCover = JsonHelpers.getOr(theJson, CLOUD_COVER_KEY, UNKNOWN_DOUBLE);

            if (theJson.containsKey(MEASURED_LOCATION_KEY)) {
                //noinspection unchecked
                final Map<String, Object> measuredJson = (Map<String, Object>) theJson.get(MEASURED_LOCATION_KEY);
                lat = JsonHelpers.getOr(measuredJson, LATITUDE_KEY, UNKNOWN_DOUBLE);
                lon = JsonHelpers.getOr(measuredJson, LONGITUDE_KEY, UNKNOWN_DOUBLE);
            } else {
                lat = UNKNOWN_DOUBLE;
                lon = UNKNOWN_DOUBLE;
            }

            if (theJson.containsKey(PRECIPITATION_KEY)) {
                //noinspection unchecked
                final Map<String, Object> precipitationJson = (Map<String, Object>) theJson.get(PRECIPITATION_KEY);

                if (precipitationJson.containsKey(PRECIPITATION_TYPE_KEY)) {
                    switch (((String) precipitationJson.get(PRECIPITATION_TYPE_KEY)).toLowerCase()) {
                        case "rain":
                            precipitation = PrecipitationType.RAIN;
                            break;
                        case "snow":
                            precipitation = PrecipitationType.SNOW;
                            break;
                        case "none":
                            precipitation = PrecipitationType.NONE;
                            break;
                        default:
                            precipitation = PrecipitationType.UNKNOWN;
                    }
                } else {
                    precipitation = PrecipitationType.UNKNOWN;
                }

                precipitationAmount = JsonHelpers.getOr(precipitationJson, PRECIPITATION_AMOUNT_KEY, UNKNOWN_DOUBLE);
            } else {
                precipitation = PrecipitationType.UNKNOWN;
                precipitationAmount = UNKNOWN_DOUBLE;
            }
        }
    }

    public String getDescription() {
        return description;
    }

    public double getTemperatureCelsius() {
        return temperature;
    }

    public double getTemperatureFahrenheit() {
        return getTemperatureCelsius() * 9 / 5 + 32;
    }

    public double getTemperatureKelvin() {
        return getTemperatureCelsius() + 273.15;
    }

    public PrecipitationType getPrecipitation() {
        return precipitation;
    }

    public double getPrecipitationAmount() {
        return precipitationAmount;
    }

    public double getWindStrength() {
        return windStrength;
    }

    public long getLastMeasured() {
        return lastMeasured;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public Point2D.Double getMeasuredAt() {
        return new Point2D.Double(lat, lon);
    }

    @Override
    public String toString() {
        return "weather of " + getDescription() + " and a current temperature of " + getTemperatureCelsius() + " with" +
                " precipitation of " + getPrecipitation();
    }
}
