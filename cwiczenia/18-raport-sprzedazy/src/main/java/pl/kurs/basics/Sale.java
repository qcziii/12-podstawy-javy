package pl.kurs.basics;

/**
 * One sale: who sold how much of what, where. unitPrice in grosze.
 */
public record Sale(String region, String seller, String product, int quantity, long unitPrice) {

    public long revenue() {
        return quantity * unitPrice;
    }
}
