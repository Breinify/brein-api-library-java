package com.brein.domain.results.temporaldataparts;

import com.brein.util.JsonHelper;

import java.util.Map;

public class BreinWeatherResult {
    private static final String DESCRIPTION_KEY = "description";
    private static final String TEMPERATURE_KEY = "temperatureC";
    private static final String TEMPERATURE_FAHRENHEIT_KEY = "temperatureF";
    private static final String PRECIPITATION_KEY = "precipitation";
    private static final String PRECIPITATION_TYPE_KEY = PRECIPITATION_KEY + "Type";
    private static final String PRECIPITATION_AMOUNT_KEY = PRECIPITATION_KEY + "Amount";
    private static final String WIND_STRENGTH_KEY = "windStrength";
    private static final String LAST_MEASURED_KEY = "lastMeasured";
    private static final String CLOUD_COVER_KEY = "cloudCover";
    private static final String MEASURED_LOCATION_KEY = "measuredAt";
    private static final String LATITUDE_KEY = "lat";
    private static final String LONGITUDE_KEY = "lon";
    private static final String PRESSURE_KEY = "pressure";
    private static final String HUMIDITY_KEY = "humidity";

    private final String description;
    private final Double temperature;
    private final Double temperatureF;
    private final PrecipitationType precipitation;
    private final Double precipitationAmount;
    private final Double windStrength;
    private final Long lastMeasured;
    private final Double cloudCover;
    private final Double lat;
    private final Double lon;
    private final Double pressure;
    private final Double humidity;

    public BreinWeatherResult(final Map<String, Object> result) {
        this.description = JsonHelper.getOr(result, DESCRIPTION_KEY, null);
        this.temperature = JsonHelper.getOr(result, TEMPERATURE_KEY, null);
        this.temperatureF = JsonHelper.getOr(result, TEMPERATURE_FAHRENHEIT_KEY, null);
        this.windStrength = JsonHelper.getOr(result, WIND_STRENGTH_KEY, null);
        this.lastMeasured = JsonHelper.getOrLong(result, LAST_MEASURED_KEY);
        this.cloudCover = JsonHelper.getOr(result, CLOUD_COVER_KEY, null);
        this.pressure = JsonHelper.getOr(result, PRESSURE_KEY, null);
        this.humidity = JsonHelper.getOr(result, HUMIDITY_KEY, null);

        //noinspection unchecked
        final Map<String, Object> measuredJson = JsonHelper.getOr(result, MEASURED_LOCATION_KEY, null);
        if (measuredJson == null) {
            this.lat = null;
            this.lon = null;
        } else {
            this.lat = JsonHelper.getOr(measuredJson, LATITUDE_KEY, null);
            this.lon = JsonHelper.getOr(measuredJson, LONGITUDE_KEY, null);
        }

        final Map<String, Object> preciValue = JsonHelper.getOr(result, PRECIPITATION_KEY, null);
        if (preciValue == null) {
            this.precipitation = PrecipitationType.UNKNOWN;
            this.precipitationAmount = null;
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
                    case "hail":
                        this.precipitation = PrecipitationType.HAIL;
                        break;
                    case "sleet":
                        this.precipitation = PrecipitationType.SLEET;
                        break;
                    case "none":
                        this.precipitation = PrecipitationType.NONE;
                        break;
                    default:
                        this.precipitation = PrecipitationType.UNKNOWN;
                }
            }

            this.precipitationAmount = JsonHelper.getOr(preciValue, PRECIPITATION_AMOUNT_KEY, null);
        }
    }

    /**
     * @return A human readable description of the weather
     */
    public String getDescription() {
        return this.description;
    }

    public Double getTemperatureCelsius() {
        return this.temperature;
    }

    public Double getTemperatureFahrenheit() {
        if (this.temperatureF != null) {
            return this.temperatureF;
        }

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
        return this.precipitation;
    }

    /**
     * @return How many centimeters of precipitation there have been in the last hour
     */
    public Double getPrecipitationAmount() {
        return this.precipitationAmount;
    }

    /**
     * @return The wind speed, in kilometers/hour
     */
    public Double getWindStrength() {
        return this.windStrength;
    }

    /**
     * @return When this weather data was collected
     */
    public Long getLastMeasured() {
        return this.lastMeasured;
    }

    /**
     * @return The percentage of the sky covered in clouds
     */
    public Double getCloudCover() {
        return this.cloudCover;
    }

    /**
     * @return the pressure
     */
    public Double getPressure() {
        return this.pressure;
    }

    /**
     * @return The humidity as percentage (0.0 - 100.0)
     */
    public Double getHumidity() {
        return this.humidity;
    }

    /**
     * @return The approximate location of the weather station the data was collected at
     */
    public GeoCoordinates getMeasuredAt() {
        if (this.lat == null && this.lon == null) {
            return null;
        } else {
            return new GeoCoordinates(this.lat, this.lon);
        }
    }

    @Override
    public String toString() {
        return "weather of " + getDescription() + " and a current temperature of " + getTemperatureCelsius() + " with" +
                " precipitation of " + getPrecipitation();
    }
}
