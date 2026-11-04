package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    static class Fruit {
        final int weight;

        Fruit(int weight) {
            this.weight = weight;
        }
    }

    static class Apple extends Fruit {
        final String variety;

        Apple(String variety, int weight) {
            super(weight);
            this.variety = variety;
        }
    }

    @Test
    @DisplayName("1) max zwraca typ elementu: Integer z listy Integerów, String z listy Stringów")
    void maxReturnsElementType() {
        Integer max = CollectionUtils.max(List.of(3, 7, 5), Comparator.naturalOrder());
        assertEquals(7, max);
        String longest = CollectionUtils.max(Set.of("a", "ccc", "bb"), Comparator.comparingInt(String::length));
        assertEquals("ccc", longest);
    }

    @Test
    @DisplayName("2) max: jabłka porównane komparatorem dla wszystkich owoców (Comparator<Fruit>)")
    void maxWithSuperComparator() {
        Comparator<Fruit> byWeight = Comparator.comparingInt(f -> f.weight);
        List<Apple> apples = List.of(new Apple("Ligol", 180), new Apple("Szampion", 210), new Apple("Gala", 150));
        Apple heaviest = CollectionUtils.max(apples, byWeight);
        assertEquals("Szampion", heaviest.variety);
    }

    @Test
    @DisplayName("3) max z pustej kolekcji to NoSuchElementException")
    void maxEmpty() {
        assertThrows(NoSuchElementException.class, () -> CollectionUtils.max(List.<Integer>of(), Comparator.naturalOrder()));
    }

    @Test
    @DisplayName("4) copyAll: Integery i Double do jednej List<Number>")
    void copyAllToSuperType() {
        List<Number> target = new ArrayList<>();
        CollectionUtils.copyAll(List.of(1, 2), target);
        CollectionUtils.copyAll(List.of(2.5), target);
        assertEquals(List.of(1, 2, 2.5), target);
    }

    @Test
    @DisplayName("5) sum działa dla List<Integer>, List<Double> i List<Number>")
    void sumOfSubtypes() {
        List<Integer> ints = List.of(1, 2, 3);
        List<Double> doubles = List.of(1.5, 2.5);
        List<Number> mixed = List.of(1, 2.5, 10L);
        assertEquals(6.0, CollectionUtils.sum(ints), 1e-9);
        assertEquals(4.0, CollectionUtils.sum(doubles), 1e-9);
        assertEquals(13.5, CollectionUtils.sum(mixed), 1e-9);
    }

    @Test
    @DisplayName("6) invert: Map<String, Integer> -> Map<Integer, String>")
    void invert() {
        Map<Integer, String> inverted = CollectionUtils.invert(Map.of("jeden", 1, "dwa", 2));
        assertEquals(Map.of(1, "jeden", 2, "dwa"), inverted);
        String one = inverted.get(1);
        assertEquals("jeden", one);
    }

    @Test
    @DisplayName("7) Pair<String, Integer>: first() to String, second() to Integer, bez rzutowania")
    void pairTypes() {
        Pair<String, Integer> p = Pair.of("Ala", 30);
        String name = p.first();
        int age = p.second();
        assertEquals("Ala", name);
        assertEquals(30, age);
    }

    @Test
    @DisplayName("8) Pair.swap(): Pair<String, Integer> -> Pair<Integer, String>")
    void pairSwap() {
        Pair<Integer, String> swapped = Pair.of("Ala", 30).swap();
        int age = swapped.first();
        String name = swapped.second();
        assertEquals(30, age);
        assertEquals("Ala", name);
        assertEquals("(30, Ala)", swapped.toString());
    }
}
