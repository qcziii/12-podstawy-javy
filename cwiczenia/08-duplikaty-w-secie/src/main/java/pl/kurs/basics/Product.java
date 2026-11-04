package pl.kurs.basics;

/**
 * Product from the supplier's catalog. Two products are the same product when they have the same SKU
 * (the name and price can differ between imports, e.g. after a price change).
 */
public class Product {

    private final String sku;
    private final String name;
    private final long priceInGrosze;

    public Product(String sku, String name, long priceInGrosze) {
        this.sku = sku;
        this.name = name;
        this.priceInGrosze = priceInGrosze;
    }

    public String sku() {
        return sku;
    }

    public String name() {
        return name;
    }

    public long priceInGrosze() {
        return priceInGrosze;
    }

    public boolean equals(Product other) {
        return sku.equals(other.sku);
    }

    @Override
    public String toString() {
        return sku + " " + name;
    }
}
