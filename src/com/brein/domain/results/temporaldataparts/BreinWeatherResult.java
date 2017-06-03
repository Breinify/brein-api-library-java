package com.brein.domain.results.temporaldataparts;

import com.brein.util.JsonHelper;

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
        description = JsonHelper.getOr(result, DESCRIPTION_KEY, null);
        temperature = JsonHelper.getOr(result, TEMPERATURE_KEY, null);
        windStrength = JsonHelper.getOr(result, WIND_STRENGTH_KEY, null);
        lastMeasured = JsonHelper.getOrLong(result, LAST_MEASURED_KEY);
        cloudCover = JsonHelper.getOr(result, CLOUD_COVER_KEY, null);

        //noinspection unchecked
        final Map<String, Object> measuredJson = JsonHelper.getOr(result, MEASURED_LOCATION_KEY, null);
        if (measuredJson == null) {
            lat = null;
            lon = null;
        } else {
            lat = JsonHelper.getOr(measuredJson, LATITUDE_KEY, null);
            lon = JsonHelper.getOr(measuredJson, LONGITUDE_KEY, null);
        }

        final Map<String, Object> preciValue = JsonHelper.getOr(result, PRECIPITATION_KEY, null);
        if (preciValue == null) {
            this.precipitation = PrecipitationType.UNKNOWN;
            precipitationAmount = null;
        } else {
            final String type = JsonHelper.getOr(preciValue, PRECIPITATION_TYPE_KEY, null);
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

            precipitationAmount = JsonHelper.getOr(preciValue, PRECIPITATION_AMOUNT_KEY, null);
        }
    }

    /**
     * @return A human readable description of the weather
     */
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

    /**
     * @return The type of precipitation, or 'NONE' if there isn't any
     */
    public PrecipitationType getPrecipitation() {
        return precipitation;
    }

    /**
     * @return How many centimeters of precipitation there have been in the last hour
     */
    public Double getPrecipitationAmount() {
        return precipitationAmount;
    }

    /**
     * @return The wind speed, in kilometers/hour
     */
    public Double getWindStrength() {
        return windStrength;
    }

    /**
     * @return When this weather data was collected
     */
    public Long getLastMeasured() {
        return lastMeasured;
    }

    /**
     * @return The percentage of the sky covered in clouds
     */
    public Double getCloudCover() {
        return cloudCover;
    }

    /**
     * @return The approximate location of the weather station the data was collected at
     */
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
