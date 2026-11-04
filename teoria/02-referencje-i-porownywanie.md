# 02. Referencje i porównywanie

Ćwiczenia: [03](../cwiczenia/03-porownanie-przez-rowna-sie/ZADANIE.md)

## Zmienna prosta a zmienna referencyjna

```java
int a = 5;
int b = a;      // kopia wartości
b = 6;          // a dalej 5

int[] t1 = {1, 2};
int[] t2 = t1;  // kopia REFERENCJI: obie zmienne wskazują tę samą tablicę
t2[0] = 99;     // t1[0] też jest 99
```

Zmienna typu obiektowego (każda klasa, tablica, `String`, `Integer`...) nie przechowuje obiektu, tylko **referencję** (adres) do obiektu na stercie. Przypisanie kopiuje referencję, nie obiekt.

Uproszczony obraz:

```
stos (zmienne lokalne)       sterta (obiekty)
t1 ──────────────┐
                 ├──────────> [99, 2]
t2 ──────────────┘
```

## Przekazywanie do metod: zawsze przez wartość

Java **zawsze** przekazuje argumenty przez wartość. Dla obiektów tą wartością jest referencja.

```java
static void change(int[] arr, int x) {
    arr[0] = 100;      // zmienia obiekt, na który wskazuje referencja: widać to na zewnątrz
    arr = new int[3];  // zmienia lokalną kopię referencji: na zewnątrz nic się nie dzieje
    x = 42;            // lokalna kopia wartości: na zewnątrz nic się nie dzieje
}
```

Wniosek: metoda może zmienić **zawartość** przekazanego obiektu (listy, tablicy), ale nie może sprawić, żeby zmienna wołającego wskazywała inny obiekt.

## `null`

`null` oznacza „referencja nie wskazuje żadnego obiektu”. Wywołanie metody na `null` kończy się `NullPointerException`:

```java
String s = null;
s.length();         // NullPointerException
```

Od Javy 14 komunikat NPE mówi dokładnie, co było nullem („Cannot invoke "String.length()" because "s" is null”). Czytaj go uważnie.

Typy proste nie mogą być `null`. Ich odpowiedniki obiektowe (`Integer`, `Long`...) mogą.

## `==` a `equals`

| | `==` | `equals` |
|---|---|---|
| typy proste | porównuje wartości | (nie istnieje) |
| obiekty | czy to **ten sam obiekt** (te same referencje) | czy obiekty są **równe** (logika zdefiniowana w klasie) |

```java
String a = new String("ala");
String b = new String("ala");
a == b;          // false: dwa różne obiekty
a.equals(b);     // true: ta sama treść
```

Dla obiektów prawie zawsze chcesz `equals`. `==` na obiektach używa się świadomie: dla enumów (rozdział 10), dla porównania z `null` i gdy naprawdę chodzi o tożsamość.

Bezpieczne porównania, gdy coś może być `null`:

```java
"ADMIN".equals(role)       // false dla role == null, bez wyjątku
Objects.equals(a, b)       // true, gdy oba null; false, gdy jedno null; inaczej a.equals(b)
```

## Klasy opakowujące i autoboxing

Każdy typ prosty ma klasę-opakowanie: `int` – `Integer`, `long` – `Long`, `double` – `Double`, `boolean` – `Boolean`, `char` – `Character`. Kolekcje przechowują tylko obiekty, więc `List<Integer>`, nie `List<int>`.

Kompilator zamienia automatycznie:

```java
Integer boxed = 5;        // autoboxing: Integer.valueOf(5)
int unboxed = boxed;      // unboxing: boxed.intValue()
```

Dwie pułapki:

**1. Cache wartości od -128 do 127.** `Integer.valueOf` (i `Long.valueOf`, `Short`, `Byte`, `Character` do 127) dla małych liczb zwraca zawsze ten sam obiekt z pamięci podręcznej. Dla większych tworzy nowe obiekty:

```java
Integer a = 127, b = 127;
a == b;        // true  (ten sam obiekt z cache)
Integer c = 128, d = 128;
c == d;        // false (dwa różne obiekty)
c.equals(d);   // true
```

Kod porównujący `Integer`/`Long` przez `==` działa w testach na małych id i psuje się na produkcji. Zawsze `equals` (albo zamiana na typ prosty: `c.intValue() == d.intValue()`).

**2. Unboxing `null`.**

```java
Long id = null;
long x = id;      // NullPointerException
```

Uwaga też na mieszanie: `Long l = 5L; l.equals(5)` to `false`, bo `5` to `Integer`, a `Long.equals(Integer)` zawsze zwraca `false`.

## Pula Stringów

Literały tekstowe (`"ADMIN"`) są przechowywane w puli: ten sam literał w kodzie to ten sam obiekt. Dlatego `"ADMIN" == "ADMIN"` daje `true`, a test z literałami „przechodzi”. Tekst powstały w czasie działania (z pliku, JSON-a, `StringBuilder`, `substring`, `+` na zmiennych) to nowy obiekt i `==` daje `false`. Wniosek ten sam: Stringi porównuj przez `equals`.

## Typowe błędy

- `if (user.getId() == other.getId())` dla `Long`.
- `if (status == "ACTIVE")` dla `String`.
- `role.equals("ADMIN")`, gdy `role` może być `null`.
- `int count = map.get(key);`, gdy klucza nie ma w mapie (unboxing `null`).

## Pytania sprawdzające

1. Co wypisze program: `int[] a = {1}; int[] b = a; b[0] = 5; System.out.println(a[0]);`?
2. Metoda dostaje `List<String> list` i robi `list = new ArrayList<>(); list.add("x");`. Czy wołający zobaczy `"x"`? A gdyby zrobiła tylko `list.add("x")`?
3. Dlaczego `Integer a = 1000, b = 1000; a == b` daje `false`, a dla 100 `true`?
4. Co robi `Objects.equals(null, null)`?
5. Kiedy `==` na obiektach jest poprawne?
