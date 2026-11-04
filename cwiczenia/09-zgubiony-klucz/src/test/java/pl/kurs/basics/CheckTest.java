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
    @DisplayName("1) Rejestracja: klient jest w rejestrze, drugi raz ten sam e-mail się nie rejestruje")
    void register() {
        CustomerRegistry r = new CustomerRegistry();
        assertTrue(r.register(new Customer("ala@example.com", "Ala")));
        assertFalse(r.register(new Customer("ala@example.com", "Ala Nowak")), "Ten sam e-mail drugi raz");
        assertTrue(r.isRegistered(new Customer("ala@example.com", "?")));
        assertEquals(1, r.count());
    }

    @Test
    @DisplayName("2) Po zmianie e-maila klient dalej jest w rejestrze")
    void stillRegisteredAfterChange() {
        CustomerRegistry r = new CustomerRegistry();
        Customer ala = new Customer("ala@example.com", "Ala");
        r.register(ala);
        r.changeEmail(ala, "ala.nowak@example.com");
        assertEquals("ala.nowak@example.com", ala.email());
        assertTrue(r.isRegistered(ala), "Klient zniknął z rejestru po zmianie e-maila");
        assertTrue(r.isRegistered(new Customer("ala.nowak@example.com", "?")), "Szukanie po nowym e-mailu");
        assertFalse(r.isRegistered(new Customer("ala@example.com", "?")), "Stary e-mail nie powinien być w rejestrze");
    }

    @Test
    @DisplayName("3) Po zmianie e-maila klienta da się usunąć")
    void removeAfterChange() {
        CustomerRegistry r = new CustomerRegistry();
        Customer ala = new Customer("ala@example.com", "Ala");
        r.register(ala);
        r.changeEmail(ala, "ala.nowak@example.com");
        assertTrue(r.remove(ala), "remove zwrócił false");
        assertEquals(0, r.count(), "Po usunięciu rejestr powinien być pusty");
    }

    @Test
    @DisplayName("4) Po zmianie e-maila nie da się zarejestrować drugiego klienta z tym samym nowym e-mailem")
    void noDuplicateAfterChange() {
        CustomerRegistry r = new CustomerRegistry();
        Customer ala = new Customer("ala@example.com", "Ala");
        r.register(ala);
        r.changeEmail(ala, "ala.nowak@example.com");
        assertFalse(r.register(new Customer("ala.nowak@example.com", "Ala (duplikat)")), "Duplikat zarejestrowany");
        assertEquals(1, r.count());
    }

    @Test
    @DisplayName("5) Zmiana e-maila na zajęty przez innego klienta to IllegalArgumentException, nic się nie zmienia")
    void changeToTakenEmail() {
        CustomerRegistry r = new CustomerRegistry();
        Customer ala = new Customer("ala@example.com", "Ala");
        Customer bartek = new Customer("bartek@example.com", "Bartek");
        r.register(ala);
        r.register(bartek);
        assertThrows(IllegalArgumentException.class, () -> r.changeEmail(ala, "bartek@example.com"));
        assertEquals("ala@example.com", ala.email(), "E-mail nie powinien się zmienić");
        assertEquals(2, r.count());
        assertTrue(r.isRegistered(ala));
    }

    @Test
    @DisplayName("6) Zmiana e-maila klienta spoza rejestru to IllegalArgumentException")
    void changeUnknownCustomer() {
        CustomerRegistry r = new CustomerRegistry();
        Customer obcy = new Customer("obcy@example.com", "Obcy");
        assertThrows(IllegalArgumentException.class, () -> r.changeEmail(obcy, "nowy@example.com"));
        assertEquals("obcy@example.com", obcy.email());
    }
}
