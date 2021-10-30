package ru.hse.se.ucheck.models;

public class ItemInStore {

    private double price;
    private Store store;
    private double averageStoreRating;

    public ItemInStore(double price, Store store, double averageStoreRating) {
        this.price = price;
        this.store = store;
        this.averageStoreRating = averageStoreRating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public double getAverageStoreRating() {
        return averageStoreRating;
    }

    public void setAverageStoreRating(double averageStoreRating) {
        this.averageStoreRating = averageStoreRating;
    }
}
