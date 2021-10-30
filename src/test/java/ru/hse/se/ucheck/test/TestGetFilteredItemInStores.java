package ru.hse.se.ucheck.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.UCheckRamImpl;
import ru.hse.se.ucheck.models.Filter;
import ru.hse.se.ucheck.models.Review;
import ru.hse.se.ucheck.models.SortRule;

import java.util.List;

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

}
