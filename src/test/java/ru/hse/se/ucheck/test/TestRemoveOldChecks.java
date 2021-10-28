package ru.hse.se.ucheck.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.UCheckRamImpl;

import java.time.ZonedDateTime;
import java.util.List;

import static ru.hse.se.ucheck.test.TestConstants.cocaCola;
import static ru.hse.se.ucheck.test.TestConstants.singleItemCheck;

public class TestRemoveOldChecks {

    private static UCheckRamImpl uCheck;

    @BeforeEach
    public void setupUCheck() throws UCheckException {
        uCheck = new UCheckRamImpl();
        uCheck.addCheck(singleItemCheck);
    }

    @Test
    public void testSingleRemove() {
        uCheck.removeOldChecks(ZonedDateTime.now());
        Assertions.assertFalse(uCheck.getChecks().contains(singleItemCheck));
    }

    @Test
    public void testSingleRemoveFromItems() {
        uCheck.removeOldChecks(ZonedDateTime.now());
        Assertions.assertTrue(uCheck.getItemsInfo().containsKey(cocaCola.getCode()));
        Assertions.assertIterableEquals(List.of(), uCheck.getItemsInfo().get(cocaCola.getCode()));
    }

}
