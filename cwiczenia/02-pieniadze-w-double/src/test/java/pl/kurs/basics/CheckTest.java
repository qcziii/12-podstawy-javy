package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    private static BigDecimal bd(String s) {
        return new BigDecimal(s);
    }

    private static void assertAmount(String expected, BigDecimal actual, String what) {
        assertNotNull(actual, what);
        assertEquals(0, bd(expected).compareTo(actual), what + ": oczekiwano " + expected + ", jest " + actual);
    }

    @Test
    @DisplayName("1) 3 x 0.10 zł to dokładnie 0.30 zł netto")
    void tenGroszeThreeTimes() {
        Invoice f = new Invoice();
        f.addItem(bd("0.10"), 3);
        assertAmount("0.30", f.net(), "Netto");
    }

    @Test
    @DisplayName("2) 0.10 + 0.20 + 0.70 zł to dokładnie 1.00 zł netto")
    void sumOfItems() {
        Invoice f = new Invoice();
        f.addItem(bd("0.10"), 1);
        f.addItem(bd("0.20"), 1);
        f.addItem(bd("0.70"), 1);
        assertAmount("1.00", f.net(), "Netto");
    }

    @Test
    @DisplayName("3) 1000 pozycji po 0.01 zł to dokładnie 10.00 zł netto")
    void manySmallItems() {
        Invoice f = new Invoice();
        for (int i = 0; i < 1000; i++) {
            f.addItem(bd("0.01"), 1);
        }
        assertAmount("10.00", f.net(), "Netto");
    }

    @Test
    @DisplayName("4) Brutto 1.50 zł + 23% VAT = 1.845, czyli 1.85 zł")
    void grossRoundsHalfUp() {
        Invoice f = new Invoice();
        f.addItem(bd("1.50"), 1);
        BigDecimal gross = f.gross(bd("0.23"));
        assertAmount("1.85", gross, "Brutto");
        assertEquals(2, gross.scale(), "Brutto ma mieć 2 miejsca po przecinku, jest " + gross);
    }

    @Test
    @DisplayName("5) Brutto 10.00 zł + 23% VAT = 12.30 zł")
    void grossSimple() {
        Invoice f = new Invoice();
        f.addItem(bd("2.50"), 4);
        BigDecimal gross = f.gross(bd("0.23"));
        assertAmount("12.30", gross, "Brutto");
        assertEquals(2, gross.scale(), "Brutto ma mieć 2 miejsca po przecinku, jest " + gross);
    }

    @Test
    @DisplayName("6) Zapłata 0.3 zł za fakturę na 0.30 zł to zapłata dokładna")
    void paidExactlyIgnoresScale() {
        Invoice f = new Invoice();
        f.addItem(bd("0.10"), 3);
        assertTrue(f.isPaidExactly(bd("0.3")), "0.3 zł pokrywa 0.30 zł");
        assertTrue(f.isPaidExactly(bd("0.300")), "0.300 zł pokrywa 0.30 zł");
        assertFalse(f.isPaidExactly(bd("0.29")), "0.29 zł nie pokrywa 0.30 zł");
    }
}
