package ru.hse.se.ucheck.models.base;

import ru.hse.se.ucheck.models.base.item.Item;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class Check {

    // duplicates removed
    private List<Item> items;
    private ZonedDateTime timestamp;
    private Store store;

    public Check(List<Item> items, ZonedDateTime timestamp, Store store) {
        this.items = items;
        this.timestamp = timestamp;
        this.store = store;
    }

    public Optional<Item> getItemByCode(int itemCode) {
        return items.stream().filter(item -> item.getCode() == itemCode).findAny();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
