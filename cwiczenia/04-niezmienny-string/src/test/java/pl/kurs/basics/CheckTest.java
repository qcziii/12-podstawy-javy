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
    @DisplayName("1) login(\"Jan Kowalski\") to \"jan_kowalski\"")
    void loginSimple() {
        assertEquals("jan_kowalski", NameFormatter.login("Jan Kowalski"));
    }

    @Test
    @DisplayName("2) login(\"  Anna NOWAK \") to \"anna_nowak\" (spacje na brzegach znikają)")
    void loginTrim() {
        assertEquals("anna_nowak", NameFormatter.login("  Anna NOWAK "));
    }

    @Test
    @DisplayName("3) login(\"Łucja Żółć\") to \"łucja_żółć\" (polskie litery)")
    void loginPolish() {
        assertEquals("łucja_żółć", NameFormatter.login("Łucja Żółć"));
    }

    @Test
    @DisplayName("4) initials(\"jan kowalski\") to \"JK\"")
    void initialsSimple() {
        assertEquals("JK", NameFormatter.initials("jan kowalski"));
    }

    @Test
    @DisplayName("5) initials(\"Anna Maria Nowak\") to \"AMN\"")
    void initialsThree() {
        assertEquals("AMN", NameFormatter.initials("Anna Maria Nowak"));
    }

    @Test
    @DisplayName("6) initials(\"  jan    kowalski \") to \"JK\" (kilka spacji)")
    void initialsManySpaces() {
        assertEquals("JK", NameFormatter.initials("  jan    kowalski "));
    }
}
