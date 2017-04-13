package com.brein.domain.results.temporaldataparts;

public class GeoCoordinates {

    private final Double lat;
    private final Double lon;

    public GeoCoordinates(final Double lat, final Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLatitude() {
        return lat;
    }

    public Double getLongitude() {
        return lon;
    }

    @Override
    public String toString() {
        return lat + " " + lon;
    }
}
