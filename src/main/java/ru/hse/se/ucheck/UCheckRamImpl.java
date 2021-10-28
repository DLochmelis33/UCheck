package ru.hse.se.ucheck;

import ru.hse.se.ucheck.check.Check;
import ru.hse.se.ucheck.check.Item;

import java.util.*;

public class UCheckRamImpl implements UCheck {

    private ArrayList<Check> checks = new ArrayList<>();
    private Map<Integer, Item> items = new HashMap<>();
    private Map<Integer, List<Check>> itemsInfo = new HashMap<>();

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
        if(check.getItems().isEmpty()) {
            throw new UCheckException("check must have at least one item");
        }
    }

}
