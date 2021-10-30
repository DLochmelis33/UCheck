package ru.hse.se.ucheck.models;

import ru.hse.se.ucheck.UCheck;
import ru.hse.se.ucheck.UCheckException;

import java.util.function.DoublePredicate;
import java.util.function.Predicate;

public class Filter {

    private DoublePredicate pricePredicate = price -> true;
    private DoublePredicate storeRatingPredicate = storeRating -> true;
    private Predicate<String> outletPredicate = outlet -> true;
    private Predicate<Coordinates> coordinatesPredicate = coordinates -> true;

    public Filter() {
    }

    public Filter(DoublePredicate pricePredicate,
                  DoublePredicate storeRatingPredicate,
                  Predicate<String> outletPredicate,
                  Predicate<Coordinates> coordinatesPredicate) {
        this.pricePredicate = pricePredicate;
        this.storeRatingPredicate = storeRatingPredicate;
        this.outletPredicate = outletPredicate;
    }

    public Predicate<Check> getItemInCheckPredicate(int itemCode, UCheck uCheck) {
        return check -> {
            try {
                return check.getItemByCode(itemCode).isPresent()
                        && pricePredicate.test(check.getItemByCode(itemCode).get().getPrice())
                        && storeRatingPredicate.test(uCheck.getStoreRating(check.getStore()).getAverage())
                        && outletPredicate.test(check.getStore().getOutlet());
            } catch (UCheckException exc) {
                throw new IllegalStateException("check's store doesn't have rating", exc);
            }
        };
    }
}
