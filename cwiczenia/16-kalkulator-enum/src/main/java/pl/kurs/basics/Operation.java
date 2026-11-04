package pl.kurs.basics;

/**
 * Arithmetic operation with its symbol. Every constant computes its own result.
 */
public enum Operation {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    // TODO: field for the symbol

    Operation(String symbol) {
        // TODO
    }

    public String symbol() {
        throw new UnsupportedOperationException("TODO");
    }

    /** Result of the operation, e.g. ADD.apply(2, 3) == 5. Dividing by 0 -> ArithmeticException. */
    public long apply(long a, long b) {
        throw new UnsupportedOperationException("TODO");
    }

    /** Operation for the symbol, e.g. "+" -> ADD. Unknown symbol -> IllegalArgumentException with the symbol in the message. */
    public static Operation fromSymbol(String symbol) {
        throw new UnsupportedOperationException("TODO");
    }
}
