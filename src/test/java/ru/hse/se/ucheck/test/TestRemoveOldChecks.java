package ru.hse.se.ucheck.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hse.se.ucheck.UCheck;
import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.UCheckRamImpl;
import ru.hse.se.ucheck.check.Item;
import ru.hse.se.ucheck.check.Measure;

import java.time.ZonedDateTime;

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

}
