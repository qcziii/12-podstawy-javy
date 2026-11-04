package pl.kurs.basics;

/** Logged-in user. role comes from the token, e.g. "ADMIN" or "USER". */
public record User(Long id, String role) {
}
