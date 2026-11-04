# 10. Enumy i rekordy

Ćwiczenia: [16](../cwiczenia/16-kalkulator-enum/ZADANIE.md), [17](../cwiczenia/17-niezmienne-zamowienie/ZADANIE.md), [21](../cwiczenia/21-projekt-wypozyczalnia/ZADANIE.md)

## Enum

Typ z ustaloną, zamkniętą listą wartości:

```java
public enum OrderStatus {
    NEW, PAID, SHIPPED, CANCELLED
}

OrderStatus s = OrderStatus.PAID;
if (s == OrderStatus.PAID) { ... }      // enumy porównujemy przez ==
```

Zamiast stałych `String` (`"PAID"`, literówka `"PIAD"` przejdzie) albo `int` (`2` niczego nie mówi). Kompilator pilnuje, że wartość jest jedną z dozwolonych.

Każda stała to jedyny obiekt swojego typu (singleton), dlatego `==` jest poprawne i bezpieczne dla `null`.

### Pola, konstruktor, metody

```java
public enum EquipmentType {
    BIKE(3000),
    KAYAK(5000);

    private final long dailyPrice;

    EquipmentType(long dailyPrice) {     // konstruktor zawsze prywatny
        this.dailyPrice = dailyPrice;
    }

    public long dailyPrice() {
        return dailyPrice;
    }
}
```

### Zachowanie zależne od stałej

Zamiast `switch` w każdym miejscu użycia, każda stała może mieć własną implementację:

```java
public enum Operation {
    ADD("+") {
        @Override
        public long apply(long a, long b) { return a + b; }
    },
    MULTIPLY("*") {
        @Override
        public long apply(long a, long b) { return a * b; }
    };

    private final String symbol;

    Operation(String symbol) { this.symbol = symbol; }

    public abstract long apply(long a, long b);
}
```

Albo przez pole z lambdą: `ADD("+", (a, b) -> a + b)` i pole `private final LongBinaryOperator op;`.

### Metody wbudowane

```java
Operation.values();               // tablica wszystkich stałych, w kolejności deklaracji
Operation.valueOf("ADD");         // ADD; dla "add" albo "+" -> IllegalArgumentException
op.name();                        // "ADD"
op.ordinal();                     // 0: pozycja w deklaracji
```

**Nie zapisuj `ordinal()` do bazy ani pliku.** Dodanie stałej w środku albo zmiana kolejności zmienia numery wszystkich następnych i stare dane zaczynają znaczyć co innego. Zapisuj `name()` albo własny kod (pole).

### EnumMap, EnumSet

```java
Map<Operation, Integer> usage = new EnumMap<>(Operation.class);   // kolejność jak stałe
Set<Day> weekend = EnumSet.of(Day.SATURDAY, Day.SUNDAY);
```

### switch na enumie (Java 14+)

```java
String label = switch (status) {
    case NEW -> "Nowe";
    case PAID, SHIPPED -> "W realizacji";
    case CANCELLED -> "Anulowane";
};   // wyrażenie switch na enumie bez default: kompilator wymaga obsługi wszystkich stałych
```

## Rekordy (Java 16+)

Klasa, której jedynym celem jest przenoszenie danych:

```java
public record OrderLine(String product, int quantity, long unitPrice) {
}
```

Kompilator generuje sam:

- pola `private final` dla każdego komponentu,
- konstruktor kanoniczny (wszystkie komponenty w tej kolejności),
- akcesory `product()`, `quantity()`, `unitPrice()` (bez `get`),
- `equals` i `hashCode` po wszystkich komponentach,
- `toString`: `OrderLine[product=Kawa, quantity=2, unitPrice=1999]`.

Rekord jest `final`, nie może dziedziczyć po klasie (może implementować interfejsy), nie może mieć dodatkowych pól instancji (statyczne może). Może mieć metody.

### Kompaktowy konstruktor: walidacja i kopie

```java
public record Order(String number, List<OrderLine> lines) {

    public Order {                                  // bez listy parametrów
        Objects.requireNonNull(number, "number");
        if (number.isBlank()) {
            throw new IllegalArgumentException("Numer zamówienia nie może być pusty");
        }
        lines = List.copyOf(lines);                 // przypisanie do PARAMETRU
    }                                               // pola przypisane automatycznie na końcu
}
```

W kompaktowym konstruktorze nie piszemy `this.lines = ...`: przypisujemy do parametru, a kompilator na końcu przepisuje parametry do pól.

### Rekord to nie gwarancja niemutowalności

Pola są `final`, ale jeśli komponent jest mutowalny (`List`, tablica, `Date`), obiekt w środku da się zmienić:

```java
List<OrderLine> lines = new ArrayList<>();
Order order = new Order("Z/1", lines);
lines.add(...);              // bez kopii w konstruktorze: zamówienie się zmienia
order.lines().add(...);      // akcesor zwraca to samo pole
```

Dlatego kopia defensywna (`List.copyOf`) w kompaktowym konstruktorze. Komponent-tablica dodatkowo psuje `equals` (tablice porównywane są przez referencję).

„Zmiana” rekordu to nowy rekord:

```java
public Order withLine(OrderLine line) {
    List<OrderLine> newLines = new ArrayList<>(lines);
    newLines.add(line);
    return new Order(number, newLines);
}
```

## sealed i pattern matching (Java 17-21)

Zamknięta hierarchia: tylko wymienione klasy mogą implementować interfejs.

```java
public sealed interface Shape permits Circle, Square {}
public record Circle(double r) implements Shape {}
public record Square(double side) implements Shape {}

double area = switch (shape) {
    case Circle c -> Math.PI * c.r() * c.r();
    case Square s -> s.side() * s.side();
};   // bez default: kompilator wie, że to wszystkie przypadki
```

To alternatywa dla polimorfizmu (metody `area()` w każdej klasie), gdy operacji jest dużo, a typy się nie zmieniają.

## Typowe błędy

- Stałe `String`/`int` zamiast enuma.
- Zapisywanie `ordinal()`.
- `valueOf(tekstOdUżytkownika)` bez obsługi `IllegalArgumentException`.
- Rekord z listą bez kopii defensywnej.
- `this.x = ...` w kompaktowym konstruktorze (błąd kompilacji).

## Pytania sprawdzające

1. Dlaczego enumy można porównywać przez `==`?
2. Co jest złego w zapisywaniu `ordinal()` do bazy?
3. Co generuje kompilator dla rekordu?
4. Czy rekord z polem `List<String>` jest niemutowalny? Jak to naprawić?
5. Czym się różni kompaktowy konstruktor od kanonicznego?
6. Co zwraca `Operation.valueOf("+")`?
