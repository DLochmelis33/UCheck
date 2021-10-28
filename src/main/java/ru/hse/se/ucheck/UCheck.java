package ru.hse.se.ucheck;

import ru.hse.se.ucheck.check.Check;

public interface UCheck {

    void addCheck(Check check) throws UCheckException;

}
