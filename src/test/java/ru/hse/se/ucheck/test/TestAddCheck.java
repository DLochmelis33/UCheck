package ru.hse.se.ucheck.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.UCheckRamImpl;
import ru.hse.se.ucheck.check.Check;
import ru.hse.se.ucheck.UCheck;
import ru.hse.se.ucheck.check.Item;
import ru.hse.se.ucheck.check.Measure;

import java.time.ZonedDateTime;
import java.util.List;

public class TestAddCheck {

    private static final Item cocaCola = new Item(1, "Coca-Cola 0.5L", 50.0, Measure.PIECE);
    private static final Item fanta = new Item(2, "Fanta 1L", 90.0, Measure.PIECE);
    private static final Item meat = new Item(3, "Porkchop", 359.9, Measure.KILOGRAM);

    public static final Check sampleCheck = new Check(List.of(cocaCola), ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]"), 0);

    @Test
    public void testEmptyCheck() {
        UCheck ucheck = new UCheckRamImpl();
        Check check = new Check(List.of(), ZonedDateTime.now(), 1);
        Assertions.assertThrows(UCheckException.class, () -> ucheck.addCheck(check));
    }

    @Test
    public void testSimpleCheck() throws UCheckException {
        UCheckRamImpl uCheck = new UCheckRamImpl();
        uCheck.addCheck(sampleCheck);
        Assertions.assertTrue(uCheck.getChecks().contains(sampleCheck));
    }

    @Test
    public void testNewItem() throws UCheckException {
        UCheckRamImpl uCheck = new UCheckRamImpl();
        uCheck.addCheck(sampleCheck);
        Assertions.assertTrue(uCheck.getItems().containsKey(cocaCola.getCode()));
        Assertions.assertEquals(cocaCola, uCheck.getItems().get(cocaCola.getCode()));
        Assertions.assertTrue(uCheck.getItemsInfo().containsKey(cocaCola.getCode()));
        Assertions.assertIterableEquals(List.of(sampleCheck), uCheck.getItemsInfo().get(cocaCola.getCode()));
    }

}
