package ru.hse.se.ucheck;

import ru.hse.se.ucheck.models.*;

import java.time.ZonedDateTime;
import java.util.List;

public interface UCheck {

    void addCheck(Check check, Review review) throws UCheckException;

    // beforeLimit timestamp is included (will be removed)
    void removeOldChecks(ZonedDateTime beforeLimit) throws UCheckException;

    Rating getStoreRating(Store store) throws UCheckException;

    List<ItemInStore> getFilteredItemInStores(int itemCode, Filter filter, SortRule sortRule);
}
