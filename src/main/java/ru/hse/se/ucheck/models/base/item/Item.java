package ru.hse.se.ucheck.models.base.item;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Item {

    private int code;
    private String label;
    private double price;
    private Measure measure;

    @JsonCreator
    public Item(
            @JsonProperty("code") int code,
            @JsonProperty("label") String label,
            @JsonProperty("price") double price,
            @JsonProperty("measure") Measure measure
    ) {
        this.code = code;
        this.label = label;
        this.price = price;
        this.measure = measure;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }
}
