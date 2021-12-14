package ru.hse.se.ucheck.test;

import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.UCheckRamImpl;
import ru.hse.se.ucheck.models.base.Check;
import ru.hse.se.ucheck.models.base.Coordinates;
import ru.hse.se.ucheck.models.cart.CartInStore;
import ru.hse.se.ucheck.models.filter.Filter;
import ru.hse.se.ucheck.models.rating.Review;
import ru.hse.se.ucheck.models.sort.SortParameter;
import ru.hse.se.ucheck.models.sort.SortRule;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ru.hse.se.ucheck.test.TestConstants.*;

public class TestGetFilteredCartInStores {

    private static UCheckRamImpl uCheck;

    @BeforeEach
    public void setupUCheck() {
        uCheck = new UCheckRamImpl();
    }

    @Test
    public void testDefaultSingleOption() throws UCheckException {
        uCheck.addCheck(singleItemCheck, Review.OK);

        Assertions.assertIterableEquals(List.of(singleItemCart),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredCartInStores(
                        List.of(oneCocaCola), new Filter(), new SortRule())));
    }

    @Test
    public void testMultiCartSingleOption() throws UCheckException {
        uCheck.addCheck(multiItemCheck, Review.OK);

        Assertions.assertIterableEquals(List.of(multiItemCart),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredCartInStores(
                        List.of(twoCocaColas, oneFanta, oneMeat), new Filter(), new SortRule())));
    }

    @Test
    public void testFilter() throws UCheckException {
        uCheck.addCheck(karuselCheck, Review.OK);
        uCheck.addCheck(perekrestokCheapCheck, Review.OK);
        uCheck.addCheck(perekrestokExpensiveCheck, Review.OK);

        DoublePredicate pricePredicate = price -> price <= cocaCola.getPrice() * 5;
        DoublePredicate ratingPredicate = rating -> true;
        Predicate<String> storeLabelPredicate = storeLabel -> Objects.equals(storeLabel, perekrestok.getOutlet());
        Predicate<Coordinates> storeCoordinatesPredicate = storeCoordinates -> true;

        Assertions.assertIterableEquals(List.of(perekrestokCart),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredCartInStores(
                        List.of(fiveCocaColas),
                        new Filter(pricePredicate, ratingPredicate, storeLabelPredicate, storeCoordinatesPredicate),
                        new SortRule())));
    }

    @Test
    public void testSort() throws UCheckException {
        uCheck.addCheck(karuselCheck, Review.OK);
        uCheck.addCheck(perekrestokCheapCheck, Review.OK);
        uCheck.addCheck(perekrestokExpensiveCheck, Review.SUPER);

        CartInStore popularPremiumPerekrestokCart = new CartInStore(
                premiumPerekrestokCart.getPrices(), premiumPerekrestok, 1.0);

        Assertions.assertIterableEquals(
                List.of(popularPremiumPerekrestokCart, karuselCart, perekrestokCart),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredCartInStores(
                        List.of(fiveCocaColas),
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

        CartInStore perekrestokCartWithActualCocaCola = new CartInStore(
                Map.of(fiveCocaColas, cocaColaExpensive.getPrice()), perekrestok, 0.0);

        Assertions.assertIterableEquals(List.of(perekrestokCartWithActualCocaCola),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredCartInStores(
                        List.of(fiveCocaColas),
                        new Filter(),
                        new SortRule())));
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

        Assertions.assertIterableEquals(List.of(perekrestokCart),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredCartInStores(
                        List.of(fiveCocaColas),
                        new Filter(pricePredicate, ratingPredicate, storeLabelPredicate, coordinatesPredicate),
                        new SortRule())));
    }

    @Test
    public void testSortByDistanceToCustomer() throws UCheckException {
        uCheck.addCheck(karuselCheck, Review.OK);
        uCheck.addCheck(perekrestokCheapCheck, Review.OK);

        Coordinates customerCoordinates = perekrestok.getCoordinates();

        Assertions.assertIterableEquals(List.of(perekrestokCart, karuselCart),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredCartInStores(
                        List.of(fiveCocaColas),
                        new Filter(),
                        new SortRule(List.of(SortParameter.DISTANCE)),
                        customerCoordinates)));
    }

    @Test
    public void testNotUniqueItemsInCart() {
        Assertions.assertThrows(UCheckException.class, () ->
                uCheck.getFilteredCartInStores(List.of(oneCocaCola, fiveCocaColas), new Filter(), new SortRule()));
    }
}
