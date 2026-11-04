package pl.kurs.basics;

/**
 * Two values of any types.
 */
public class Pair {

    private final Object first;
    private final Object second;

    private Pair(Object first, Object second) {
        this.first = first;
        this.second = second;
    }

    public static Pair of(Object first, Object second) {
        return new Pair(first, second);
    }

    public Object first() {
        return first;
    }

    public Object second() {
        return second;
    }

    /** The same pair in reverse order: ("Ala", 30) -> (30, "Ala"). */
    public Pair swap() {
        return new Pair(second, first);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
