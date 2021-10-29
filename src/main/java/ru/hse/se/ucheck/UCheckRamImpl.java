package ru.hse.se.ucheck;

import ru.hse.se.ucheck.models.*;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Predicate;

public class UCheckRamImpl implements UCheck {

    private final ArrayList<Check> checks = new ArrayList<>();
    private final Map<Integer, List<Check>> itemsInfo = new HashMap<>();
    private final Map<Store, List<Check>> storesInfo = new HashMap<>();
    private final Map<Store, Rating> storeRating = new HashMap<>();

    public List<Check> getChecks() {
        return checks;
    }

    public Map<Integer, List<Check>> getItemsInfo() {
        return itemsInfo;
    }

    public Map<Store, List<Check>> getStoreInfo() {
        return storesInfo;
    }

    @Override
    public void addCheck(Check check, Review review) throws UCheckException {
        if (check.getItems().isEmpty()) {
            throw new UCheckException("check must have at least one item");
        }
        checks.add(check);
        for (Item item : check.getItems()) {
            itemsInfo.putIfAbsent(item.getCode(), new ArrayList<>());
            itemsInfo.get(item.getCode()).add(check);
        }
        storesInfo.putIfAbsent(check.getStore(), new ArrayList<>());
        storesInfo.get(check.getStore()).add(check);

        storeRating.putIfAbsent(check.getStore(), new Rating());
        storeRating.get(check.getStore()).applyReview(review);
    }

    @Override
    public void removeOldChecks(ZonedDateTime beforeLimit) {
        Predicate<Check> checkPredicate = check -> check.getTimestamp().compareTo(beforeLimit) <= 0;
        checks.removeIf(checkPredicate);
        for (List<Check> itemChecks : itemsInfo.values()) {
            itemChecks.removeIf(checkPredicate);
        }
        for (List<Check> storeChecks : storesInfo.values()) {
            storeChecks.removeIf(checkPredicate);
        }
    }

    @Override
    public Rating getStoreRating(Store store) throws UCheckException {
        if (!storeRating.containsKey(store)) {
            throw new UCheckException("store doesn't exist");
        }
        return storeRating.get(store);
    }

}
