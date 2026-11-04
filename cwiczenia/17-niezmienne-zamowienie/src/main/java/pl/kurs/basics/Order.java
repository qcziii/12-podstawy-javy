package pl.kurs.basics;

import java.util.List;

/**
 * Order. Should be immutable: once created, nobody can change its lines.
 * number not blank, lines not null (can be empty).
 */
public record Order(String number, List<OrderLine> lines) {

    public long total() {
        long sum = 0;
        for (OrderLine line : lines) {
            sum += line.total();
        }
        return sum;
    }

    /** A new order with one more line. This order does not change. */
    public Order withLine(OrderLine line) {
        lines.add(line);
        return this;
    }
}
