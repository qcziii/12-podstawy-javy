package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    @Test
    @DisplayName("1) parse(\"1.2.3\") daje 1, 2, 3")
    void parseSimple() {
        Version v = Version.parse("1.2.3");
        assertEquals(1, v.major());
        assertEquals(2, v.minor());
        assertEquals(3, v.patch());
        assertEquals("1.2.3", v.toString());
    }

    @Test
    @DisplayName("2) parse(\"10.20.300\") daje 10, 20, 300")
    void parseMultiDigit() {
        Version v = Version.parse("10.20.300");
        assertEquals(10, v.major());
        assertEquals(20, v.minor());
        assertEquals(300, v.patch());
    }

    @Test
    @DisplayName("3) parse odrzuca \"1.2\", \"1.2.3.4\", \"1.x.3\", \"\" (IllegalArgumentException)")
    void parseInvalid() {
        for (String s : List.of("1.2", "1.2.3.4", "1.x.3", "", "1..3")) {
            assertThrows(IllegalArgumentException.class, () -> Version.parse(s), "Wersja \"" + s + "\" powinna być odrzucona");
        }
    }

    @Test
    @DisplayName("4) 1.9.0 jest starsza od 1.10.0")
    void nineBeforeTen() {
        assertTrue(Version.parse("1.9.0").compareTo(Version.parse("1.10.0")) < 0, "1.9.0 < 1.10.0");
        assertTrue(Version.parse("1.10.0").compareTo(Version.parse("1.9.0")) > 0, "1.10.0 > 1.9.0");
    }

    @Test
    @DisplayName("5) 2.0.0 jest starsza od 10.0.0")
    void twoBeforeTen() {
        assertTrue(Version.parse("2.0.0").compareTo(Version.parse("10.0.0")) < 0, "2.0.0 < 10.0.0");
    }

    @Test
    @DisplayName("6) Sortowanie: 1.2.10, 1.10.0, 1.2.9, 0.9.9, 2.0.0")
    void sorting() {
        List<Version> list = new ArrayList<>();
        for (String s : List.of("1.2.10", "1.10.0", "1.2.9", "0.9.9", "2.0.0")) {
            list.add(Version.parse(s));
        }
        Collections.sort(list);
        assertEquals("[0.9.9, 1.2.9, 1.2.10, 1.10.0, 2.0.0]", list.toString());
    }

    @Test
    @DisplayName("7) Ta sama wersja: compareTo == 0")
    void sameVersion() {
        assertEquals(0, Version.parse("3.4.5").compareTo(Version.parse("3.4.5")));
    }
}
