package pl.kurs.basics;

/**
 * Application version in the form MAJOR.MINOR.PATCH, e.g. "1.10.2".
 */
public final class Version implements Comparable<Version> {

    private final int major;
    private final int minor;
    private final int patch;

    private Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    /**
     * Parses "1.10.2". Anything else than three non-negative numbers separated by dots
     * ends with IllegalArgumentException.
     */
    public static Version parse(String text) {
        String[] parts = text.split(".");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Niepoprawna wersja: " + text);
        }
        try {
            int major = Integer.parseInt(parts[0]);
            int minor = Integer.parseInt(parts[1]);
            int patch = Integer.parseInt(parts[2]);
            if (major < 0 || minor < 0 || patch < 0) {
                throw new IllegalArgumentException("Niepoprawna wersja: " + text);
            }
            return new Version(major, minor, patch);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Niepoprawna wersja: " + text, e);
        }
    }

    public int major() {
        return major;
    }

    public int minor() {
        return minor;
    }

    public int patch() {
        return patch;
    }

    /** Older version is "smaller": 1.9.0 < 1.10.0 < 2.0.0. */
    @Override
    public int compareTo(Version other) {
        return toString().compareTo(other.toString());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Version v && major == v.major && minor == v.minor && patch == v.patch;
    }

    @Override
    public int hashCode() {
        return 31 * (31 * major + minor) + patch;
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }
}
