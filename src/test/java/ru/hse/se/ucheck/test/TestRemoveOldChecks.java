package ru.hse.se.ucheck.test;

import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.UCheckRamImpl;
import ru.hse.se.ucheck.models.Rating;
import ru.hse.se.ucheck.models.Review;
import ru.hse.se.ucheck.models.Store;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ru.hse.se.ucheck.test.TestConstants.cocaCola;
import static ru.hse.se.ucheck.test.TestConstants.singleItemCheck;

public class TestRemoveOldChecks {

    private static UCheckRamImpl uCheck;

    @BeforeEach
    public void setupUCheck() throws UCheckException {
        uCheck = new UCheckRamImpl();
        uCheck.addCheck(singleItemCheck, Review.OK);
    }

    @Test
    public void testSingleRemove() {
        uCheck.removeOldChecks(ZonedDateTime.now(ZoneId.systemDefault()));
        Assertions.assertFalse(uCheck.getChecks().contains(singleItemCheck));
    }

    @Test
    public void testRemoveFromItems() {
        uCheck.removeOldChecks(ZonedDateTime.now(ZoneId.systemDefault()));
        Assertions.assertTrue(uCheck.getItemsInfo().containsKey(cocaCola.getCode()));
        Assertions.assertIterableEquals(List.of(), uCheck.getItemsInfo().get(cocaCola.getCode()));
    }

    @Test
    public void testRemoveFromStores() throws UCheckException {
        Store store = singleItemCheck.getStore();
        Rating storeRating = uCheck.getStoreRating(store);
        uCheck.removeOldChecks(ZonedDateTime.now(ZoneId.systemDefault()));

        Assertions.assertTrue(uCheck.getStoreInfo().containsKey(store));
        Assertions.assertIterableEquals(List.of(), uCheck.getStoreInfo().get(store));

        Assertions.assertEquals(storeRating, Assertions.assertDoesNotThrow(() -> uCheck.getStoreRating(store)));
    }

}
