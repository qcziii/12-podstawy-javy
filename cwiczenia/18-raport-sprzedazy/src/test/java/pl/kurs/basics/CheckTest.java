package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    private static final List<Sale> SALES = List.of(
            new Sale("Północ", "Ala", "Kawa", 3, 2000),      // 6000
            new Sale("Południe", "Bartek", "Herbata", 10, 500), // 5000
            new Sale("Północ", "Celina", "Kawa", 1, 2000),   // 2000
            new Sale("Centrum", "Ala", "Kakao", 4, 1500),    // 6000
            new Sale("Południe", "Ala", "Herbata", 2, 500),  // 1000
            new Sale("Północ", "Ala", "Cukier", 4, 300));    // 1200

    private final SalesReport report = new SalesReport(SALES);
    private final SalesReport empty = new SalesReport(List.of());

    @Test
    @DisplayName("1) totalRevenue: 212,00 zł; pusty raport: 0")
    void totalRevenue() {
        assertEquals(21200, report.totalRevenue());
        assertEquals(0, empty.totalRevenue());
    }

    @Test
    @DisplayName("2) revenueByRegion: {Centrum=6000, Południe=6000, Północ=9200}, alfabetycznie")
    void revenueByRegion() {
        Map<String, Long> byRegion = report.revenueByRegion();
        assertEquals(Map.of("Centrum", 6000L, "Południe", 6000L, "Północ", 9200L), byRegion);
        assertEquals(List.of("Centrum", "Południe", "Północ"), new ArrayList<>(byRegion.keySet()), "Kolejność regionów");
    }

    @Test
    @DisplayName("3) topProducts(3): Herbata (12), Cukier (4), Kakao (4); Kawa (4) poza podium")
    void topProducts() {
        assertEquals(List.of("Herbata", "Cukier", "Kakao"), report.topProducts(3));
        assertEquals(List.of("Herbata", "Cukier", "Kakao", "Kawa"), report.topProducts(10));
        assertEquals(List.of(), empty.topProducts(3));
    }

    @Test
    @DisplayName("4) sellersIn(\"Północ\"): [Ala, Celina]; nieznany region: []")
    void sellersIn() {
        assertEquals(List.of("Ala", "Celina"), report.sellersIn("Północ"));
        assertEquals(List.of(), report.sellersIn("Wschód"));
    }

    @Test
    @DisplayName("5) bestSeller: Ala; pusty raport: Optional.empty()")
    void bestSeller() {
        assertEquals(Optional.of("Ala"), report.bestSeller());
        assertEquals(Optional.empty(), empty.bestSeller());
    }

    @Test
    @DisplayName("6) averageQuantity: 4.0; pusty raport: 0.0")
    void averageQuantity() {
        assertEquals(4.0, report.averageQuantity(), 1e-9);
        assertEquals(0.0, empty.averageQuantity(), 1e-9);
    }

    @Test
    @DisplayName("7) productsByRegion: Północ -> [Kawa, Kawa, Cukier]")
    void productsByRegion() {
        Map<String, List<String>> byRegion = report.productsByRegion();
        assertEquals(List.of("Kawa", "Kawa", "Cukier"), byRegion.get("Północ"));
        assertEquals(List.of("Herbata", "Herbata"), byRegion.get("Południe"));
        assertEquals(3, byRegion.size());
    }

    @Test
    @DisplayName("8) bigSales(5000): [Ala: Kawa x 3, Bartek: Herbata x 10, Ala: Kakao x 4]")
    void bigSales() {
        assertEquals(List.of("Ala: Kawa x 3", "Bartek: Herbata x 10", "Ala: Kakao x 4"), report.bigSales(5000));
        assertEquals(List.of(), report.bigSales(1_000_000));
    }
}
