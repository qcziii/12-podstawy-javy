package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static pl.kurs.basics.EquipmentType.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    private RentalService shop;

    @BeforeEach
    void setUp() {
        shop = new RentalService();
        shop.addEquipment(new Equipment("R-02", BIKE));
        shop.addEquipment(new Equipment("R-01", BIKE));
        shop.addEquipment(new Equipment("K-01", KAYAK));
        shop.addEquipment(new Equipment("R-03", BIKE));
    }

    private static List<String> ids(List<Equipment> list) {
        List<String> ids = new ArrayList<>();
        list.forEach(e -> ids.add(e.id()));
        return ids;
    }

    @Test
    @DisplayName("01) addEquipment: ten sam numer drugi raz to IllegalArgumentException")
    void duplicateId() {
        assertThrows(IllegalArgumentException.class, () -> shop.addEquipment(new Equipment("R-01", SKIS)));
        assertEquals(List.of(), shop.available(SKIS), "Duplikat nie powinien trafić do wypożyczalni");
    }

    @Test
    @DisplayName("02) available: wolne rowery posortowane po numerze")
    void availableSorted() {
        assertEquals(List.of("R-01", "R-02", "R-03"), ids(shop.available(BIKE)));
        assertEquals(List.of(), shop.available(SKIS));
    }

    @Test
    @DisplayName("03) rent: wypożyczony sprzęt znika z wolnych, rentalOf go pokazuje")
    void rent() {
        Rental r = shop.rent("R-02", "Ala", 3);
        assertEquals(new Rental("R-02", "Ala", 3), r);
        assertEquals(List.of("R-01", "R-03"), ids(shop.available(BIKE)));
        assertEquals(Optional.of(r), shop.rentalOf("R-02"));
        assertEquals(Optional.empty(), shop.rentalOf("R-01"));
    }

    @Test
    @DisplayName("04) rent: nieznany numer to EquipmentNotFoundException")
    void rentUnknown() {
        assertThrows(EquipmentNotFoundException.class, () -> shop.rent("X-99", "Ala", 1));
    }

    @Test
    @DisplayName("05) rent: już wypożyczony to IllegalStateException, wypożyczenie się nie zmienia")
    void rentTwice() {
        shop.rent("K-01", "Ala", 2);
        assertThrows(IllegalStateException.class, () -> shop.rent("K-01", "Bartek", 1));
        assertEquals("Ala", shop.rentalOf("K-01").map(Rental::customer).orElse("?"));
    }

    @Test
    @DisplayName("06) rent: liczba dni spoza 1..30 to IllegalArgumentException, sprzęt zostaje wolny")
    void rentDays() {
        assertThrows(IllegalArgumentException.class, () -> shop.rent("R-01", "Ala", 0));
        assertThrows(IllegalArgumentException.class, () -> shop.rent("R-01", "Ala", 31));
        assertDoesNotThrow(() -> shop.rent("R-02", "Ala", 30));
        assertEquals(List.of("R-01", "R-03"), ids(shop.available(BIKE)));
    }

    @Test
    @DisplayName("07) returnEquipment: rower na 3 dni to 90,00 zł; sprzęt wraca do wolnych")
    void returnShort() {
        shop.rent("R-01", "Ala", 3);
        assertEquals(9000, shop.returnEquipment("R-01"));
        assertEquals(List.of("R-01", "R-02", "R-03"), ids(shop.available(BIKE)));
        assertEquals(Optional.empty(), shop.rentalOf("R-01"));
    }

    @Test
    @DisplayName("08) returnEquipment: kajak na 7 dni to 350,00 zł - 10% = 315,00 zł; 6 dni bez rabatu")
    void returnLong() {
        shop.rent("K-01", "Ala", 7);
        assertEquals(31500, shop.returnEquipment("K-01"));
        shop.rent("K-01", "Ala", 6);
        assertEquals(30000, shop.returnEquipment("K-01"));
    }

    @Test
    @DisplayName("09) returnEquipment: niewypożyczony to IllegalStateException, nieznany to EquipmentNotFoundException")
    void returnErrors() {
        assertThrows(IllegalStateException.class, () -> shop.returnEquipment("R-01"));
        assertThrows(EquipmentNotFoundException.class, () -> shop.returnEquipment("X-99"));
    }

    @Test
    @DisplayName("10) revenue: suma zwrotów (aktywne wypożyczenia się nie liczą)")
    void revenue() {
        assertEquals(0, shop.revenue());
        shop.rent("R-01", "Ala", 3);
        shop.rent("K-01", "Bartek", 7);
        shop.returnEquipment("R-01");
        assertEquals(9000, shop.revenue());
        shop.returnEquipment("K-01");
        assertEquals(40500, shop.revenue());
    }

    @Test
    @DisplayName("11) activeRentalsByCustomer: {Ala=2, Bartek=1}, alfabetycznie")
    void byCustomer() {
        shop.rent("R-03", "Bartek", 1);
        shop.rent("R-01", "Ala", 1);
        shop.rent("K-01", "Ala", 1);
        Map<String, Long> m = shop.activeRentalsByCustomer();
        assertEquals(Map.of("Ala", 2L, "Bartek", 1L), m);
        assertEquals(List.of("Ala", "Bartek"), new ArrayList<>(m.keySet()));
        shop.returnEquipment("R-03");
        assertEquals(Map.of("Ala", 2L), shop.activeRentalsByCustomer());
    }

    @Test
    @DisplayName("12) availableCountByType: {BIKE=2, KAYAK=1, SKIS=0} w kolejności stałych")
    void countByType() {
        shop.rent("R-01", "Ala", 1);
        Map<EquipmentType, Long> m = shop.availableCountByType();
        assertEquals(Map.of(BIKE, 2L, KAYAK, 1L, SKIS, 0L), m);
        assertEquals(List.of(BIKE, KAYAK, SKIS), new ArrayList<>(m.keySet()));
    }
}
