package pl.kurs.basics;

/**
 * Item in the shop. The same name can have several variants with different prices
 * (e.g. "Kawa" 250 g and "Kawa" 1 kg).
 */
public record Item(String name, double price) {
}
