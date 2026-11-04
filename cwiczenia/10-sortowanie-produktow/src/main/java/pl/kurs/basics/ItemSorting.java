package pl.kurs.basics;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Sorting for the shop's product list.
 */
public class ItemSorting {

    /** Cheapest first. Same price: alphabetically by name. Does not change the input list. */
    public static List<Item> byPrice(List<Item> items) {
        List<Item> copy = new ArrayList<>(items);
        copy.sort((a, b) -> (int) (a.price() - b.price()));
        return copy;
    }

    /**
     * Catalog: all items sorted by name, and the same name by price (cheapest first).
     * Only identical items (same name and price) appear once.
     */
    public static SortedSet<Item> catalog(List<Item> items) {
        SortedSet<Item> catalog = new TreeSet<>((a, b) -> a.name().compareTo(b.name()));
        catalog.addAll(items);
        return catalog;
    }
}
