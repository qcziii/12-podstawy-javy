package pl.kurs.basics;

/**
 * There is no equipment with this id in the rental shop.
 */
public class EquipmentNotFoundException extends RuntimeException {

    public EquipmentNotFoundException(String equipmentId) {
        super("Nie ma sprzętu o numerze " + equipmentId);
    }
}
