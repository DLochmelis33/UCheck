package ru.hse.se.ucheck.models.sort;

import ru.hse.se.ucheck.models.base.Store;

public interface Sortable {

    double getPrice();

    Store getStore();

    double getAverageStoreRating();

}
