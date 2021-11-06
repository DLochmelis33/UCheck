package ru.hse.se.ucheck.models;

public class ItemInCart {

    private int itemCode;
    private int count;

    public ItemInCart(int itemCode, int count) {
        this.itemCode = itemCode;
        this.count = count;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
