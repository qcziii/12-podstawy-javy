package pl.kurs.basics;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Notes stored in a text file, one note per line, UTF-8 (the same files are opened by other programs).
 */
public class NotesFile {

    /** Saves the notes, overwriting the file. */
    public static void save(Path file, List<String> notes) throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file.toFile()), StandardCharsets.ISO_8859_1));
        for (String note : notes) {
            writer.write(note);
            writer.newLine();
        }
    }

    /** Reads all notes. File does not exist -> NoSuchFileException. */
    public static List<String> load(Path file) throws IOException {
        return Files.readAllLines(file, StandardCharsets.ISO_8859_1);
    }

    /** Notes that contain the text (ignoring letter case). */
    public static List<String> search(Path file, String text) throws IOException {
        return load(file).stream()
                .filter(note -> note.toLowerCase().contains(text.toLowerCase()))
                .toList();
    }
}
