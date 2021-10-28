package ru.hse.se.ucheck;

import ru.hse.se.ucheck.models.Check;
import ru.hse.se.ucheck.models.Rating;

import java.time.ZonedDateTime;

public interface UCheck {

    void addCheck(Check check, int rating) throws UCheckException;

    // beforeLimit timestamp is included (will be removed)
    void removeOldChecks(ZonedDateTime beforeLimit) throws UCheckException;

}
