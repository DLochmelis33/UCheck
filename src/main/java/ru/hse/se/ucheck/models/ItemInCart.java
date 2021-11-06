package ru.hse.se.ucheck.models;

import java.util.Objects;

public class ItemInCart {

    private int itemCode;
    private int count;

    public ItemInCart(int itemCode, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("count should not be <= 0");
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemInCart)) {
            return false;
        }
        ItemInCart that = (ItemInCart) o;
        return itemCode == that.itemCode && count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, count);
    }
}
