package ru.hse.se.ucheck.models.base;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Coordinates {
    private double latitude; // in degrees
    private double longitude; // in degrees

    public Coordinates(
            @JsonProperty("latitude") double latitude,
            @JsonProperty("longitude") double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance(Coordinates other) { // in km
        int earthRadius = 6371; // in km
        double deltaLatitude = Math.toRadians(latitude - other.getLatitude()); // in rad
        double deltaLongitude = Math.toRadians(longitude - other.getLongitude()); // in rad
        double underSquareExpression = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2)
                + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(other.latitude))
                * Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);
        return earthRadius * 2 * Math.asin(Math.sqrt(underSquareExpression));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coordinates)) {
            return false;
        }
        Coordinates that = (Coordinates) o;
        return that.latitude == latitude && that.longitude == longitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
