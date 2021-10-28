package ru.hse.se.ucheck;

import ru.hse.se.ucheck.check.Check;

import java.time.ZonedDateTime;

public interface UCheck {

    void addCheck(Check check) throws UCheckException;

    // beforeLimit timestamp is included (will be removed)
    void removeOldChecks(ZonedDateTime beforeLimit) throws UCheckException;
}
