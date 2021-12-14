package ru.hse.se.ucheck;

import ru.hse.se.ucheck.models.base.Check;
import ru.hse.se.ucheck.models.base.Coordinates;
import ru.hse.se.ucheck.models.base.ItemInStore;
import ru.hse.se.ucheck.models.base.Store;
import ru.hse.se.ucheck.models.base.item.Tag;
import ru.hse.se.ucheck.models.cart.CartInStore;
import ru.hse.se.ucheck.models.cart.ItemInCart;
import ru.hse.se.ucheck.models.filter.Filter;
import ru.hse.se.ucheck.models.rating.Rating;
import ru.hse.se.ucheck.models.rating.Review;
import ru.hse.se.ucheck.models.sort.SortRule;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public interface UCheck {

    void addCheck(Check check, Review review) throws UCheckException;

    // beforeLimit timestamp is included (will be removed)
    void removeOldChecks(ZonedDateTime beforeLimit) throws UCheckException;

    Rating getStoreRating(Store store) throws UCheckException;

    List<ItemInStore> getFilteredItemInStores(int itemCode, Filter filter, SortRule sortRule) throws UCheckException;

    List<ItemInStore> getFilteredItemInStores(
            int itemCode, Filter filter, SortRule sortRule, Coordinates customerCoordinates) throws UCheckException;

    List<CartInStore> getFilteredCartInStores(
            List<ItemInCart> itemsInCart, Filter filter, SortRule sortRule) throws UCheckException;

    List<CartInStore> getFilteredCartInStores(
            List<ItemInCart> itemsInCart, Filter filter, SortRule sortRule,
            Coordinates customerCoordinates) throws UCheckException;

    void setItemTags(int itemCode, List<Tag> tags) throws UCheckException;

    // tags are sorted in declaration order (in Tag enum class)
    List<Tag> getItemTags(int itemCode) throws UCheckException;

    // only items that have all tags will be shown
    Map<Integer, List<ItemInStore>> getFilteredTagsItemsInStores(
            List<Tag> tags, Filter filter, SortRule sortRule) throws UCheckException;

    Map<Integer, List<ItemInStore>> getFilteredTagsItemsInStores(
            List<Tag> tags, Filter filter, SortRule sortRule, Coordinates customerCoordinates) throws UCheckException;
}
