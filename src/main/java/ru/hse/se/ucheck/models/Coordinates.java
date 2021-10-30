package ru.hse.se.ucheck.models;

import java.util.Objects;

public class Coordinates {
    private double latitude;
    private double longitude;

    public Coordinates(double latitude, double longitude) {
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
