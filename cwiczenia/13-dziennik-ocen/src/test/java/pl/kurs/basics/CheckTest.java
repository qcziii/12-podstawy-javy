package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    private GradeBook book;

    @BeforeEach
    void setUp() {
        book = new GradeBook();
        book.addGrade("Zosia", "matematyka", 5);
        book.addGrade("Zosia", "matematyka", 4);
        book.addGrade("Zosia", "biologia", 6);
        book.addGrade("Adam", "matematyka", 3);
        book.addGrade("Adam", "angielski", 4);
        book.addGrade("Zosia", "matematyka", 6);
    }

    @Test
    @DisplayName("1) grades: oceny ucznia z przedmiotu w kolejności dodania")
    void grades() {
        assertEquals(List.of(5, 4, 6), book.grades("Zosia", "matematyka"));
        assertEquals(List.of(3), book.grades("Adam", "matematyka"));
    }

    @Test
    @DisplayName("2) grades: brak ocen (albo nieznany uczeń) to pusta lista, nie null i nie wyjątek")
    void gradesEmpty() {
        assertEquals(List.of(), book.grades("Adam", "biologia"));
        assertEquals(List.of(), book.grades("Nikt", "matematyka"));
    }

    @Test
    @DisplayName("3) addGrade: ocena spoza 1..6 to IllegalArgumentException i nie trafia do dziennika")
    void invalidGrade() {
        assertThrows(IllegalArgumentException.class, () -> book.addGrade("Adam", "matematyka", 0));
        assertThrows(IllegalArgumentException.class, () -> book.addGrade("Adam", "matematyka", 7));
        assertEquals(List.of(3), book.grades("Adam", "matematyka"));
    }

    @Test
    @DisplayName("4) average: Zosia (5, 4, 6, 6) ma 5.25, Adam (3, 4) ma 3.5")
    void average() {
        assertEquals(5.25, book.average("Zosia"), 1e-9);
        assertEquals(3.5, book.average("Adam"), 1e-9);
    }

    @Test
    @DisplayName("5) average: nieznany uczeń to IllegalArgumentException")
    void averageUnknown() {
        assertThrows(IllegalArgumentException.class, () -> book.average("Nikt"));
    }

    @Test
    @DisplayName("6) averagesBySubject: Zosia {biologia=6.0, matematyka=5.0}, alfabetycznie")
    void averagesBySubject() {
        Map<String, Double> avg = book.averagesBySubject("Zosia");
        assertEquals(List.of("biologia", "matematyka"), new ArrayList<>(avg.keySet()), "Kolejność przedmiotów");
        assertEquals(6.0, avg.get("biologia"), 1e-9);
        assertEquals(5.0, avg.get("matematyka"), 1e-9);
        assertEquals(Map.of(), book.averagesBySubject("Nikt"));
    }

    @Test
    @DisplayName("7) students: [Adam, Zosia], alfabetycznie, bez powtórzeń")
    void students() {
        book.addGrade("Bartek", "fizyka", 2);
        assertEquals(List.of("Adam", "Bartek", "Zosia"), book.students());
    }

    @Test
    @DisplayName("8) histogram: {3=1, 4=2, 5=1, 6=2}, rosnąco")
    void histogram() {
        Map<Integer, Integer> h = book.histogram();
        assertEquals(List.of(3, 4, 5, 6), new ArrayList<>(h.keySet()), "Kolejność ocen");
        assertEquals(Map.of(3, 1, 4, 2, 5, 1, 6, 2), h);
    }

    @Test
    @DisplayName("9) Zwrócona lista ocen nie pozwala zmienić dziennika")
    void noLeak() {
        List<Integer> grades = book.grades("Zosia", "matematyka");
        try {
            grades.add(1);
        } catch (UnsupportedOperationException expected) {
            // also fine
        }
        assertEquals(List.of(5, 4, 6), book.grades("Zosia", "matematyka"));
    }
}
