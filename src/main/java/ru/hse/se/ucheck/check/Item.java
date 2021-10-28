package ru.hse.se.ucheck.check;

public class Item {

    private int code;
    private String label;
    private double price;
    private Measure measure;

    public Item(int code, String label, double price, Measure measure) {
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
