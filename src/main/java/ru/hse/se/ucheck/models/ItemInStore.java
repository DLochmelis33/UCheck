package ru.hse.se.ucheck.models;

import java.util.Objects;

public class ItemInStore implements Sortable {

    private double price;
    private Store store;
    private double averageStoreRating;

    public ItemInStore(double price, Store store, double averageStoreRating) {
        this.price = price;
        this.store = store;
        this.averageStoreRating = averageStoreRating;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public double getAverageStoreRating() {
        return averageStoreRating;
    }

    public void setAverageStoreRating(double averageStoreRating) {
        this.averageStoreRating = averageStoreRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemInStore)) {
            return false;
        }
        ItemInStore that = (ItemInStore) o;
        return that.price == price
                && that.averageStoreRating == averageStoreRating
                && Objects.equals(store, that.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, averageStoreRating, store);
    }

    @Override
    public String toString() {
        return "ItemInStore {"
                + "price=" + price
                + ", store=" + store
                + ", averageStoreRating=" + averageStoreRating
                + '}';
    }
}
