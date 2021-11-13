package ru.hse.se.ucheck.models.rating;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
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
