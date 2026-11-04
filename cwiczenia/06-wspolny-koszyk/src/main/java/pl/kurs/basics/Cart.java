package pl.kurs.basics;

import java.util.ArrayList;
import java.util.List;

/**
 * Shopping cart. Every customer gets their own cart; ids are assigned one after another: 1, 2, 3...
 */
public class Cart {

    private static int nextId = 1;

    private final int id;
    private static String owner;
    private static List<String> products = new ArrayList<>();

    public Cart(String owner) {
        this.id = nextId++;
        Cart.owner = owner;
    }

    public void add(String product) {
        products.add(product);
    }

    public int id() {
        return id;
    }

    public String owner() {
        return owner;
    }

    public List<String> products() {
        return List.copyOf(products);
    }

    /** How many carts have been created so far (all of them together). */
    public static int createdCount() {
        return nextId - 1;
    }
}
