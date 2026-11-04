package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    private static Product p(String sku, String name, long price) {
        return new Product(sku, name, price);
    }

    @Test
    @DisplayName("1) Ten sam SKU to ten sam produkt: equals(Object) i hashCode")
    void equalsAndHashCode() {
        Object a = p("SKU-1", "Kawa", 2999);
        Object b = p("SKU-1", "Kawa ziarnista", 3199);
        assertTrue(a.equals(b), "equals(Object) dla tego samego SKU");
        assertEquals(a.hashCode(), b.hashCode(), "hashCode dla tego samego SKU");
        assertFalse(a.equals(p("SKU-2", "Kawa", 2999)), "Inny SKU to inny produkt");
    }

    @Test
    @DisplayName("2) equals: null i obiekt innej klasy to false, bez wyjątku")
    void equalsNullAndOtherType() {
        Product a = p("SKU-1", "Kawa", 2999);
        assertFalse(a.equals((Object) null), "equals(null)");
        assertFalse(a.equals((Object) "SKU-1"), "equals(String)");
        assertTrue(a.equals((Object) a), "equals(samego siebie)");
    }

    @Test
    @DisplayName("3) unique: 3 importy SKU-1 i 1 import SKU-2 to 2 produkty")
    void uniqueRemovesDuplicates() {
        Set<Product> unique = CatalogImport.unique(List.of(
                p("SKU-1", "Kawa", 2999), p("SKU-2", "Herbata", 1599), p("SKU-1", "Kawa", 2999),
                p("SKU-1", "Kawa (nowa cena)", 3199)));
        assertEquals(2, unique.size(), "Unikalne produkty: " + unique);
    }

    @Test
    @DisplayName("4) newProducts: produkt, który już jest w katalogu, nie jest nowy")
    void newProductsUsesContains() {
        List<Product> existing = List.of(p("SKU-1", "Kawa", 2999));
        List<Product> imported = List.of(p("SKU-1", "Kawa", 3199), p("SKU-3", "Kakao", 899));
        assertEquals("[SKU-3 Kakao]", CatalogImport.newProducts(existing, imported).toString());
    }

    @Test
    @DisplayName("5) stock: 3 sztuki SKU-1 w dostawie to jeden wpis z liczbą 3")
    void stockCountsPerProduct() {
        Map<Product, Integer> stock = CatalogImport.stock(List.of(
                p("SKU-1", "Kawa", 2999), p("SKU-1", "Kawa", 2999), p("SKU-2", "Herbata", 1599),
                p("SKU-1", "Kawa", 2999)));
        assertEquals(2, stock.size(), "Wpisy w stanie magazynu: " + stock);
        assertEquals(3, stock.get(p("SKU-1", "?", 0)), "Liczba sztuk SKU-1");
    }

    @Test
    @DisplayName("6) hashCode jest stały i spójny z equals dla wielu obiektów")
    void hashCodeConsistency() {
        Set<Product> set = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            set.add(p("SKU-" + (i % 10), "Produkt " + i, i));
        }
        assertEquals(10, set.size());
        assertTrue(set.contains(p("SKU-7", "inna nazwa", 1)));
    }
}
