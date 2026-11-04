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

    private static final OrderLine COFFEE = new OrderLine("Kawa", 2, 1999);
    private static final OrderLine TEA = new OrderLine("Herbata", 1, 750);

    @Test
    @DisplayName("1) total: 2 x 19,99 zł + 1 x 7,50 zł = 47,48 zł")
    void total() {
        assertEquals(4748, new Order("Z/1", List.of(COFFEE, TEA)).total());
        assertEquals(0, new Order("Z/2", List.of()).total());
    }

    @Test
    @DisplayName("2) Zmiana listy przekazanej do konstruktora nie zmienia zamówienia")
    void copiesInputList() {
        List<OrderLine> lines = new ArrayList<>(List.of(COFFEE));
        Order order = new Order("Z/1", lines);
        lines.add(TEA);
        lines.set(0, new OrderLine("Kawior", 100, 99999));
        assertEquals(List.of(COFFEE), order.lines());
    }

    @Test
    @DisplayName("3) Listy z lines() nie da się zmienić")
    void linesReadOnly() {
        Order order = new Order("Z/1", new ArrayList<>(List.of(COFFEE)));
        assertThrows(UnsupportedOperationException.class, () -> order.lines().add(TEA));
        assertEquals(List.of(COFFEE), order.lines());
    }

    @Test
    @DisplayName("4) withLine zwraca nowe zamówienie, stare się nie zmienia")
    void withLine() {
        Order order = new Order("Z/1", List.of(COFFEE));
        Order bigger = order.withLine(TEA);
        assertEquals(List.of(COFFEE), order.lines(), "Stare zamówienie");
        assertEquals(List.of(COFFEE, TEA), bigger.lines(), "Nowe zamówienie");
        assertEquals("Z/1", bigger.number());
        assertNotSame(order, bigger);
    }

    @Test
    @DisplayName("5) Walidacja zamówienia: pusty/blank numer to IllegalArgumentException, null to NullPointerException")
    void orderValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Order("  ", List.of()));
        assertThrows(NullPointerException.class, () -> new Order(null, List.of()));
        assertThrows(NullPointerException.class, () -> new Order("Z/1", null));
    }

    @Test
    @DisplayName("6) Walidacja pozycji: ilość < 1, cena < 0, pusty produkt to IllegalArgumentException")
    void lineValidation() {
        assertThrows(IllegalArgumentException.class, () -> new OrderLine("Kawa", 0, 100));
        assertThrows(IllegalArgumentException.class, () -> new OrderLine("Kawa", 1, -1));
        assertThrows(IllegalArgumentException.class, () -> new OrderLine(" ", 1, 100));
        assertDoesNotThrow(() -> new OrderLine("Gratis", 1, 0));
    }

    @Test
    @DisplayName("7) Rekordy z tymi samymi danymi są równe (equals, hashCode)")
    void recordEquality() {
        Order a = new Order("Z/1", new ArrayList<>(List.of(COFFEE, TEA)));
        Order b = new Order("Z/1", List.of(new OrderLine("Kawa", 2, 1999), new OrderLine("Herbata", 1, 750)));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
