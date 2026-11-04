# 11. Lambdy i streamy

Ćwiczenia: [18](../cwiczenia/18-raport-sprzedazy/ZADANIE.md), [21](../cwiczenia/21-projekt-wypozyczalnia/ZADANIE.md)

## Lambda

Lambda to krótki zapis implementacji **interfejsu funkcyjnego** (interfejsu z jedną metodą abstrakcyjną):

```java
Comparator<String> byLength = (a, b) -> Integer.compare(a.length(), b.length());
Predicate<String> isEmpty = s -> s.isEmpty();
Runnable hello = () -> System.out.println("Hej");
Function<String, Integer> length = s -> {
    int n = s.length();
    return n;                 // z klamrami: return wymagany
};
```

To to samo, co klasa anonimowa, tylko krócej.

Najważniejsze interfejsy z `java.util.function`:

| Interfejs | Metoda | Przykład |
|---|---|---|
| `Function<T, R>` | `R apply(T)` | `s -> s.length()` |
| `Predicate<T>` | `boolean test(T)` | `s -> s.isBlank()` |
| `Consumer<T>` | `void accept(T)` | `s -> System.out.println(s)` |
| `Supplier<T>` | `T get()` | `() -> new ArrayList<>()` |
| `BiFunction<T, U, R>` | `R apply(T, U)` | `(a, b) -> a + b` |
| `UnaryOperator<T>`, `BinaryOperator<T>` | jak `Function`, ten sam typ | `x -> x * 2`, `Integer::sum` |

Wersje dla typów prostych (bez boxingu): `IntPredicate`, `ToLongFunction<T>`, `LongBinaryOperator`...

### Referencje do metod

```java
s -> s.length()                 String::length          // metoda instancji argumentu
s -> System.out.println(s)      System.out::println     // metoda konkretnego obiektu
s -> Integer.parseInt(s)        Integer::parseInt       // metoda statyczna
() -> new ArrayList<>()         ArrayList::new          // konstruktor
```

### Zmienne z otoczenia

Lambda może używać zmiennych lokalnych z otoczenia tylko wtedy, gdy są **effectively final** (nie zmieniają się po przypisaniu):

```java
int limit = 10;
list.removeIf(x -> x > limit);    // ok
int sum = 0;
list.forEach(x -> sum += x);      // błąd kompilacji: sum się zmienia. Użyj streamu: mapToInt(...).sum()
```

## Stream

Stream to potok operacji na sekwencji elementów: **źródło -> operacje pośrednie -> operacja końcowa**.

```java
List<String> names = sales.stream()            // źródło
        .filter(s -> s.region().equals("Północ"))   // pośrednia
        .map(Sale::seller)                          // pośrednia
        .distinct()                                 // pośrednia
        .sorted()                                   // pośrednia
        .toList();                                  // końcowa
```

Najważniejsze cechy:

1. **Leniwy.** Operacje pośrednie nic nie robią, dopóki nie ma operacji końcowej. `list.stream().map(x -> { System.out.println(x); return x; });` nie wypisze niczego.
2. **Jednorazowy.** Stream po operacji końcowej jest zużyty; drugi raz `IllegalStateException: stream has already been operated upon or closed`.
3. **Nie zmienia źródła.** `filter` nie usuwa z listy, tylko produkuje nowy wynik.
4. Lambdy w streamie nie powinny mieć efektów ubocznych (zmieniać zewnętrznych zmiennych i kolekcji).

### Operacje pośrednie

| Operacja | Co robi |
|---|---|
| `filter(predicate)` | zostawia elementy spełniające warunek |
| `map(function)` | zamienia każdy element |
| `mapToInt/Long/Double` | zamienia na `IntStream` itd. (są `sum`, `average`, `max`) |
| `flatMap(f)` | każdy element na stream i spłaszczenie (`List<List<X>>` -> `X`) |
| `distinct()` | bez powtórzeń (`equals`) |
| `sorted()`, `sorted(comparator)` | sortowanie |
| `limit(n)`, `skip(n)` | pierwsze n / pomiń n |
| `peek(consumer)` | podgląd (do debugowania) |

### Operacje końcowe

