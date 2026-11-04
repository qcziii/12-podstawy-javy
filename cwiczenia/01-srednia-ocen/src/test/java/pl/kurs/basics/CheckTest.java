package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    @Test
    @DisplayName("1) Średnia z [4, 5] to 4.5")
    void averageOfTwo() {
        assertEquals(4.5, CourseStats.average(new int[]{4, 5}), 1e-9, "Średnia z [4, 5]");
    }

    @Test
    @DisplayName("2) Średnia z [3, 4, 4] to 3.666...")
    void averageOfThree() {
        assertEquals(11.0 / 3, CourseStats.average(new int[]{3, 4, 4}), 1e-9, "Średnia z [3, 4, 4]");
    }

    @Test
    @DisplayName("3) Średnia z pustej tablicy to 0.0, z [5] to 5.0")
    void averageEdgeCases() {
        assertEquals(0.0, CourseStats.average(new int[0]), 1e-9);
        assertEquals(5.0, CourseStats.average(new int[]{5}), 1e-9);
    }

    @Test
    @DisplayName("4) Czas oglądania: małe liczby")
    void smallTotals() {
        assertEquals(6_000, CourseStats.totalWatchMillis(new int[]{1, 2, 3}));
        assertEquals(0, CourseStats.totalWatchMillis(new int[0]));
    }

    @Test
    @DisplayName("5) Czas oglądania: 5 filmów po 1 000 000 s to 5 000 000 000 ms")
    void sumOverflow() {
        int[] videos = {1_000_000, 1_000_000, 1_000_000, 1_000_000, 1_000_000};
        assertEquals(5_000_000_000L, CourseStats.totalWatchMillis(videos), "Suma w milisekundach");
    }

    @Test
    @DisplayName("6) Czas oglądania: jeden film z 3 000 000 s to 3 000 000 000 ms")
    void singleValueOverflow() {
        assertEquals(3_000_000_000L, CourseStats.totalWatchMillis(new int[]{3_000_000}), "Jeden film w milisekundach");
    }
}
