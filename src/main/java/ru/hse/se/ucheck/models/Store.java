package ru.hse.se.ucheck.models;

import java.util.Objects;

public class Store {

    private String address;
    private String outlet;
    private Coordinates storeCoordinates;

    public Store(String address, String outlet, Coordinates storeCoordinates) {
        this.address = address;
        this.outlet = outlet;
        this.storeCoordinates = storeCoordinates;
    }

    public Coordinates getStoreCoordinates() {
        return storeCoordinates;
    }

    public void setStoreCoordinates(Coordinates storeCoordinates) {
        this.storeCoordinates = storeCoordinates;
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
        return Objects.equals(address, store.address) && Objects.equals(outlet, store.outlet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, outlet);
    }
}