| Operacja | Wynik |
|---|---|
| `toList()` (Java 16+) | niemodyfikowalna lista |
| `collect(Collectors...)` | lista, zbiór, mapa, tekst... |
| `count()` | `long` |
| `sum()`, `average()`, `min()`, `max()` (na `IntStream` itd.) | `sum` liczba, reszta `OptionalInt/Double` |
| `min(comparator)`, `max(comparator)` | `Optional<T>` |
| `findFirst()`, `findAny()` | `Optional<T>` |
| `anyMatch`, `allMatch`, `noneMatch` | `boolean` |
| `forEach(consumer)` | nic (efekt uboczny) |
| `reduce(identity, op)` | jedna wartość |

```java
long total = sales.stream().mapToLong(Sale::revenue).sum();
double avg = sales.stream().mapToInt(Sale::quantity).average().orElse(0.0);
Optional<Sale> biggest = sales.stream().max(Comparator.comparingLong(Sale::revenue));
boolean any = sales.stream().anyMatch(s -> s.quantity() > 100);
```

## Collectors

```java
import java.util.stream.Collectors;

// lista, zbiór, tekst
.collect(Collectors.toList())          // lista (w praktyce ArrayList, bez gwarancji); toList() zwraca niemodyfikowalną
.collect(Collectors.toSet())
.collect(Collectors.joining(", ", "[", "]"))

// mapa: klucz -> wartość, każdy klucz raz
.collect(Collectors.toMap(Product::sku, Product::price))
// drugi raz ten sam klucz: IllegalStateException: Duplicate key. Trzecim argumentem decydujesz:
.collect(Collectors.toMap(Product::sku, Product::price, (old, neu) -> neu))

// grupowanie: klucz -> lista elementów
Map<String, List<Sale>> byRegion = sales.stream().collect(Collectors.groupingBy(Sale::region));

// grupowanie z dalszym przetwarzaniem grupy (downstream collector)
Map<String, Long> revenue = sales.stream()
        .collect(Collectors.groupingBy(Sale::region, Collectors.summingLong(Sale::revenue)));
Map<String, Long> count = ... groupingBy(Sale::seller, Collectors.counting());
Map<String, List<String>> products = ... groupingBy(Sale::region, Collectors.mapping(Sale::product, Collectors.toList()));

// grupowanie do TreeMap (klucze posortowane)
Map<String, Long> sorted = sales.stream()
        .collect(Collectors.groupingBy(Sale::region, TreeMap::new, Collectors.summingLong(Sale::revenue)));

// podział na dwie grupy: true/false
Map<Boolean, List<Sale>> bigOrNot = sales.stream()
        .collect(Collectors.partitioningBy(s -> s.revenue() > 5000));
```

### Sortowanie wpisów mapy

```java
map.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                .thenComparing(Map.Entry.comparingByKey()))
        .limit(3)
        .map(Map.Entry::getKey)
        .toList();
```

(`<String, Integer>` przed `comparingByValue()` podpowiada kompilatorowi typy, bo `reversed()` je gubi.)

## Stream czy pętla?

Stream jest czytelny, gdy opisuje przekształcenie danych: filtruj, zamień, zgrupuj, policz. Pętla jest lepsza, gdy jest dużo warunków, wczesne wyjście z kilku miejsc, praca na indeksach albo zmiana stanu. Nie zamieniaj na siłę wszystkiego na streamy; nie pisz też ręcznie pętli z mapą-licznikiem, gdy wystarczy `groupingBy`.

## Typowe błędy

- Stream bez operacji końcowej („nic się nie dzieje”).
- Użycie tego samego streamu dwa razy.
- `forEach` z dodawaniem do zewnętrznej listy zamiast `map` + `toList()`.
- `toMap` przy możliwych duplikatach kluczy bez funkcji łączącej.
- `toList()` i potem `add` (lista niemodyfikowalna).
- `.get()` na `Optional` z `max`/`findFirst` bez obsługi pustego wyniku.

## Pytania sprawdzające

1. Co to jest interfejs funkcyjny? Podaj trzy z `java.util.function`.
2. Zapisz `x -> x.toUpperCase()` jako referencję do metody.
3. Dlaczego `int sum = 0; list.forEach(x -> sum += x);` się nie kompiluje?
4. Co wypisze `List.of(1, 2, 3).stream().peek(System.out::println).map(x -> x * 2);`?
5. Jak policzyć przychód per region, z regionami posortowanymi?
6. Co zrobi `toMap`, gdy dwa elementy dadzą ten sam klucz?
