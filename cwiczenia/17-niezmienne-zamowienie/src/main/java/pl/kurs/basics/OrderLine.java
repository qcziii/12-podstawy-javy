package pl.kurs.basics;

/**
 * Order line. quantity >= 1, unitPrice in grosze >= 0, product not blank.
 */
public record OrderLine(String product, int quantity, long unitPrice) {

    public long total() {
        return quantity * unitPrice;
    }
}
