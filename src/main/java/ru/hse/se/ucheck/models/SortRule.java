package ru.hse.se.ucheck.models;

import java.util.Comparator;
import java.util.List;

// sort in descending or alphabetical order
public class SortRule {

    private List<SortParameter> parameters = List.of(SortParameter.PRICE, SortParameter.RATING, SortParameter.OUTLET);

    public SortRule() {
    }

    public SortRule(List<SortParameter> parameters) {
        this.parameters = parameters;
    }

    public Comparator<ItemInStore> getItemInStoreComparator() {
        Comparator<ItemInStore> comparator = (item1, item2) -> 0;
        for (SortParameter parameter : parameters) {
            switch(parameter) {
                case PRICE:
                    comparator = comparator.thenComparing(ItemInStore::getPrice);
                    break;
                case RATING:
                    comparator = comparator.thenComparing(
                            Comparator.comparing(ItemInStore::getAverageStoreRating).reversed());
                    break;
                case OUTLET:
                    comparator = comparator.thenComparing(item -> item.getStore().getOutlet());
                    break;
            }
        }
        return comparator;
    }
}
