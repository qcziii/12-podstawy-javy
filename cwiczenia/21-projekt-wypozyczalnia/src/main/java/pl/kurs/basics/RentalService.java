package pl.kurs.basics;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Sports equipment rental shop. See ZADANIE.md for all the rules.
 */
public class RentalService {

    // TODO: fields

    /** Adds equipment to the shop. Id already used -> IllegalArgumentException. */
    public void addEquipment(Equipment equipment) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Rents the equipment.
     * Unknown id -> EquipmentNotFoundException, already rented -> IllegalStateException,
     * days outside 1..30 -> IllegalArgumentException.
     */
    public Rental rent(String equipmentId, String customer, int days) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Returns the equipment and gives the cost: days * daily price, 10% less (rounded down to a grosz)
     * for 7 days or more. Unknown id -> EquipmentNotFoundException, not rented -> IllegalStateException.
     */
    public long returnEquipment(String equipmentId) {
        throw new UnsupportedOperationException("TODO");
    }

    /** The active rental of the equipment, if it is rented. */
    public Optional<Rental> rentalOf(String equipmentId) {
        throw new UnsupportedOperationException("TODO");
    }

    /** Equipment of the type that is not rented now, sorted by id. */
    public List<Equipment> available(EquipmentType type) {
        throw new UnsupportedOperationException("TODO");
    }

    /** Sum of the costs of all returns so far. */
    public long revenue() {
        throw new UnsupportedOperationException("TODO");
    }

    /** Number of active rentals per customer, customers alphabetically. */
    public Map<String, Long> activeRentalsByCustomer() {
        throw new UnsupportedOperationException("TODO");
    }

    /** Number of available pieces of every type (also 0), in the order of EquipmentType constants. */
    public Map<EquipmentType, Long> availableCountByType() {
        throw new UnsupportedOperationException("TODO");
    }
}
