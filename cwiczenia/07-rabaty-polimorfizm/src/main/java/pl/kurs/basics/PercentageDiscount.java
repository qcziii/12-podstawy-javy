package pl.kurs.basics;

/**
 * Percentage discount, e.g. 10%.
 */
public class PercentageDiscount implements Discount {

    // TODO: fields

    public PercentageDiscount(int percent) {
        // TODO
    }

    @Override
    public long apply(long priceInGrosze) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public String description() {
        throw new UnsupportedOperationException("TODO");
    }
}
