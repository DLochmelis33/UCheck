package ru.hse.se.ucheck.test;

import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.UCheckRamImpl;
import ru.hse.se.ucheck.models.Tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static ru.hse.se.ucheck.test.TestConstants.*;

public class TestAddGetItemTags {

    private static UCheckRamImpl uCheck;

    @BeforeEach
    public void setupUCheck() {
        uCheck = new UCheckRamImpl();
    }

    @Test
    public void testAddNewItem() {
        Assertions.assertDoesNotThrow(() -> uCheck.setItemTags(cocaCola.getCode(), cocaColaDefaultTags));
        Assertions.assertIterableEquals(cocaColaDefaultTags,
                Assertions.assertDoesNotThrow(() -> uCheck.getItemTags(cocaCola.getCode())));
    }

    @Test
    public void testUpdateItemTags() {
        Assertions.assertDoesNotThrow(() -> uCheck.setItemTags(cocaCola.getCode(), cocaColaDefaultTags));
        Assertions.assertDoesNotThrow(() -> uCheck.setItemTags(cocaCola.getCode(), cocaColaUpdatedTags));
        Assertions.assertIterableEquals(cocaColaUpdatedTags,
                Assertions.assertDoesNotThrow(() -> uCheck.getItemTags(cocaCola.getCode())));
    }

    @Test
    public void testNotUniqueTags() {
        Assertions.assertThrows(UCheckException.class,
                () -> uCheck.setItemTags(cocaCola.getCode(), List.of(Tag.DRINK, Tag.DRINK)));
    }

    @Test
    public void testGetAbsentItemTags() {
        Assertions.assertThrows(UCheckException.class, () -> uCheck.getItemTags(cocaCola.getCode()));
    }

}
