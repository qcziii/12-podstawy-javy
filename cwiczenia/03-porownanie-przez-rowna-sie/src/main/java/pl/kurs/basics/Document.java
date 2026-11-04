package pl.kurs.basics;

/** Document from the database. ownerId can be null (document imported without an owner). */
public record Document(Long id, Long ownerId, String title) {
}
