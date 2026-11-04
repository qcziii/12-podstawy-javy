package pl.kurs.basics;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Shopping list. Starts with some products, you can add more and tick off the bought ones.
 */
public class ShoppingList {

    private final List<String> items;

    public ShoppingList(String... initialItems) {
        this.items = Arrays.asList(initialItems);
    }

    public void add(String item) {
        items.add(item);
    }

    /** Removes all the bought products from the list. */
    public void tickOff(Set<String> bought) {
        for (String item : items) {
            if (bought.contains(item)) {
                items.remove(item);
            }
        }
    }

    public List<String> items() {
        return List.copyOf(items);
    }
}
