package ru.hse.se.ucheck.models.base;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Store {

    private String address;
    private String outlet;
    private Coordinates coordinates;

    @JsonCreator
    public Store(
            @JsonProperty("address") String address,
            @JsonProperty("outlet") String outlet,
            @JsonProperty("coordinates") Coordinates coordinates
    ) {
        this.address = address;
        this.outlet = outlet;
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Store)) {
            return false;
        }
        Store store = (Store) o;
        return Objects.equals(address, store.address)
                && Objects.equals(outlet, store.outlet)
                && Objects.equals(coordinates, store.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, outlet, coordinates);
    }
}
