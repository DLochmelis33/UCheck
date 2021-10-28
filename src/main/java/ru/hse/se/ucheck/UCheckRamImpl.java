package ru.hse.se.ucheck;

import ru.hse.se.ucheck.check.Check;
import ru.hse.se.ucheck.check.Item;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Predicate;

public class UCheckRamImpl implements UCheck {

    private final ArrayList<Check> checks = new ArrayList<>();
    private final Map<Integer, List<Check>> itemsInfo = new HashMap<>();

    public List<Check> getChecks() {
        return checks;
    }

    public Map<Integer, List<Check>> getItemsInfo() {
        return itemsInfo;
    }

    @Override
    public void addCheck(Check check) throws UCheckException {
        if (check.getItems().isEmpty()) {
            throw new UCheckException("check must have at least one item");
        }
        checks.add(check);
        for (Item item : check.getItems()) {
            itemsInfo.putIfAbsent(item.getCode(), new ArrayList<>());
            itemsInfo.get(item.getCode()).add(check);
        }
    }

    @Override
    public void removeOldChecks(ZonedDateTime beforeLimit) {
        Predicate<Check> checkPredicate = check -> check.getTimestamp().compareTo(beforeLimit) <= 0;
        checks.removeIf(checkPredicate);
        for (List<Check> itemChecks : itemsInfo.values()) {
            itemChecks.removeIf(checkPredicate);
        }
    }

}
