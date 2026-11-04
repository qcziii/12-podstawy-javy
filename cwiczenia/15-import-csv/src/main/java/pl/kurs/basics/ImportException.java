package pl.kurs.basics;

/**
 * The price list could not be imported. Checked exception: the caller must decide what to do
 * (show the error to the user, try another file...).
 */
public class ImportException extends Exception {

    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
