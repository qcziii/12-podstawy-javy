package pl.kurs.basics;

/** An active rental: who has the equipment and for how many days. */
public record Rental(String equipmentId, String customer, int days) {
}
