package ru.hse.se.ucheck.models.rating;

public enum Review {
    NEGATIVE(-1),
    OK(0),
    SUPER(+1);

    private final int value;

    public int getValue() {
        return value;
    }

    Review(int value) {
        this.value = value;
    }
}
