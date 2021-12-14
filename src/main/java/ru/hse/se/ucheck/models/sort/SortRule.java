package ru.hse.se.ucheck.models.sort;

import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.models.base.Coordinates;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

// sort in descending or alphabetical order
public class SortRule {

    private List<SortParameter> parameters = List.of(SortParameter.PRICE, SortParameter.RATING, SortParameter.OUTLET);

    public SortRule() {
    }

    public SortRule(List<SortParameter> parameters) throws UCheckException {
        if (new HashSet<>(parameters).size() != parameters.size()) {
            throw new UCheckException("Sort parameters aren't unique");
        }
        this.parameters = parameters;
    }

    public <T extends Sortable> Comparator<T> getComparator(Coordinates customerCoordinates) throws UCheckException {
        Comparator<T> comparator = (t1, t2) -> 0;
        for (SortParameter parameter : parameters) {
            switch (parameter) {
                case PRICE:
                    comparator = comparator.thenComparingDouble(Sortable::getPrice);
                    break;
                case RATING:
                    comparator = comparator.thenComparing(
                            Comparator.comparingDouble(Sortable::getAverageStoreRating).reversed());
                    break;
                case OUTLET:
                    comparator = comparator.thenComparing(t -> t.getStore().getOutlet());
                    break;
                case DISTANCE:
                    if (customerCoordinates == null) {
                        throw new UCheckException("using DISTANCE, but customerCoordinates are null");
                    }
                    comparator = comparator.thenComparing(
                            t -> t.getStore().getCoordinates().getDistance(customerCoordinates));
                    break;
                default:
                    throw new IllegalStateException("undefined SortParameter");
            }
        }
        return comparator;
    }
}
