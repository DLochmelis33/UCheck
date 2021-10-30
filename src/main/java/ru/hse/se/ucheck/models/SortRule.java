package ru.hse.se.ucheck.models;

import java.util.List;

// sort in descending order
public class SortRule {

    private List<SortParameter> parameters = List.of(SortParameter.PRICE, SortParameter.RATING, SortParameter.OUTLET);

    public SortRule() {

    }

    public SortRule(List<SortParameter> parameters) {
        this.parameters = parameters;
    }
}
