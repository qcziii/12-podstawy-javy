# 09. Wyjątki

Ćwiczenia: [15](../cwiczenia/15-import-csv/ZADANIE.md), [20](../cwiczenia/20-plik-z-polskimi-znakami/ZADANIE.md), [21](../cwiczenia/21-projekt-wypozyczalnia/ZADANIE.md)

## Hierarchia

```
Throwable
├── Error                      błędy JVM: OutOfMemoryError, StackOverflowError. Nie łapiemy.
└── Exception                  checked: kompilator wymaga obsługi
    ├── IOException            (FileNotFoundException, NoSuchFileException...)
    ├── ...
    └── RuntimeException       unchecked: kompilator nie wymaga obsługi
        ├── NullPointerException
        ├── IllegalArgumentException   (NumberFormatException)
        ├── IllegalStateException
        ├── IndexOutOfBoundsException
        ├── ArithmeticException
        ├── ClassCastException
        ├── UnsupportedOperationException
        └── ConcurrentModificationException
```

- **Checked** (`Exception`, ale nie `RuntimeException`): metoda musi je złapać albo zadeklarować `throws`. Sytuacje, na które wołający może sensownie zareagować i których nie da się wykluczyć z góry: brak pliku, zerwane połączenie, zły format pliku od użytkownika.
- **Unchecked** (`RuntimeException`): zwykle **błąd programisty** albo złamanie kontraktu metody: `null` tam, gdzie nie wolno, zły argument, wywołanie w złym stanie. Naprawia się kod, a nie łapie wyjątek.

## Rzucanie

```java
public void withdraw(long amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Kwota musi być dodatnia, jest: " + amount);
    }
    if (amount > balance) {
        throw new IllegalStateException("Za mało środków: saldo " + balance + ", wypłata " + amount);
    }
    balance -= amount;
}
```

Najczęściej używane wbudowane:

| Wyjątek | Kiedy |
|---|---|
| `IllegalArgumentException` | zły argument (ujemna kwota, ocena 7) |
| `IllegalStateException` | obiekt w złym stanie do tej operacji (zwrot niewypożyczonego sprzętu) |
| `NullPointerException` | `null` tam, gdzie nie wolno: `Objects.requireNonNull(x, "x")` |
| `UnsupportedOperationException` | operacja nie jest obsługiwana (niemodyfikowalna lista, `TODO`) |
| `NoSuchElementException` | nie ma elementu (pusta kolekcja, `Optional.get()`) |

**Komunikat ma pomagać**: co było nie tak i z jaką wartością. „Błąd” albo `null` jako komunikat nie pomagają nikomu.

## Łapanie

```java
try {
    int n = Integer.parseInt(text);
    ...
} catch (NumberFormatException e) {
    throw new ImportException("Zła liczba w linii " + lineNumber + ": " + text, e);
} finally {
    // wykonuje się zawsze: po sukcesie, po wyjątku, po return
}
```

- Kilka `catch`: od najbardziej szczegółowego do najogólniejszego. Wspólna obsługa: `catch (IOException | NumberFormatException e)`.
- Łap tylko to, co umiesz obsłużyć. Pozostałe niech lecą wyżej.

### Nie połykaj wyjątków

```java
try {
    ...
} catch (Exception e) {
    System.out.println("Błąd: " + e.getMessage());
    return new ArrayList<>();          // wołający myśli, że wszystko się udało i plik był pusty
}
```

To najgorszy wzorzec: błąd znika, program działa dalej z błędnymi danymi, a w logach jest jedna linia bez stack trace. Przynajmniej jedno z poniższych:

1. nie łap (niech wyjątek leci wyżej),
2. przetłumacz na wyjątek z poziomu twojej warstwy i **przekaż przyczynę**: `throw new ImportException("...", e);`,
3. naprawdę obsłuż (ponów, użyj wartości domyślnej, która jest poprawna w tej sytuacji) i zaloguj z wyjątkiem: `log.warn("...", e)`.

`catch (Exception e)` łapie też `NullPointerException` i inne błędy programisty, więc ukrywa bugi.

### Przyczyna (cause)

```java
throw new ImportException("Błąd w linii 3", e);
```

Stack trace pokaże oba wyjątki:

```
pl.kurs.basics.ImportException: Błąd w linii 3: HERBATA;siedem
    at ...
Caused by: java.lang.NumberFormatException: Character s is neither a decimal digit number...
    at ...
```

Czytaj stack trace od góry (co się stało i gdzie), potem `Caused by` (dlaczego). Pierwsza linia z **twojego** pakietu to zwykle miejsce do obejrzenia.

## Własne wyjątki

```java
public class ImportException extends Exception {             // checked
    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class EquipmentNotFoundException extends RuntimeException {   // unchecked
    public EquipmentNotFoundException(String id) {
        super("Nie ma sprzętu o numerze " + id);
    }
}
```

Własny wyjątek ma sens, gdy wołający ma go łapać osobno albo gdy niesie informację z dziedziny. Zawsze z konstruktorem przyjmującym przyczynę, jeśli będzie opakowywał inne wyjątki.

## try-with-resources

Zasoby (pliki, połączenia, strumienie) trzeba zamknąć, także gdy wystąpi wyjątek. Ręcznie:

```java
BufferedReader reader = new BufferedReader(source);
try {
    ...
} finally {
    reader.close();
}
```

Krócej i bezpieczniej:

```java
try (BufferedReader reader = new BufferedReader(source)) {
    ...
}   // reader.close() wywołane automatycznie, zawsze
```

Działa dla każdej klasy implementującej `AutoCloseable`. Kilka zasobów: `try (var in = ...; var out = ...)`, zamykane w odwrotnej kolejności. Jeśli `close()` też rzuci wyjątek, nie przykryje on oryginalnego: trafi do `e.getSuppressed()`.

Zamknięcie „opakowania” (`BufferedReader`) zamyka też opakowany obiekt (`Reader`).

## Typowe błędy

- `catch (Exception e) { e.printStackTrace(); }` albo pusty `catch`.
- Zwracanie `null` albo pustej listy zamiast zgłoszenia błędu.
- `throw new RuntimeException(e.getMessage())`: zgubiona przyczyna i stack trace. Dobrze: `throw new RuntimeException("kontekst", e)`.
- Zasób zamykany tylko na ścieżce sukcesu.
- Wyjątki do sterowania normalnym przepływem programu (np. `try { parse } catch` zamiast sprawdzenia warunku, gdy jest proste).

## Pytania sprawdzające

1. Czym się różni wyjątek checked od unchecked? Podaj po dwa przykłady.
2. Co jest nie tak z `catch (Exception e) { return List.of(); }`?
3. Po co przekazywać przyczynę do konstruktora wyjątku?
4. Kiedy wykonuje się `finally`?
5. Co robi try-with-resources i dla jakich klas działa?
6. `IllegalArgumentException` czy `IllegalStateException`: wypłata ujemnej kwoty; wypłata z zamkniętego konta?
