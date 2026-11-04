package pl.kurs.basics;

/**
 * Discount by a fixed amount, e.g. 5,00 zł.
 */
public class FixedAmountDiscount implements Discount {

    // TODO: fields

    public FixedAmountDiscount(long amountInGrosze) {
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
