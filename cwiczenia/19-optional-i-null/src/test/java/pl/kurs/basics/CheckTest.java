package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    private AvatarStore store;
    private UserDirectory dir;

    @BeforeEach
    void setUp() {
        store = new AvatarStore();
        dir = new UserDirectory(store);
        dir.add(new UserAccount("ala", "Ala z Krakowa", "ala@example.com"));
        dir.add(new UserAccount("bartek", null, "bartek@poczta.pl"));
        dir.add(new UserAccount("celina", "Cela", null));
        dir.setAvatar("ala", "ala.png");
    }

    @Test
    @DisplayName("1) find: znany użytkownik to Optional z kontem, nieznany to Optional.empty() (nie null)")
    void find() {
        assertEquals("ala", dir.find("ala").map(UserAccount::login).orElse("?"));
        Optional<UserAccount> none = dir.find("nikt");
        assertNotNull(none, "find nigdy nie powinno zwracać null");
        assertTrue(none.isEmpty());
    }

    @Test
    @DisplayName("2) displayName: pseudonim, gdy jest")
    void displayNameNickname() {
        assertEquals("Ala z Krakowa", dir.displayName("ala"));
    }

    @Test
    @DisplayName("3) displayName: login, gdy nie ma pseudonimu")
    void displayNameLogin() {
        assertEquals("bartek", dir.displayName("bartek"));
    }

    @Test
    @DisplayName("4) displayName: nieznany użytkownik to \"Nieznany użytkownik\"")
    void displayNameUnknown() {
        assertEquals("Nieznany użytkownik", dir.displayName("nikt"));
    }

    @Test
    @DisplayName("5) emailDomain: example.com; brak e-maila albo użytkownika: Optional.empty()")
    void emailDomain() {
        assertEquals(Optional.of("example.com"), dir.emailDomain("ala"));
        assertEquals(Optional.empty(), dir.emailDomain("celina"), "Celina nie ma e-maila");
        assertEquals(Optional.empty(), dir.emailDomain("nikt"), "Nieznany użytkownik");
    }

    @Test
    @DisplayName("6) avatar: własny plik, gdy jest, i wtedy domyślny avatar nie jest pobierany")
    void ownAvatarDoesNotLoadDefault() {
        assertEquals("ala.png", dir.avatar("ala"));
        assertEquals(0, store.defaultLoads(), "Domyślny avatar pobrany niepotrzebnie");
    }

    @Test
    @DisplayName("7) avatar: domyślny, gdy nie ma własnego (pobrany raz na wywołanie)")
    void defaultAvatar() {
        assertEquals("default.png", dir.avatar("bartek"));
        assertEquals(1, store.defaultLoads());
    }
}
