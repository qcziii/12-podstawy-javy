package pl.kurs.basics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Imports products from several supplier files. The same product can appear in many files.
 */
public class CatalogImport {

    /** Unique products (by SKU). */
    public static Set<Product> unique(List<Product> imported) {
        return new HashSet<>(imported);
    }

    /** Products from 'imported' that are not in 'existing' yet. */
    public static List<Product> newProducts(List<Product> existing, List<Product> imported) {
        List<Product> result = new ArrayList<>();
        for (Product p : imported) {
            if (!existing.contains(p)) {
                result.add(p);
            }
        }
        return result;
    }

    /** Stock count per product: how many times the product appears in the delivery. */
    public static Map<Product, Integer> stock(List<Product> delivery) {
        Map<Product, Integer> stock = new HashMap<>();
        for (Product p : delivery) {
            stock.merge(p, 1, Integer::sum);
        }
        return stock;
    }
}
