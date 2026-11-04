package pl.kurs.basics;

import java.util.List;

/**
 * Several discounts applied one after another, in the given order.
 */
public class CombinedDiscount implements Discount {

    // TODO: fields

    public CombinedDiscount(List<Discount> discounts) {
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
