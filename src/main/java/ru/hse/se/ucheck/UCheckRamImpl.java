package ru.hse.se.ucheck;

import ru.hse.se.ucheck.check.Check;
import ru.hse.se.ucheck.check.Item;

import java.time.ZonedDateTime;
import java.util.*;

public class UCheckRamImpl implements UCheck {

    private final ArrayList<Check> checks = new ArrayList<>();
    private final Map<Integer, Item> items = new HashMap<>();
    private final Map<Integer, List<Check>> itemsInfo = new HashMap<>();

    public List<Check> getChecks() {
        return checks;
    }

    public Map<Integer, Item> getItems() {
        return items;
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
            items.putIfAbsent(item.getCode(), item);
            itemsInfo.putIfAbsent(item.getCode(), new LinkedList<>());
            itemsInfo.get(item.getCode()).add(check);
        }
    }

    @Override
    public void removeOldChecks(ZonedDateTime beforeLimit) {
        throw new RuntimeException();
    }

}
