package pl.kurs.basics;

import java.io.BufferedReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Imports a price list in CSV format:
 * <pre>
 * sku;price
 * KAWA-250;19.99
 * HERBATA;7.50
 * </pre>
 * The first line is a header. Empty lines are skipped.
 */
public class PriceListImporter {

    public List<PriceEntry> importFrom(Reader source) {
        try {
            BufferedReader reader = new BufferedReader(source);
            List<PriceEntry> result = new ArrayList<>();
            reader.readLine(); // header
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(";");
                result.add(new PriceEntry(parts[0].trim(), new BigDecimal(parts[1].trim())));
            }
            return result;
        } catch (Exception e) {
            System.out.println("Błąd importu: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
