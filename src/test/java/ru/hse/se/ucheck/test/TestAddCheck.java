package ru.hse.se.ucheck.test;

import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.UCheckRamImpl;
import ru.hse.se.ucheck.models.Check;
import ru.hse.se.ucheck.models.Review;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ru.hse.se.ucheck.test.TestConstants.*;

public class TestAddCheck {

    private static UCheckRamImpl uCheck;

    @BeforeEach
    public void setupUCheck() {
        uCheck = new UCheckRamImpl();
    }

    @Test
    public void testEmptyCheck() {
        Check check = new Check(List.of(), ZonedDateTime.now(ZoneId.systemDefault()), perekrestok);
        Assertions.assertThrows(UCheckException.class, () -> uCheck.addCheck(check, Review.OK));
    }

    @Test
    public void testSimpleCheck() throws UCheckException {
        uCheck.addCheck(singleItemCheck, Review.OK);
        Assertions.assertTrue(uCheck.getChecks().contains(singleItemCheck));
    }

    @Test
    public void testNewItem() throws UCheckException {
        uCheck.addCheck(singleItemCheck, Review.OK);
        Assertions.assertTrue(uCheck.getItemsInfo().containsKey(cocaCola.getCode()));
        Assertions.assertIterableEquals(List.of(singleItemCheck), uCheck.getItemsInfo().get(cocaCola.getCode()));
    }

    @Test
    public void testChangeStore() throws UCheckException {
        uCheck.addCheck(singleItemCheck, Review.OK);
        Assertions.assertTrue(uCheck.getStoreInfo().containsKey(singleItemCheck.getStore()));
        Assertions.assertIterableEquals(List.of(singleItemCheck),
                uCheck.getStoreInfo().get(singleItemCheck.getStore()));
    }

    @Test
    public void testRating() throws UCheckException {
        uCheck.addCheck(singleItemCheck, Review.SUPER);
        Assertions.assertEquals(1.0,
                uCheck.getStoreRating(singleItemCheck.getStore()).getAverage());

        Check okCheck = new Check(List.of(fanta),
                ZonedDateTime.now(ZoneId.systemDefault()), singleItemCheck.getStore());
        uCheck.addCheck(okCheck, Review.OK);
        Assertions.assertEquals(0.5,
                uCheck.getStoreRating(singleItemCheck.getStore()).getAverage());

        Check negativeCheck = new Check(List.of(meat),
                ZonedDateTime.now(ZoneId.systemDefault()), singleItemCheck.getStore());
        uCheck.addCheck(negativeCheck, Review.NEGATIVE);
        Assertions.assertEquals(0.0,
                uCheck.getStoreRating(singleItemCheck.getStore()).getAverage());
    }

}
