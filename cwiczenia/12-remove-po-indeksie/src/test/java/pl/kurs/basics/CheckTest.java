package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    private static Playlist playlist(int... ids) {
        Playlist p = new Playlist();
        for (int id : ids) {
            p.add(id);
        }
        return p;
    }

    @Test
    @DisplayName("1) removeTrack(20) usuwa utwór o id 20")
    void removeLargeId() {
        Playlist p = playlist(10, 20, 30);
        assertTrue(p.removeTrack(20));
        assertEquals(List.of(10, 30), p.trackIds());
    }

    @Test
    @DisplayName("2) removeTrack(0) usuwa utwór o id 0, a nie pierwszy na liście")
    void removeSmallId() {
        Playlist p = playlist(3, 0, 2);
        assertTrue(p.removeTrack(0));
        assertEquals(List.of(3, 2), p.trackIds());
    }

    @Test
    @DisplayName("3) removeTrack usuwa tylko pierwsze wystąpienie; nieznany utwór: false")
    void removeFirstOccurrence() {
        Playlist p = playlist(1, 2, 1, 3);
        assertTrue(p.removeTrack(1));
        assertEquals(List.of(2, 1, 3), p.trackIds());
        assertFalse(p.removeTrack(99));
        assertEquals(List.of(2, 1, 3), p.trackIds());
    }

    @Test
    @DisplayName("4) removeAt(1) usuwa drugi utwór na liście")
    void removeAt() {
        Playlist p = playlist(7, 8, 9);
        p.removeAt(1);
        assertEquals(List.of(7, 9), p.trackIds());
    }

    @Test
    @DisplayName("5) Listy z trackIds() nie da się użyć do zmiany playlisty")
    void noLeak() {
        Playlist p = playlist(1, 2, 3);
        List<Integer> ids = p.trackIds();
        try {
            ids.add(99);
            ids.clear();
        } catch (UnsupportedOperationException expected) {
            // also fine: the returned list is read-only
        }
        assertEquals(List.of(1, 2, 3), p.trackIds(), "Playlista zmieniła się przez listę zwróconą z trackIds()");
    }
}
