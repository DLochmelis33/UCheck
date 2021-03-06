package ru.hse.se.ucheck.test;

import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.UCheckRamImpl;
import ru.hse.se.ucheck.models.base.Check;
import ru.hse.se.ucheck.models.base.Coordinates;
import ru.hse.se.ucheck.models.base.ItemInStore;
import ru.hse.se.ucheck.models.filter.Filter;
import ru.hse.se.ucheck.models.rating.Review;
import ru.hse.se.ucheck.models.sort.SortParameter;
import ru.hse.se.ucheck.models.sort.SortRule;

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
        uCheck.addCheck(karuselCheck, Review.OK);
        uCheck.addCheck(perekrestokCheapCheck, Review.OK);
        uCheck.addCheck(perekrestokExpensiveCheck, Review.OK);

        DoublePredicate pricePredicate = price -> price < 100;
        DoublePredicate ratingPredicate = rating -> true;
        Predicate<String> storeLabelPredicate = storeLabel -> Objects.equals(storeLabel, perekrestok.getOutlet());
        Predicate<Coordinates> storeCoordinatesPredicate = storeCoordinates -> true;

        Assertions.assertIterableEquals(List.of(cocaColaInPerekrestok),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredItemInStores(
                        cocaCola.getCode(),
                        new Filter(pricePredicate, ratingPredicate, storeLabelPredicate, storeCoordinatesPredicate),
                        new SortRule())));
    }

    @Test
    public void testSort() throws UCheckException {
        uCheck.addCheck(karuselCheck, Review.OK);
        uCheck.addCheck(perekrestokCheapCheck, Review.OK);
        uCheck.addCheck(perekrestokExpensiveCheck, Review.SUPER);

        ItemInStore cocaColaInPopularPremiumPerekrestok = new ItemInStore(
                cocaColaExpensive.getPrice(), premiumPerekrestok, 1.0);

        Assertions.assertIterableEquals(
                List.of(cocaColaInPopularPremiumPerekrestok, cocaColaInKarusel, cocaColaInPerekrestok),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredItemInStores(
                        cocaCola.getCode(),
                        new Filter(),
                        new SortRule(List.of(SortParameter.RATING, SortParameter.PRICE, SortParameter.OUTLET)))));
    }

    @Test
    public void testActualChecks() throws UCheckException {
        Check outdatedItemCheck = new Check(List.of(cocaCola),
                ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]"), perekrestok);
        Check actualItemCheck = new Check(List.of(cocaColaExpensive),
                ZonedDateTime.now(ZoneId.systemDefault()), perekrestok);

        uCheck.addCheck(outdatedItemCheck, Review.OK);
        uCheck.addCheck(actualItemCheck, Review.OK);

        ItemInStore actualCocaColaInPerekrestok = new ItemInStore(cocaColaExpensive.getPrice(), perekrestok, 0.0);

        Assertions.assertIterableEquals(List.of(actualCocaColaInPerekrestok),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredItemInStores(
                        cocaCola.getCode(),
                        new Filter(),
                        new SortRule())));
    }

    @Test
    public void testNotUniqueSortParameters() {
        Assertions.assertThrows(UCheckException.class, () -> new SortRule(
                List.of(SortParameter.RATING, SortParameter.RATING)));
    }

    @Test
    public void testFilterByCoordinates() throws UCheckException {
        uCheck.addCheck(karuselCheck, Review.OK);
        uCheck.addCheck(perekrestokCheapCheck, Review.OK);

        DoublePredicate pricePredicate = price -> true;
        DoublePredicate ratingPredicate = rating -> true;
        Predicate<String> storeLabelPredicate = storeLabel -> true;
        Predicate<Coordinates> coordinatesPredicate = coordinates
                -> Objects.equals(coordinates, perekrestok.getCoordinates());

        Assertions.assertIterableEquals(List.of(cocaColaInPerekrestok),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredItemInStores(
                        cocaCola.getCode(),
                        new Filter(pricePredicate, ratingPredicate, storeLabelPredicate, coordinatesPredicate),
                        new SortRule())));
    }

    @Test
    public void testSortByDistanceToCustomer() throws UCheckException {
        uCheck.addCheck(karuselCheck, Review.OK);
        uCheck.addCheck(perekrestokCheapCheck, Review.OK);

        Coordinates customerCoordinates = perekrestok.getCoordinates();

        Assertions.assertIterableEquals(List.of(cocaColaInPerekrestok, cocaColaInKarusel),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredItemInStores(
                        cocaCola.getCode(),
                        new Filter(),
                        new SortRule(List.of(SortParameter.DISTANCE)),
                        customerCoordinates)));
    }

    @Test
    public void testReturnEmptyList() throws UCheckException {
        uCheck.addCheck(perekrestokCheapCheck, Review.NEGATIVE);

        DoublePredicate pricePredicate = price -> true;
        DoublePredicate ratingPredicate = rating -> rating >= 0;
        Predicate<String> storeLabelPredicate = storeLabel -> true;
        Predicate<Coordinates> storeCoordinatesPredicate = storeCoordinates -> true;

        Assertions.assertIterableEquals(List.of(),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredItemInStores(
                        cocaCola.getCode(),
                        new Filter(pricePredicate, ratingPredicate, storeLabelPredicate, storeCoordinatesPredicate),
                        new SortRule())));
    }
}
