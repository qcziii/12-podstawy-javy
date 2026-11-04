package pl.kurs.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(10)
class CheckTest {

    @Test
    @DisplayName("1) apply: 7+3=10, 7-3=4, 7*3=21, 7/3=2")
    void apply() {
        assertEquals(10, Operation.ADD.apply(7, 3));
        assertEquals(4, Operation.SUBTRACT.apply(7, 3));
        assertEquals(21, Operation.MULTIPLY.apply(7, 3));
        assertEquals(2, Operation.DIVIDE.apply(7, 3));
    }

    @Test
    @DisplayName("2) Dzielenie przez zero to ArithmeticException")
    void divideByZero() {
        assertThrows(ArithmeticException.class, () -> Operation.DIVIDE.apply(1, 0));
    }

    @Test
    @DisplayName("3) symbol() i fromSymbol(): \"+\" <-> ADD dla wszystkich operacji")
    void symbols() {
        assertEquals("+", Operation.ADD.symbol());
        assertEquals("/", Operation.DIVIDE.symbol());
        for (Operation op : Operation.values()) {
            assertSame(op, Operation.fromSymbol(op.symbol()), "fromSymbol(" + op.symbol() + ")");
        }
    }

    @Test
    @DisplayName("4) fromSymbol(\"%\") to IllegalArgumentException z symbolem w komunikacie")
    void unknownSymbol() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Operation.fromSymbol("%"));
        assertNotNull(e.getMessage());
        assertTrue(e.getMessage().contains("%"), "Komunikat: " + e.getMessage());
    }

    @Test
    @DisplayName("5) evaluate: \"12 * 3\" = 36, \"100   -  1\" = 99, \"-5 + 2\" = -3")
    void evaluate() {
        Calculator c = new Calculator();
        assertEquals(36, c.evaluate("12 * 3"));
        assertEquals(99, c.evaluate("100   -  1"));
        assertEquals(-3, c.evaluate("-5 + 2"));
        assertEquals(5, c.evaluate("  10 / 2 "));
    }

    @Test
    @DisplayName("6) evaluate: zły format to IllegalArgumentException")
    void evaluateInvalid() {
        Calculator c = new Calculator();
        for (String s : List.of("12*3", "1 +", "a + 1", "1 + 2 + 3", "", "2 ^ 3")) {
            assertThrows(IllegalArgumentException.class, () -> c.evaluate(s), "Wyrażenie \"" + s + "\"");
        }
    }

    @Test
    @DisplayName("7) usage: liczy użycia operacji, w kolejności stałych w Operation")
    void usage() {
        Calculator c = new Calculator();
        c.evaluate("1 * 2");
        c.evaluate("1 + 2");
        c.evaluate("3 * 4");
        c.evaluate("5 * 6");
        Map<Operation, Integer> usage = c.usage();
        assertEquals(Map.of(Operation.ADD, 1, Operation.MULTIPLY, 3), usage);
        assertEquals(List.of(Operation.ADD, Operation.MULTIPLY), new ArrayList<>(usage.keySet()), "Kolejność");
    }

    @Test
    @DisplayName("8) usage: nieudane wyrażenie się nie liczy, każdy kalkulator liczy osobno")
    void usageOnlySuccessful() {
        Calculator a = new Calculator();
        Calculator b = new Calculator();
        a.evaluate("1 + 1");
        assertThrows(IllegalArgumentException.class, () -> a.evaluate("1 % 1"));
        assertEquals(Map.of(Operation.ADD, 1), a.usage());
        assertEquals(Map.of(), b.usage());
    }
}
