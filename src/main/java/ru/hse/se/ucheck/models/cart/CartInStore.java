package ru.hse.se.ucheck.models.cart;

import ru.hse.se.ucheck.models.base.Store;
import ru.hse.se.ucheck.models.sort.Sortable;

import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartInStore)) {
            return false;
        }
        CartInStore that = (CartInStore) o;
        return averageStoreRating == that.averageStoreRating
                && Objects.equals(store, that.store)
                && Objects.equals(prices, that.prices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(averageStoreRating, store, prices);
    }

    @Override
    public String toString() {
        return "ItemInStore {"
                + "prices=" + prices
                + ", store=" + store
                + ", averageStoreRating=" + averageStoreRating
                + '}';
    }
}
