package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    @TempDir
    Path dir;

    private static final List<String> NOTES = List.of("Kupić mleko", "Zażółć gęślą jaźń", "Spotkanie o 15:00");

    @Test
    @DisplayName("1) save: plik po zapisie nie jest pusty")
    void saveWritesSomething() throws Exception {
        Path file = dir.resolve("notatki.txt");
        NotesFile.save(file, List.of("Kupić mleko"));
        assertTrue(Files.size(file) > 0, "Plik po zapisie ma 0 bajtów");
    }

    @Test
    @DisplayName("2) save + load: te same notatki, z polskimi znakami")
    void roundTrip() throws Exception {
        Path file = dir.resolve("notatki.txt");
        NotesFile.save(file, NOTES);
        assertEquals(NOTES, NotesFile.load(file));
    }

    @Test
    @DisplayName("3) save zapisuje w UTF-8 (plik czytany przez inny program)")
    void savesUtf8() throws Exception {
        Path file = dir.resolve("notatki.txt");
        NotesFile.save(file, List.of("Zażółć gęślą jaźń"));
        assertEquals("Zażółć gęślą jaźń", Files.readString(file, StandardCharsets.UTF_8).strip());
    }

    @Test
    @DisplayName("4) load czyta plik UTF-8 zapisany przez inny program")
    void loadsUtf8() throws Exception {
        Path file = dir.resolve("z-telefonu.txt");
        Files.writeString(file, "Łódź\nŻurek i pierogi\n", StandardCharsets.UTF_8);
        assertEquals(List.of("Łódź", "Żurek i pierogi"), NotesFile.load(file));
    }

    @Test
    @DisplayName("5) save nadpisuje plik: drugi zapis zastępuje pierwszy")
    void overwrite() throws Exception {
        Path file = dir.resolve("notatki.txt");
        NotesFile.save(file, NOTES);
        NotesFile.save(file, List.of("Tylko to"));
        assertEquals(List.of("Tylko to"), NotesFile.load(file));
    }

    @Test
    @DisplayName("6) search(\"ŻÓŁĆ\") znajduje \"Zażółć gęślą jaźń\"")
    void search() throws Exception {
        Path file = dir.resolve("notatki.txt");
        NotesFile.save(file, NOTES);
        assertEquals(List.of("Zażółć gęślą jaźń"), NotesFile.search(file, "ŻÓŁĆ"));
    }

    @Test
    @DisplayName("7) load nieistniejącego pliku to NoSuchFileException")
    void missingFile() {
        assertThrows(NoSuchFileException.class, () -> NotesFile.load(dir.resolve("nie-ma.txt")));
    }
}
