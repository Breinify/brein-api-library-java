package com.brein.domain.results.temporaldataparts;

import com.brein.util.JsonHelpers;

import java.util.Map;

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
    private final Double temperature;
    private final PrecipitationType precipitation;
    private final Double precipitationAmount;
    private final Double windStrength;
    private final Long lastMeasured;
    private final Double cloudCover;
    private final Double lat;
    private final Double lon;

    public BreinWeatherResult(final Map<String, Object> result) {
        description = JsonHelpers.getOr(result, DESCRIPTION_KEY, null);
        temperature = JsonHelpers.getOr(result, TEMPERATURE_KEY, null);
        windStrength = JsonHelpers.getOr(result, WIND_STRENGTH_KEY, null);
        lastMeasured = JsonHelpers.getOrLong(result, LAST_MEASURED_KEY);
        cloudCover = JsonHelpers.getOr(result, CLOUD_COVER_KEY, null);

        //noinspection unchecked
        final Map<String, Object> measuredJson = JsonHelpers.getOr(result, MEASURED_LOCATION_KEY, null);
        if (measuredJson == null) {
            lat = null;
            lon = null;
        } else {
            lat = JsonHelpers.getOr(measuredJson, LATITUDE_KEY, null);
            lon = JsonHelpers.getOr(measuredJson, LONGITUDE_KEY, null);
        }

        final Map<String, Object> precipitation = JsonHelpers.getOr(result, PRECIPITATION_KEY, null);
        if (precipitation == null) {
            this.precipitation = PrecipitationType.UNKNOWN;
            precipitationAmount = null;
        } else {
            final String type = JsonHelpers.getOr(precipitation, PRECIPITATION_TYPE_KEY, null);
            if (type == null) {
                this.precipitation = PrecipitationType.UNKNOWN;
            } else {
                switch (type.toLowerCase()) {
                    case "rain":
                        this.precipitation = PrecipitationType.RAIN;
                        break;
                    case "snow":
                        this.precipitation = PrecipitationType.SNOW;
                        break;
                    case "none":
                        this.precipitation = PrecipitationType.NONE;
                        break;
                    default:
                        this.precipitation = PrecipitationType.UNKNOWN;
                }
            }

            precipitationAmount = JsonHelpers.getOr(precipitation, PRECIPITATION_AMOUNT_KEY, null);
        }
    }

    public String getDescription() {
        return description;
    }

    public Double getTemperatureCelsius() {
        return temperature;
    }

    public Double getTemperatureFahrenheit() {
        final Double celsius = getTemperatureCelsius();
        if (celsius == null) {
            return null;
        }

        return celsius * 9 / 5 + 32;
    }

    public Double getTemperatureKelvin() {
        final Double celsius = getTemperatureCelsius();
        if (celsius == null) {
            return null;
        }

        return celsius + 273.15;
    }

    public PrecipitationType getPrecipitation() {
        return precipitation;
    }

    public Double getPrecipitationAmount() {
        return precipitationAmount;
    }

    public Double getWindStrength() {
        return windStrength;
    }

    public Long getLastMeasured() {
        return lastMeasured;
    }

    public Double getCloudCover() {
        return cloudCover;
    }

    public GeoCoordinates getMeasuredAt() {
        if (lat == null && lon == null) {
            return null;
        } else {
            return new GeoCoordinates(lat, lon);
        }
    }

    @Override
    public String toString() {
        return "weather of " + getDescription() + " and a current temperature of " + getTemperatureCelsius() + " with" +
                " precipitation of " + getPrecipitation();
    }
}
