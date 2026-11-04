package pl.kurs.basics;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Invoice. Prices and amounts come from the API as BigDecimal.
 */
public class Invoice {

    // "double is faster than BigDecimal" (from the last refactoring)
    private double net;

    public void addItem(BigDecimal unitPrice, int quantity) {
        net += unitPrice.doubleValue() * quantity;
    }

    /** Net total (sum of unit price * quantity). */
    public BigDecimal net() {
        return BigDecimal.valueOf(net);
    }

    /**
     * Gross total: net * (1 + vatRate), rounded to 2 decimal places (to a grosz) the usual way:
     * from 0.005 up (1.845 -> 1.85, 1.844 -> 1.84).
     */
    public BigDecimal gross(BigDecimal vatRate) {
        double gross = net * (1 + vatRate.doubleValue());
        return BigDecimal.valueOf(gross).setScale(2, RoundingMode.DOWN);
    }

    /** Whether the paid amount covers the net total exactly. */
    public boolean isPaidExactly(BigDecimal amountPaid) {
        return amountPaid.equals(net());
    }
}
