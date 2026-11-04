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

    private final AccessChecker checker = new AccessChecker();

    /** Simulates a value read from a token or JSON: a new String object, not a literal. */
    private static String fromToken(String s) {
        return new StringBuilder(s).toString();
    }

    @Test
    @DisplayName("1) Właściciel o id 7 jest właścicielem swojego dokumentu")
    void smallId() {
        assertTrue(checker.isOwner(new Document(1L, 7L, "Umowa"), new User(7L, "USER")));
    }

    @Test
    @DisplayName("2) Właściciel o id 1000 jest właścicielem swojego dokumentu")
    void largeId() {
        assertTrue(checker.isOwner(new Document(1L, 1000L, "Umowa"), new User(1000L, "USER")),
                "Użytkownik 1000 nie został uznany za właściciela dokumentu z ownerId = 1000");
    }

    @Test
    @DisplayName("3) Obcy użytkownik nie jest właścicielem")
    void otherUser() {
        assertFalse(checker.isOwner(new Document(1L, 1000L, "Umowa"), new User(1001L, "USER")));
    }

    @Test
    @DisplayName("4) Dokument bez właściciela (ownerId = null): nikt nie jest właścicielem, bez wyjątku")
    void nullOwner() {
        assertFalse(checker.isOwner(new Document(1L, null, "Import"), new User(1000L, "USER")));
    }

    @Test
    @DisplayName("5) Rola ADMIN odczytana z tokenu daje uprawnienia administratora")
    void adminFromToken() {
        assertTrue(checker.isAdmin(new User(5L, fromToken("ADMIN"))),
                "Rola \"ADMIN\" odczytana z tokenu nie została rozpoznana");
        assertFalse(checker.isAdmin(new User(5L, fromToken("USER"))));
    }

    @Test
    @DisplayName("6) Użytkownik bez roli (role = null) nie jest administratorem, bez wyjątku")
    void nullRole() {
        assertFalse(checker.isAdmin(new User(5L, null)));
    }

    @Test
    @DisplayName("7) canEdit: właściciel 5000 i administrator mogą, inni nie")
    void canEdit() {
        Document doc = new Document(1L, 5000L, "Regulamin");
        assertTrue(checker.canEdit(doc, new User(5000L, fromToken("USER"))), "Właściciel");
        assertTrue(checker.canEdit(doc, new User(1L, fromToken("ADMIN"))), "Administrator");
        assertFalse(checker.canEdit(doc, new User(2L, fromToken("USER"))), "Obcy");
    }
}
