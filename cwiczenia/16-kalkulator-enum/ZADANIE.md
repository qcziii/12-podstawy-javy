# 16. Kalkulator na enumie

Poziom: **średni** · Rodzaj: **napisz** · Teoria: [10. Enumy i rekordy](../../teoria/10-enumy-i-rekordy.md)

## Uruchomienie

```bash
./cwiczenia/16-kalkulator-enum/check.sh
```

## Zadanie

Napisz prosty kalkulator wyrażeń `"liczba operator liczba"`, np. `"12 * 3"`. Szkielety klas są gotowe.

1. `Operation` (enum ze stałymi `ADD("+")`, `SUBTRACT("-")`, `MULTIPLY("*")`, `DIVIDE("/")`):
   - pole z symbolem i metoda `symbol()`,
   - `apply(long a, long b)`: wynik operacji; **każda stała liczy sama** (metoda abstrakcyjna nadpisana w każdej stałej albo pole z lambdą); dzielenie całkowite, dzielenie przez 0 to `ArithmeticException`,
   - `static fromSymbol(String)`: stała dla symbolu, nieznany symbol to `IllegalArgumentException` z symbolem w komunikacie.
2. `Calculator`:
   - `evaluate(String)`: liczby i operator są rozdzielone co najmniej jedną spacją (`"100   -  1"` też jest poprawne, spacje na brzegach też), liczby mogą być ujemne. Inny format: `IllegalArgumentException`.
   - `usage()`: ile razy użyto każdej operacji (tylko udane obliczenia), w kolejności stałych w `Operation`. Każdy kalkulator liczy osobno.

## Wymagania

- Bez `switch` i `ordinal()` (sprawdza `check.sh`).
- Do liczenia użyć `EnumMap`.

## Pytania dodatkowe

1. Dlaczego zapisywanie `ordinal()` do bazy albo pliku to zły pomysł? Co się stanie po dodaniu stałej `MODULO` w środku?
2. Co zwraca `Operation.valueOf("ADD")`, a co `Operation.valueOf("+")`? Czym się różni od `fromSymbol`?
3. Dlaczego enumy porównuje się przez `==`, a nie `equals`?
4. Jak wyglądałby `apply` napisany przez wyrażenie `switch` (Java 14+)? Co się stanie przy dodaniu nowej stałej w każdej z wersji?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
