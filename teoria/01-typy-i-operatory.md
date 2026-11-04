# 01. Typy i operatory

Ćwiczenia: [01](../cwiczenia/01-srednia-ocen/ZADANIE.md), [02](../cwiczenia/02-pieniadze-w-double/ZADANIE.md)

## Typy proste (prymitywne)

Java ma 8 typów prostych. Zmienna typu prostego przechowuje **samą wartość** (nie referencję do obiektu).

| Typ | Rozmiar | Zakres / uwagi | Literał |
|---|---|---|---|
| `byte` | 8 bitów | -128 .. 127 | `(byte) 10` |
| `short` | 16 bitów | -32 768 .. 32 767 | `(short) 10` |
| `int` | 32 bity | ok. ±2,1 mld (`Integer.MAX_VALUE` = 2 147 483 647) | `10`, `1_000_000` |
| `long` | 64 bity | ok. ±9,2 · 10^18 | `10L` |
| `float` | 32 bity | ok. 7 cyfr znaczących | `1.5f` |
| `double` | 64 bity | ok. 15-16 cyfr znaczących | `1.5`, `1e-9` |
| `char` | 16 bitów | jeden znak UTF-16 | `'a'`, `'ą'` |
| `boolean` | | `true` / `false` | `true` |

Na co dzień: `int`, `long`, `double`, `boolean`, `char`. Do pieniędzy: `BigDecimal` albo `long` w groszach (niżej).

Podkreślenia w literałach (`1_000_000`) tylko poprawiają czytelność.

## Typ wyrażenia decyduje o wyniku

Najważniejsza zasada tego rozdziału: **typ wyniku operacji zależy od typów argumentów, a nie od zmiennej, do której trafia wynik.**

```java
int a = 7, b = 2;
double x = a / b;          // 3.0 !  int / int = int (3), dopiero potem zamiana na double
double y = (double) a / b; // 3.5    double / int = double
double z = a / 2.0;        // 3.5    int / double = double
double w = (double) (a / b); // 3.0  rzutowanie już po dzieleniu całkowitym
```

Reguły promocji w wyrażeniach arytmetycznych:

1. `byte`, `short`, `char` są zamieniane na `int` przed każdym działaniem.
2. Jeśli któryś argument jest `double`, wynik jest `double`; inaczej jeśli `float`, to `float`; inaczej jeśli `long`, to `long`; inaczej `int`.

Dzielenie całkowite obcina część ułamkową (w stronę zera): `7 / 2 == 3`, `-7 / 2 == -3`. Reszta z dzielenia: `7 % 2 == 1`, `-7 % 2 == -1`.

Dzielenie całkowite przez zero rzuca `ArithmeticException`. Dzielenie `double` przez zero daje `Infinity` albo `NaN`, bez wyjątku.

## Przepełnienie (overflow)

Liczby całkowite w Javie **przekręcają się** po cichu, bez żadnego błędu:

```java
int max = Integer.MAX_VALUE;   // 2147483647
int next = max + 1;            // -2147483648
```

Pułapka z mnożeniem:

```java
int seconds = 3_000_000;
long millis = seconds * 1000;   // -1294967296 ! int * int = int, przepełnienie, potem zamiana na long
long ok     = seconds * 1000L;  // 3000000000   int * long = long
```

Zmiana typu zmiennej po lewej stronie nic nie daje, jeśli samo wyrażenie po prawej liczy się w `int`.

Gdy przepełnienie ma być błędem, a nie cichą pomyłką: `Math.addExact`, `Math.multiplyExact`, `Math.toIntExact` rzucają `ArithmeticException`.

## Rzutowanie (konwersje)

- **Rozszerzające** (`int` -> `long`, `int` -> `double`) są automatyczne i bezpieczne (prawie: `long` -> `double` może zgubić precyzję dużych liczb).
- **Zawężające** (`double` -> `int`, `long` -> `int`) wymagają jawnego rzutowania i mogą zgubić dane:

```java
int i = (int) 9.99;          // 9     obcięcie, nie zaokrąglenie
int r = (int) Math.round(9.5); // 10  Math.round zwraca long dla double
int j = (int) 3_000_000_000L;  // -1294967296
```

