package pl.kurs.basics;

import java.util.Map;

/**
 * Calculator for expressions of the form "number operator number", e.g. "12 * 3".
 * Remembers how many times each operation was used.
 */
public class Calculator {

    // TODO: fields

    /**
     * Computes "a op b". Spaces around the operator are required, there may be more than one.
     * Wrong format -> IllegalArgumentException.
     */
    public long evaluate(String expression) {
        throw new UnsupportedOperationException("TODO");
    }

    /** How many times each operation was used, in the order of the constants in Operation. Unused ones are not in the map. */
    public Map<Operation, Integer> usage() {
        throw new UnsupportedOperationException("TODO");
    }
}
