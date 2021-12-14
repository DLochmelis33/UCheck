package ru.hse.se.ucheck.test;

import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.UCheckRamImpl;
import ru.hse.se.ucheck.models.base.Check;
import ru.hse.se.ucheck.models.base.Coordinates;
import ru.hse.se.ucheck.models.base.ItemInStore;
import ru.hse.se.ucheck.models.base.item.Tag;
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

public class TestGetFilteredTagsItemsInStores {

    private static UCheckRamImpl uCheck;

    @BeforeEach
    public void setupUCheck() throws UCheckException {
        uCheck = new UCheckRamImpl();
        uCheck.setItemTags(cocaCola.getCode(), cocaColaDefaultTags);
    }

    @Test
    public void testDefaultSingleOption() throws UCheckException {
        uCheck.addCheck(singleItemCheck, Review.OK);

        Assertions.assertEquals(Map.of(cocaCola.getCode(), List.of(cocaColaInPerekrestok)),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredTagsItemsInStores(
                        cocaColaDefaultTags, new Filter(), new SortRule())));
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

        Assertions.assertEquals(Map.of(cocaCola.getCode(), List.of(cocaColaInPerekrestok)),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredTagsItemsInStores(
                        cocaColaDefaultTags,
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

        Assertions.assertEquals(
                Map.of(cocaCola.getCode(),
                        List.of(cocaColaInPopularPremiumPerekrestok, cocaColaInKarusel, cocaColaInPerekrestok)),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredTagsItemsInStores(
                        cocaColaDefaultTags,
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

        Assertions.assertEquals(Map.of(cocaCola.getCode(), List.of(actualCocaColaInPerekrestok)),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredTagsItemsInStores(
                        cocaColaDefaultTags,
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

        Assertions.assertEquals(Map.of(cocaCola.getCode(), List.of(cocaColaInPerekrestok)),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredTagsItemsInStores(
                        cocaColaDefaultTags,
                        new Filter(pricePredicate, ratingPredicate, storeLabelPredicate, coordinatesPredicate),
                        new SortRule())));
    }

    @Test
    public void testSortByDistanceToCustomer() throws UCheckException {
        uCheck.addCheck(karuselCheck, Review.OK);
        uCheck.addCheck(perekrestokCheapCheck, Review.OK);

        Coordinates customerCoordinates = perekrestok.getCoordinates();

        Assertions.assertEquals(Map.of(cocaCola.getCode(), List.of(cocaColaInPerekrestok, cocaColaInKarusel)),
                Assertions.assertDoesNotThrow(() -> uCheck.getFilteredTagsItemsInStores(
                        cocaColaDefaultTags,
                        new Filter(),
                        new SortRule(List.of(SortParameter.DISTANCE)),
                        customerCoordinates)));
    }

    @Test
    public void testNotUniqueTags() {
        Assertions.assertThrows(UCheckException.class, () ->
                uCheck.getFilteredTagsItemsInStores(List.of(Tag.FOOD, Tag.FOOD), new Filter(), new SortRule()));
    }

    @Test
    public void testTagsMatch() throws UCheckException {
        uCheck.setItemTags(fanta.getCode(), List.of(Tag.DRINK, Tag.CARBONATED_DRINK));
        uCheck.setItemTags(coneForest.getCode(), List.of(Tag.DRINK));

        uCheck.addCheck(karuselCheck, Review.OK);
        uCheck.addCheck(perekrestokExpensiveCheck, Review.OK);

        Assertions.assertEquals(
                Map.of(
                        cocaCola.getCode(), List.of(cocaColaInKarusel, cocaColaInPremiumPerekrestok),
                        fanta.getCode(), List.of()
                ), Assertions.assertDoesNotThrow(() -> uCheck.getFilteredTagsItemsInStores(
                        List.of(Tag.DRINK, Tag.CARBONATED_DRINK),
                        new Filter(),
                        new SortRule())));
    }
}

