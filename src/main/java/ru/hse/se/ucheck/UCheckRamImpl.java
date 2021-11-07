package ru.hse.se.ucheck;

import ru.hse.se.ucheck.models.base.Check;
import ru.hse.se.ucheck.models.base.Coordinates;
import ru.hse.se.ucheck.models.base.ItemInStore;
import ru.hse.se.ucheck.models.base.Store;
import ru.hse.se.ucheck.models.base.item.Item;
import ru.hse.se.ucheck.models.base.item.Tag;
import ru.hse.se.ucheck.models.cart.CartInStore;
import ru.hse.se.ucheck.models.cart.ItemInCart;
import ru.hse.se.ucheck.models.filter.Filter;
import ru.hse.se.ucheck.models.rating.Rating;
import ru.hse.se.ucheck.models.rating.Review;
import ru.hse.se.ucheck.models.sort.SortRule;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UCheckRamImpl implements UCheck {

    private final ArrayList<Check> checks = new ArrayList<>();
    private final Map<Integer, List<Check>> itemsInfo = new HashMap<>();
    private final Map<Integer, Set<Tag>> itemTags = new HashMap<>();
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

    @Override
    public List<ItemInStore> getFilteredItemInStores(
            int itemCode, Filter filter, SortRule sortRule) throws UCheckException {
        return getFilteredItemInStores(itemCode, filter, sortRule, null);
    }

    @Override
    public List<ItemInStore> getFilteredItemInStores(
            int itemCode, Filter filter, SortRule sortRule, Coordinates customerCoordinates) throws UCheckException {
        return itemsInfo.get(itemCode).stream()
                .filter(filter.getItemInCheckPredicate(itemCode, this))
                .collect(
                        Collectors.groupingBy(
                                Check::getStore,
                                Collectors.maxBy(
                                        Comparator.comparing(Check::getTimestamp))))
                .values()
                .stream()
                .map(Optional::get)
                .map(check -> new ItemInStore(
                        check.getItemByCode(itemCode).orElseThrow().getPrice(),
                        check.getStore(),
                        storeRating.get(check.getStore()).getAverage()))
                .sorted(sortRule.getComparator(customerCoordinates))
                .collect(Collectors.toList());
    }

    private CartInStore getCartInStoreFromStoreChecks(List<ItemInCart> itemsInCart, Store store) {
        if (!storesInfo.containsKey(store)) {
            throw new IllegalArgumentException("no such store in UCheck");
        }
        Map<ItemInCart, Double> prices = new HashMap<>();
        itemsInCart.forEach(itemInCart -> {
            int itemCode = itemInCart.getItemCode();
            Optional<Double> minPrice =
                    storesInfo.get(store).stream()
                            .filter(check -> check.getItemByCode(itemCode).isPresent())
                            .collect(
                                    Collectors.groupingBy(
                                            Check::getStore,
                                            Collectors.maxBy(
                                                    Comparator.comparing(Check::getTimestamp))))
                            .values()
                            .stream()
                            .map(Optional::get)
                            .map(check -> check.getItemByCode(itemCode).orElseThrow().getPrice())
                            .min(Double::compare);
            minPrice.ifPresent(price -> prices.put(itemInCart, price));
        });
        try {
            return new CartInStore(prices, store, this.getStoreRating(store).getAverage());
        } catch (UCheckException exc) {
            throw new IllegalStateException("check's store doesn't have rating", exc);
        }
    }

    @Override
    public List<CartInStore> getFilteredCartInStores(
            List<ItemInCart> itemsInCart, Filter filter, SortRule sortRule) throws UCheckException {
        return getFilteredCartInStores(itemsInCart, filter, sortRule, null);
    }

    @Override
    public List<CartInStore> getFilteredCartInStores(
            List<ItemInCart> itemsInCart, Filter filter, SortRule sortRule,
            Coordinates customerCoordinates) throws UCheckException {
        if (new HashSet<>(
                itemsInCart.stream()
                        .map(ItemInCart::getItemCode)
                        .collect(Collectors.toList()))
                .size() != itemsInCart.size()) {
            throw new UCheckException("Item's in cart aren't unique");
        }
        return storesInfo.keySet().stream()
                .map(store -> getCartInStoreFromStoreChecks(itemsInCart, store))
                .filter(filter.getCartInStorePredicate(itemsInCart, this))
                .sorted(sortRule.getComparator(customerCoordinates))
                .collect(Collectors.toList());
    }

    @Override
    public void setItemTags(int itemCode, List<Tag> tags) throws UCheckException {
        Set<Tag> tagsSet = new HashSet<>(tags);
        if (tagsSet.size() != tags.size()) {
            throw new UCheckException("tags aren't unique");
        }
        itemTags.put(itemCode, tagsSet);
        itemsInfo.putIfAbsent(itemCode, new ArrayList<>());
    }

    @Override
    public List<Tag> getItemTags(int itemCode) throws UCheckException {
        if (!itemTags.containsKey(itemCode)) {
            throw new UCheckException("no such item in UCheck");
        }
        return itemTags.get(itemCode).stream()
                .sorted(Comparator.comparingInt(Tag::ordinal))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, List<ItemInStore>> getFilteredTagsItemsInStores(
            List<Tag> tags, Filter filter, SortRule sortRule) throws UCheckException {
        return getFilteredTagsItemsInStores(tags, filter, sortRule, null);
    }

    @Override
    public Map<Integer, List<ItemInStore>> getFilteredTagsItemsInStores(
            List<Tag> tags, Filter filter, SortRule sortRule, Coordinates customerCoordinates) throws UCheckException {
        Set<Tag> tagsSet = new HashSet<>(tags);
        if (tagsSet.size() != tags.size()) {
            throw new UCheckException("tags aren't unique");
        }
        try {
            return itemTags.entrySet().stream()
                    .filter(entry -> entry.getValue().containsAll(tagsSet))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toMap(
                            Function.identity(),
                            itemCode -> {
                                try {
                                    return getFilteredItemInStores(itemCode, filter, sortRule, customerCoordinates);
                                } catch (UCheckException exc) {
                                    throw new RuntimeException(exc);
                                }
                            }));
        } catch (RuntimeException wrappedExc) {
            throw (UCheckException) wrappedExc.getCause();
        }
    }
}
