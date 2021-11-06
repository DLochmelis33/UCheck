package ru.hse.se.ucheck.models;

import java.util.Map;

public class CartInStore implements Sortable {

    private final Map<ItemInCart, Double> prices;
    private Store store;
    private double averageStoreRating;

    public CartInStore(Map<ItemInCart, Double> prices, Store store, double averageStoreRating) {
        this.prices = prices;
        this.store = store;
        this.averageStoreRating = averageStoreRating;
    }

    @Override
    public double getPrice() {
        return prices.entrySet().stream()
                .mapToDouble(entry -> entry.getValue() * entry.getKey().getCount())
                .sum();
    }

    public Map<ItemInCart, Double> getPrices() {
        return prices;
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
}
