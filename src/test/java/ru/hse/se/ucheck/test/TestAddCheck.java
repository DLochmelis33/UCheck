package ru.hse.se.ucheck.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hse.se.ucheck.UCheckException;
import ru.hse.se.ucheck.check.Check;
import ru.hse.se.ucheck.UCheck;

import java.time.ZonedDateTime;
import java.util.List;

public class TestAddCheck {

    @Test
    public void testEmptyCheck() {
        UCheck ucheck = Mockito.mock(UCheck.class);
        Check check = new Check(List.of(), ZonedDateTime.now(), 1);
        Assertions.assertThrows(UCheckException.class, () -> ucheck.addCheck(check));
    }



}
