package ru.hse.se.ucheck.test;

import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.UCheckRamImpl;
import ru.hse.se.ucheck.models.Check;
import ru.hse.se.ucheck.UCheck;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hse.se.ucheck.models.Store;

import static ru.hse.se.ucheck.test.TestConstants.*;

public class TestAddCheck {

    @Test
    public void testEmptyCheck() {
        UCheck ucheck = new UCheckRamImpl();
        Check check = new Check(List.of(), ZonedDateTime.now(), perekrestok);
        Assertions.assertThrows(UCheckException.class, () -> ucheck.addCheck(check));
    }

    @Test
    public void testSimpleCheck() throws UCheckException {
        UCheckRamImpl uCheck = new UCheckRamImpl();
        uCheck.addCheck(singleItemCheck);
        Assertions.assertTrue(uCheck.getChecks().contains(singleItemCheck));
    }

    @Test
    public void testNewItem() throws UCheckException {
        UCheckRamImpl uCheck = new UCheckRamImpl();
        uCheck.addCheck(singleItemCheck);
        Assertions.assertTrue(uCheck.getItemsInfo().containsKey(cocaCola.getCode()));
        Assertions.assertIterableEquals(List.of(singleItemCheck), uCheck.getItemsInfo().get(cocaCola.getCode()));
    }

}
