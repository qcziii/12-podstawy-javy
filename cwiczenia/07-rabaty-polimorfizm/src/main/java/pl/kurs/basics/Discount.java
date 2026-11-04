package pl.kurs.basics;

/**
 * A discount on a price. All amounts are in grosze (1 zł = 100 gr), so we can use long.
 */
public interface Discount {

    /** Price after the discount. Never less than 0. */
    long apply(long priceInGrosze);

    /** Description shown on the receipt, e.g. "Rabat 10%". */
    String description();
}
