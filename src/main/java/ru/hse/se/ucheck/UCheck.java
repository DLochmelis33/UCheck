package ru.hse.se.ucheck;

import ru.hse.se.ucheck.models.Check;
import ru.hse.se.ucheck.models.Review;

import java.time.ZonedDateTime;

public interface UCheck {

    void addCheck(Check check, Review review) throws UCheckException;

    // beforeLimit timestamp is included (will be removed)
    void removeOldChecks(ZonedDateTime beforeLimit) throws UCheckException;

}
