package pl.kurs.basics;

/**
 * Formatting user names in the registration form.
 */
public class NameFormatter {

    /**
     * Login from a full name: no spaces at the start and end, lower case, space replaced by '_'.
     * "  Jan Kowalski " -> "jan_kowalski"
     */
    public static String login(String fullName) {
        fullName.trim();
        fullName.toLowerCase();
        fullName.replace(' ', '_');
        return fullName;
    }

    /**
     * Initials, upper case: "jan kowalski" -> "JK", "Anna Maria Nowak" -> "AMN".
     * Words can be separated by several spaces, and there can be spaces at the start and end.
     */
    public static String initials(String fullName) {
        String result = "";
        for (String word : fullName.split(" ")) {
            result.concat(word.substring(0, 1).toUpperCase());
        }
        return result;
    }
}
