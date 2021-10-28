package ru.hse.se.ucheck.check;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class Check {

    private List<Item> items;
    private ZonedDateTime timestamp;
    private int storeID;

    public Check(List<Item> items, ZonedDateTime timestamp, int storeID) {
        this.items = items;
        this.timestamp = timestamp;
        this.storeID = storeID;
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

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }
}
