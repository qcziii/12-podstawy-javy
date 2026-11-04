package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    /** StringReader that remembers whether it was closed. */
    static class TrackingReader extends StringReader {
        boolean closed;

        TrackingReader(String s) {
            super(s);
        }

        @Override
        public void close() {
            closed = true;
            super.close();
        }
    }

    /** Reader that fails in the middle of reading, like a broken network drive. */
    static class FailingReader extends Reader {
        boolean closed;
        private final StringReader start = new StringReader("sku;price\nKAWA;10.00\n");

        @Override
        public int read(char[] buf, int off, int len) throws IOException {
            int n = start.read(buf, off, len);
            if (n == -1) {
                throw new IOException("Dysk sieciowy niedostępny");
            }
            return n;
        }

        @Override
        public void close() {
            closed = true;
        }
    }

    private final PriceListImporter importer = new PriceListImporter();

    @Test
    @DisplayName("1) Poprawny plik: wszystkie pozycje, nagłówek i puste linie pominięte")
    void validFile() throws Exception {
        List<PriceEntry> entries = importer.importFrom(new StringReader("sku;price\nKAWA-250;19.99\n\nHERBATA; 7.50 \n"));
        assertEquals(List.of(new PriceEntry("KAWA-250", new BigDecimal("19.99")), new PriceEntry("HERBATA", new BigDecimal("7.50"))), entries);
    }

    @Test
    @DisplayName("2) Poprawny plik: Reader zostaje zamknięty")
    void closesOnSuccess() throws Exception {
        TrackingReader reader = new TrackingReader("sku;price\nKAWA;1.00\n");
        importer.importFrom(reader);
        assertTrue(reader.closed, "Reader nie został zamknięty");
    }

    @Test
    @DisplayName("3) Zła cena w linii 3: ImportException z numerem linii i przyczyną")
    void badPrice() {
        TrackingReader reader = new TrackingReader("sku;price\nKAWA;19.99\nHERBATA;siedem\nKAKAO;3.00\n");
        ImportException e = assertThrows(ImportException.class, () -> importer.importFrom(reader),
                "Błąd w pliku ma kończyć się ImportException, a nie pustą listą");
        assertNotNull(e.getMessage());
        assertTrue(e.getMessage().contains("3"), "Komunikat ma zawierać numer linii (3): " + e.getMessage());
        assertInstanceOf(NumberFormatException.class, e.getCause(), "Przyczyna (getCause) ma być oryginalnym wyjątkiem");
    }

    @Test
    @DisplayName("4) Linia bez średnika (linia 2): ImportException z numerem linii")
    void missingColumn() {
        ImportException e = assertThrows(ImportException.class, () -> importer.importFrom(new StringReader("sku;price\nKAWA 19.99\n")));
        assertTrue(e.getMessage().contains("2"), "Komunikat ma zawierać numer linii (2): " + e.getMessage());
        assertNotNull(e.getCause(), "Przyczyna (getCause)");
    }

    @Test
    @DisplayName("5) Błąd w pliku: Reader i tak zostaje zamknięty")
    void closesOnError() {
        TrackingReader reader = new TrackingReader("sku;price\nKAWA;zero\n");
        assertThrows(ImportException.class, () -> importer.importFrom(reader));
        assertTrue(reader.closed, "Reader nie został zamknięty po błędzie");
    }

    @Test
    @DisplayName("6) Błąd odczytu (IOException): ImportException z przyczyną IOException, Reader zamknięty")
    void ioError() {
        FailingReader reader = new FailingReader();
        ImportException e = assertThrows(ImportException.class, () -> importer.importFrom(reader));
        assertInstanceOf(IOException.class, e.getCause(), "Przyczyna (getCause)");
        assertTrue(reader.closed, "Reader nie został zamknięty");
    }

    @Test
    @DisplayName("7) Ujemna cena (linia 2): ImportException")
    void negativePrice() {
        assertThrows(ImportException.class, () -> importer.importFrom(new StringReader("sku;price\nKAWA;-1.00\n")));
    }
}
