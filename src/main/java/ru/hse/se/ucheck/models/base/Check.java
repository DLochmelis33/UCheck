package ru.hse.se.ucheck.models.base;

import com.fasterxml.jackson.annotation.*;
import ru.hse.se.ucheck.models.base.item.Item;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Check {

    // duplicates removed
    private List<Item> items;
    private ZonedDateTime timestamp;
    private Store store;

    public Check(
            List<Item> items,
            ZonedDateTime timestamp,
            Store store
    ) {
        this.items = items;
        this.timestamp = timestamp;
        this.store = store;
    }

    @JsonCreator
    public Check(
            @JsonProperty("items") List<Item> items,
            @JsonProperty("timestampRepresentation") String timestampRepresentation,
            @JsonProperty("store") Store store
    ) {
        this(items, ZonedDateTime.parse(timestampRepresentation), store);
    }

    @JsonIgnore
    public Optional<Item> getItemByCode(int itemCode) {
        return items.stream().filter(item -> item.getCode() == itemCode).findAny();
    }

    @JsonIgnore
    public List<Item> getItems() {
        return items;
    }

    @JsonIgnore
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @JsonIgnore
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @JsonIgnore
    public Store getStore() {
        return store;
    }

    @JsonIgnore
    public void setStore(Store store) {
        this.store = store;
    }
}
