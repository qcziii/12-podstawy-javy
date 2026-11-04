package pl.kurs.basics;

/**
 * User account. email and nickname are optional (can be null).
 */
public record UserAccount(String login, String nickname, String email) {
}
