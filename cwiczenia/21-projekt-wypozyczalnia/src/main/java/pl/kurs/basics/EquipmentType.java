package pl.kurs.basics;

/**
 * Type of equipment with the price per day in grosze.
 */
public enum EquipmentType {
    BIKE(3000),
    KAYAK(5000),
    SKIS(4000);

    private final long dailyPrice;

    EquipmentType(long dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public long dailyPrice() {
        return dailyPrice;
    }
}
