package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    @Test
    @DisplayName("1) Jeden koszyk: ma swoje produkty i właściciela")
    void singleCart() {
        Cart c = new Cart("Ala");
        c.add("mleko");
        c.add("chleb");
        assertEquals(List.of("mleko", "chleb"), c.products());
        assertEquals("Ala", c.owner());
    }

    @Test
    @DisplayName("2) Nowy koszyk jest pusty, nawet gdy inny koszyk ma produkty")
    void newCartIsEmpty() {
        Cart a = new Cart("Ala");
        a.add("mleko");
        Cart b = new Cart("Bartek");
        assertEquals(List.of(), b.products(), "Koszyk Bartka zaraz po utworzeniu");
    }

    @Test
    @DisplayName("3) Dwa koszyki: każdy ma tylko swoje produkty")
    void twoCartsSeparate() {
        Cart a = new Cart("Ala");
        Cart b = new Cart("Bartek");
        a.add("mleko");
        b.add("piwo");
        a.add("chleb");
        assertEquals(List.of("mleko", "chleb"), a.products(), "Koszyk Ali");
        assertEquals(List.of("piwo"), b.products(), "Koszyk Bartka");
    }

    @Test
    @DisplayName("4) Dwa koszyki: każdy ma swojego właściciela")
    void twoOwners() {
        Cart a = new Cart("Ala");
        Cart b = new Cart("Bartek");
        assertEquals("Ala", a.owner(), "Właściciel pierwszego koszyka");
        assertEquals("Bartek", b.owner(), "Właściciel drugiego koszyka");
    }

    @Test
    @DisplayName("5) Numery koszyków są kolejne i unikalne, createdCount() liczy wszystkie")
    void ids() {
        int before = Cart.createdCount();
        Cart a = new Cart("Ala");
        Cart b = new Cart("Bartek");
        Cart c = new Cart("Celina");
        assertEquals(before + 1, a.id());
        assertEquals(before + 2, b.id());
        assertEquals(before + 3, c.id());
        assertEquals(before + 3, Cart.createdCount());
    }
}
