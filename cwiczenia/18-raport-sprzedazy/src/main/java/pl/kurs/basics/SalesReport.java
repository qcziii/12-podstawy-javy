package pl.kurs.basics;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Sales report. Write every method with streams, without for/while loops.
 */
public class SalesReport {

    private final List<Sale> sales;

    public SalesReport(List<Sale> sales) {
        this.sales = List.copyOf(sales);
    }

    /** Revenue of all sales together. */
    public long totalRevenue() {
        throw new UnsupportedOperationException("TODO");
    }

    /** Revenue per region, regions in alphabetical order. */
    public Map<String, Long> revenueByRegion() {
        throw new UnsupportedOperationException("TODO");
    }

    /** Names of the n products with the largest number of pieces sold (desc). Same number: alphabetically. */
    public List<String> topProducts(int n) {
        throw new UnsupportedOperationException("TODO");
    }

    /** Sellers who sold anything in the region, alphabetically, without repetitions. */
    public List<String> sellersIn(String region) {
        throw new UnsupportedOperationException("TODO");
    }

    /** Seller with the largest revenue (all regions together). No sales -> empty Optional. */
    public Optional<String> bestSeller() {
        throw new UnsupportedOperationException("TODO");
    }

    /** Average number of pieces per sale. No sales -> 0.0. */
    public double averageQuantity() {
        throw new UnsupportedOperationException("TODO");
    }

    /** Sales grouped by region: region -> list of products sold there (in the order of sales, with repetitions). */
    public Map<String, List<String>> productsByRegion() {
        throw new UnsupportedOperationException("TODO");
    }

    /** Receipt-style lines of sales with revenue >= minRevenue: "seller: product x quantity", in the order of sales. */
    public List<String> bigSales(long minRevenue) {
        throw new UnsupportedOperationException("TODO");
    }
}
