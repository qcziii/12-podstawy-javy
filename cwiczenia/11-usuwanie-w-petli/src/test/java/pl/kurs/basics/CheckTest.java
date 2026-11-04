package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    @Test
    @DisplayName("1) Lista startowa i dodanie produktu")
    void add() {
        ShoppingList list = new ShoppingList("mleko", "chleb");
        list.add("masło");
        assertEquals(List.of("mleko", "chleb", "masło"), list.items());
    }

    @Test
    @DisplayName("2) Pusta lista na start i dodanie produktu")
    void addToEmpty() {
        ShoppingList list = new ShoppingList();
        list.add("jajka");
        assertEquals(List.of("jajka"), list.items());
    }

    @Test
    @DisplayName("3) Odhaczenie dwóch produktów z początku listy")
    void tickOffFirst() {
        ShoppingList list = new ShoppingList("mleko", "chleb", "masło", "jajka");
        list.tickOff(Set.of("mleko", "chleb"));
        assertEquals(List.of("masło", "jajka"), list.items());
    }

    @Test
    @DisplayName("4) Odhaczenie dwóch ostatnich produktów (oba znikają)")
    void tickOffLastTwo() {
        ShoppingList list = new ShoppingList("mleko", "chleb", "masło");
        list.tickOff(Set.of("chleb", "masło"));
        assertEquals(List.of("mleko"), list.items());
    }

    @Test
    @DisplayName("5) Odhaczenie wszystkiego, także po dodaniu produktów")
    void tickOffAll() {
        ShoppingList list = new ShoppingList("mleko");
        list.add("chleb");
        list.add("mleko");
        list.tickOff(Set.of("mleko", "chleb"));
        assertEquals(List.of(), list.items());
    }

    @Test
    @DisplayName("6) Lista startowa nie zmienia się, gdy ktoś zmieni tablicę przekazaną do konstruktora")
    void constructorCopiesArray() {
        String[] start = {"mleko", "chleb"};
        ShoppingList list = new ShoppingList(start);
        start[0] = "piwo";
        assertEquals(List.of("mleko", "chleb"), list.items());
    }
}