Operatory złożone (`+=`, `*=`) rzutują same: `int x = 5; x += 2.7;` kompiluje się i daje `7`.

## double nie nadaje się do pieniędzy

`double` przechowuje liczbę w systemie dwójkowym. Ułamków takich jak 0,1 nie da się zapisać dokładnie dwójkowo (tak jak 1/3 dziesiętnie), więc:

```java
System.out.println(0.1 + 0.2);        // 0.30000000000000004
System.out.println(0.1 * 3 == 0.3);   // false
```

To nie błąd Javy, tak działa standard IEEE 754 w każdym języku. Do obliczeń naukowych to nie przeszkadza, do pieniędzy tak.

Porównując `double`, używaj tolerancji: `Math.abs(a - b) < 1e-9` (tak robi `assertEquals(expected, actual, delta)` w testach).

## BigDecimal

`java.math.BigDecimal` to liczba dziesiętna o dowolnej precyzji: wartość + **skala** (liczba cyfr po przecinku).

```java
BigDecimal price = new BigDecimal("19.99");          // z tekstu: dokładnie 19.99
BigDecimal qty = BigDecimal.valueOf(3);
BigDecimal total = price.multiply(qty);               // 59.97
BigDecimal withVat = total.multiply(new BigDecimal("1.23"))
                          .setScale(2, RoundingMode.HALF_UP); // 73.76
```

Zasady:

1. **Twórz z tekstu** (`new BigDecimal("0.1")`) albo przez `BigDecimal.valueOf(double)`. `new BigDecimal(0.1)` daje `0.1000000000000000055511151231257827...`, bo dostaje już niedokładny `double`.
2. `BigDecimal` jest **niemutowalny**: `a.add(b)` zwraca nowy obiekt, `a` się nie zmienia. `total.add(x);` bez przypisania nic nie robi.
3. Zaokrąglanie zawsze jawnie: `setScale(2, RoundingMode.HALF_UP)` („szkolne”: od 5 w górę), `HALF_EVEN` (bankowe), `DOWN` (obcięcie). `divide` bez trybu zaokrąglenia rzuca wyjątek, gdy wynik ma nieskończone rozwinięcie (1/3).
4. **`equals` porównuje też skalę**: `new BigDecimal("0.3").equals(new BigDecimal("0.30"))` to `false`. Do porównania wartości: `a.compareTo(b) == 0`.

Alternatywa: kwoty w groszach jako `long` (1999 = 19,99 zł). Szybkie i proste przy dodawaniu, ale procenty, kursy walut i dzielenie trzeba zaokrąglać ręcznie.

## Operatory, o których warto pamiętać

- `i++` (zwraca starą wartość) i `++i` (nową): `int a = 5; int b = a++;` daje `a == 6`, `b == 5`.
- `&&` i `||` są **leniwe**: `user != null && user.isActive()` nie zawoła `isActive()` na `null`.
- `==` na typach prostych porównuje wartości. Na obiektach porównuje referencje (rozdział 02).
- Operator warunkowy: `String label = count == 1 ? "plik" : "pliki";`

## Typowe błędy

- `double avg = sum / count;` przy `int sum, count`.
- `long ms = seconds * 1000;` przy `int seconds`.
- Pieniądze w `double` albo `float`.
- `new BigDecimal(0.1)` zamiast `new BigDecimal("0.1")`.
- `bigDecimal.equals(...)` zamiast `compareTo`.
- `(int) 2.99` w przekonaniu, że to zaokrąglenie.

## Pytania sprawdzające

1. Ile wynosi `5 / 2`, `5 / 2.0`, `5 % 2`, `-5 / 2`?
2. Dlaczego `long x = Integer.MAX_VALUE + 1;` daje liczbę ujemną? Jak to poprawić?
3. Jaki typ ma wyrażenie `'a' + 1`? Co wypisze `System.out.println('a' + 1)`, a co `System.out.println((char) ('a' + 1))`?
4. Dlaczego `0.1 + 0.2 == 0.3` to `false`?
5. Czym różni się `new BigDecimal("2.50").equals(new BigDecimal("2.5"))` od `compareTo`?
6. Co robi `RoundingMode.HALF_UP`, a co `RoundingMode.DOWN` dla 1,845 przy skali 2?
