package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    @Test
    @DisplayName("01) Rabat 10%: 100,00 zł -> 90,00 zł, 0,99 zł -> 0,90 zł (rabat w dół do grosza)")
    void percentage() {
        Discount d = new PercentageDiscount(10);
        assertEquals(9000, d.apply(10000));
        assertEquals(90, d.apply(99), "10% z 99 gr to 9,9 gr, rabat zaokrąglamy w dół do 9 gr");
        assertEquals(0, d.apply(0));
    }

    @Test
    @DisplayName("02) Rabat 0% i 100%: cena bez zmian i 0")
    void percentageEdges() {
        assertEquals(1234, new PercentageDiscount(0).apply(1234));
        assertEquals(0, new PercentageDiscount(100).apply(1234));
    }

    @Test
    @DisplayName("03) Rabat procentowy spoza 0..100 to IllegalArgumentException w konstruktorze")
    void percentageInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new PercentageDiscount(-1));
        assertThrows(IllegalArgumentException.class, () -> new PercentageDiscount(101));
    }

    @Test
    @DisplayName("04) Rabat kwotowy 5,00 zł: 12,00 zł -> 7,00 zł, 3,00 zł -> 0 (nie poniżej zera)")
    void fixed() {
        Discount d = new FixedAmountDiscount(500);
        assertEquals(700, d.apply(1200));
        assertEquals(0, d.apply(300), "Cena po rabacie nie może być ujemna");
    }

    @Test
    @DisplayName("05) Rabat kwotowy ujemny to IllegalArgumentException")
    void fixedInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountDiscount(-1));
    }

    @Test
    @DisplayName("06) Opisy: \"Rabat 10%\", \"Rabat 5,00 zł\", \"Rabat 0,05 zł\", \"Rabat 12,30 zł\"")
    void descriptions() {
        assertEquals("Rabat 10%", new PercentageDiscount(10).description());
        assertEquals("Rabat 5,00 zł", new FixedAmountDiscount(500).description());
        assertEquals("Rabat 0,05 zł", new FixedAmountDiscount(5).description());
        assertEquals("Rabat 12,30 zł", new FixedAmountDiscount(1230).description());
    }

    @Test
    @DisplayName("07) Rabat łączony: najpierw 10%, potem 5,00 zł: 100,00 zł -> 85,00 zł")
    void combinedOrder() {
        Discount d = new CombinedDiscount(List.of(new PercentageDiscount(10), new FixedAmountDiscount(500)));
        assertEquals(8500, d.apply(10000));
        Discount reversed = new CombinedDiscount(List.of(new FixedAmountDiscount(500), new PercentageDiscount(10)));
        assertEquals(8550, reversed.apply(10000), "Kolejność odwrotna: (100 - 5) - 10%");
    }

    @Test
    @DisplayName("08) Rabat łączony: opis \"Rabat 10% + Rabat 5,00 zł\", pusta lista nic nie zmienia")
    void combinedDescription() {
        Discount d = new CombinedDiscount(List.of(new PercentageDiscount(10), new FixedAmountDiscount(500)));
        assertEquals("Rabat 10% + Rabat 5,00 zł", d.description());
        Discount none = new CombinedDiscount(List.of());
        assertEquals(1234, none.apply(1234));
    }

    @Test
    @DisplayName("09) Rabat łączony nie zmienia się, gdy ktoś potem zmieni listę przekazaną do konstruktora")
    void combinedCopiesList() {
        List<Discount> list = new ArrayList<>();
        list.add(new PercentageDiscount(10));
        Discount d = new CombinedDiscount(list);
        list.add(new FixedAmountDiscount(9999));
        assertEquals(9000, d.apply(10000));
    }

    @Test
    @DisplayName("10) Każdy własny rabat działa z kasą (klasa anonimowa)")
    void customDiscount() {
        Discount halfPrice = new Discount() {
            @Override
            public long apply(long price) {
                return price / 2;
            }

            @Override
            public String description() {
                return "Pół ceny";
            }
        };
        assertEquals(1500, Checkout.total(List.of(1000L, 2000L), halfPrice));
        assertEquals(3000, Checkout.total(List.of(1000L, 2000L), new CombinedDiscount(List.of())));
    }

    @Test
    @DisplayName("11) Kasa: suma 30,00 + 20,00 zł z rabatem 10% to 45,00 zł; podsumowanie na paragonie")
    void checkout() {
        List<Long> prices = List.of(3000L, 2000L);
        assertEquals(4500, Checkout.total(prices, new PercentageDiscount(10)));
        assertEquals("Do zapłaty: 45,00 zł (Rabat 10%)", Checkout.summary(prices, new PercentageDiscount(10)));
        assertEquals("Do zapłaty: 0,07 zł (Rabat 0,00 zł)", Checkout.summary(List.of(7L), new FixedAmountDiscount(0)));
    }
}
