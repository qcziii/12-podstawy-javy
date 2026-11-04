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

    private static List<String> names(Iterable<Item> items) {
        List<String> result = new ArrayList<>();
        for (Item i : items) {
            result.add(i.name() + " " + i.price());
        }
        return result;
    }

    @Test
    @DisplayName("1) byPrice: ceny różniące się o złotówki są dobrze posortowane")
    void byPriceWholeNumbers() {
        List<Item> sorted = ItemSorting.byPrice(List.of(new Item("C", 30.0), new Item("A", 10.0), new Item("B", 20.0)));
        assertEquals(List.of("A 10.0", "B 20.0", "C 30.0"), names(sorted));
    }

    @Test
    @DisplayName("2) byPrice: ceny różniące się o grosze (9.99, 9.49, 9.50) też są dobrze posortowane")
    void byPriceCents() {
        List<Item> sorted = ItemSorting.byPrice(List.of(new Item("Kawa", 9.99), new Item("Herbata", 9.49), new Item("Kakao", 9.50)));
        assertEquals(List.of("Herbata 9.49", "Kakao 9.5", "Kawa 9.99"), names(sorted));
    }

    @Test
    @DisplayName("3) byPrice: ta sama cena: alfabetycznie po nazwie")
    void byPriceTies() {
        List<Item> sorted = ItemSorting.byPrice(List.of(new Item("Cukier", 4.0), new Item("Ananas", 4.0), new Item("Banan", 4.0), new Item("Mąka", 3.0)));
        assertEquals(List.of("Mąka 3.0", "Ananas 4.0", "Banan 4.0", "Cukier 4.0"), names(sorted));
    }

    @Test
    @DisplayName("4) byPrice: nie zmienia listy wejściowej")
    void byPriceDoesNotModifyInput() {
        List<Item> input = new ArrayList<>(List.of(new Item("B", 2.0), new Item("A", 1.0)));
        ItemSorting.byPrice(input);
        assertEquals(List.of("B 2.0", "A 1.0"), names(input));
    }

    @Test
    @DisplayName("5) catalog: dwa warianty \"Kawa\" w różnych cenach to dwie pozycje")
    void catalogKeepsVariants() {
        List<Item> items = List.of(new Item("Kawa", 39.99), new Item("Herbata", 12.0), new Item("Kawa", 11.49));
        assertEquals(List.of("Herbata 12.0", "Kawa 11.49", "Kawa 39.99"), names(ItemSorting.catalog(items)));
    }

    @Test
    @DisplayName("6) catalog: identyczne pozycje są raz")
    void catalogRemovesIdentical() {
        List<Item> items = List.of(new Item("Kawa", 11.49), new Item("Kawa", 11.49), new Item("Cukier", 4.0));
        assertEquals(List.of("Cukier 4.0", "Kawa 11.49"), names(ItemSorting.catalog(items)));
    }
}
