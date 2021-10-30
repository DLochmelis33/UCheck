package ru.hse.se.ucheck.test;

import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.UCheckRamImpl;
import ru.hse.se.ucheck.models.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ru.hse.se.ucheck.test.TestConstants.*;

public class TestGetFilteredItemInStores {

    private static UCheckRamImpl uCheck;

    @BeforeEach
    public void setupUCheck() {
        uCheck = new UCheckRamImpl();
    }

    @Test
    public void testDefaultSingleOption() throws UCheckException {
        uCheck.addCheck(singleItemCheck, Review.OK);

        Assertions.assertIterableEquals(List.of(cocaColaInPerekrestok),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredItemInStores(
                        cocaCola.getCode(), new Filter(), new SortRule())));
    }

    @Test
    public void testFilter() throws UCheckException {
        Item cocaColaExpensive = new Item(1, "Coca-Cola 0.5L", 150.0, Measure.PIECE);
        Store premiumPerekrestok = new Store("Paris, Louvre", perekrestok.getOutlet());

        Check karuselCheck = new Check(List.of(cocaCola),
                ZonedDateTime.now(ZoneId.systemDefault()), karusel);
        Check perekrestokCheapCheck = new Check(List.of(cocaCola),
                ZonedDateTime.now(ZoneId.systemDefault()), perekrestok);
        Check perekrestokExpensiveCheck = new Check(List.of(cocaColaExpensive),
                ZonedDateTime.now(ZoneId.systemDefault()), premiumPerekrestok);

        uCheck.addCheck(karuselCheck, Review.OK);
        uCheck.addCheck(perekrestokCheapCheck, Review.OK);
        uCheck.addCheck(perekrestokExpensiveCheck, Review.OK);

        DoublePredicate pricePredicate = price -> price < 100;
        DoublePredicate ratingPredicate = rating -> true;
        Predicate<String> storeLabelPredicate = storeLabel -> Objects.equals(storeLabel, perekrestok.getOutlet());

        Assertions.assertIterableEquals(List.of(cocaColaInPerekrestok),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredItemInStores(
                        cocaCola.getCode(),
                        new Filter(pricePredicate, ratingPredicate, storeLabelPredicate),
                        new SortRule())));
    }
}
