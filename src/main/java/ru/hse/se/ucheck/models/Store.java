package ru.hse.se.ucheck.models;

public class Store {

    private String address;
    private String outlet;

    public Store(String address, String outlet) {
        this.address = address;
        this.outlet = outlet;
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
}