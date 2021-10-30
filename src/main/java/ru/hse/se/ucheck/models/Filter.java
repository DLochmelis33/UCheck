package ru.hse.se.ucheck.models;

import java.util.function.DoublePredicate;
import java.util.function.Predicate;

public class Filter {

    private final DoublePredicate pricePredicate = price -> true;
    private final DoublePredicate storeRatingPredicate = storeRating -> true;
    private final Predicate<String> outletPredicate = outlet -> true;

}
