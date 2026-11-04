# Ściąga: podstawy Javy

## Liczby

```java
7 / 2 == 3;  7 / 2.0 == 3.5;  (double) 7 / 2 == 3.5;  7 % 2 == 1
long ms = seconds * 1000L;            // L! inaczej mnożenie w int i przepełnienie
Math.addExact(a, b);                  // wyjątek zamiast cichego przepełnienia
new BigDecimal("19.99")               // pieniądze: z tekstu, nie z double
a.add(b); a.multiply(b); x.setScale(2, RoundingMode.HALF_UP)
a.compareTo(b) == 0                   // porównanie kwot (equals patrzy też na skalę)
```

## Porównywanie

| Co | Jak |
|---|---|
| typy proste | `==` |
| obiekty (`String`, `Integer`, `Long`, `BigDecimal`...) | `equals` (`BigDecimal`: `compareTo`) |
| może być `null` | `"stała".equals(x)`, `Objects.equals(a, b)` |
| enumy | `==` |
| `double` | `Math.abs(a - b) < 1e-9` |

`Integer`/`Long` przez `==` działa tylko dla -128..127.

## String (niemutowalny: używaj wyniku!)

```java
s = s.trim().toLowerCase().replace(' ', '_');
s.split("\\.")                  // regex! kropka, |, + itd. trzeba escapować
s.trim().split("\\s+")          // słowa przy wielu spacjach
s.isBlank(); s.strip(); s.substring(od, doWyłącznie); s.indexOf('@')
String.join(", ", list); "%d,%02d zł".formatted(zl, gr)
StringBuilder sb = new StringBuilder(); sb.append(x); sb.toString();   // w pętli
Integer.parseInt("42")          // NumberFormatException
```

## Klasa

```java
public class X {
    private static int counter;          // jedno na klasę
    private final List<String> items = new ArrayList<>();   // każdy obiekt ma swoje
    public X(List<String> start) { this.items.addAll(start); }   // kopia, nie przypisanie
    public List<String> items() { return List.copyOf(items); }   // nie oddawaj swojej listy
}
```

## equals / hashCode / compareTo

```java
@Override public boolean equals(Object o) {          // Object, nie X!
    if (this == o) return true;
    if (!(o instanceof X other)) return false;
    return id.equals(other.id);
}
@Override public int hashCode() { return Objects.hash(id); }   // zawsze razem z equals
Comparator.comparing(X::name).thenComparingDouble(X::price).reversed()
Integer.compare(a, b)                                  // nie a - b
```

Klucz w `HashMap`/`HashSet` nie może się zmieniać. `TreeSet`/`TreeMap` uznają za równe to, co komparator.

## Kolekcje

| Potrzebuję | Wybieram |
|---|---|
| lista w kolejności | `ArrayList` |
| bez duplikatów | `HashSet` (`LinkedHashSet` z kolejnością, `TreeSet` posortowane) |
| klucz -> wartość | `HashMap` (`LinkedHashMap`, `TreeMap`) |
| klucze to enum | `EnumMap`, `EnumSet` |

```java
new ArrayList<>(Arrays.asList(arr))    // Arrays.asList i List.of nie pozwalają na add
list.removeIf(x -> ...)                // nie remove w for-each
list.remove(Integer.valueOf(id))       // List<Integer>: remove(int) usuwa po indeksie!
map.getOrDefault(k, 0); map.merge(k, 1, Integer::sum);
map.computeIfAbsent(k, key -> new ArrayList<>()).add(v);
```

## Generyki

```java
static <T> T max(Collection<? extends T> items, Comparator<? super T> cmp)
// PECS: z czego czytasz: ? extends; do czego wkładasz: ? super
// List<Integer> NIE jest List<Number>
```

## Wyjątki

```java
throw new IllegalArgumentException("Zła ocena: " + grade);     // zły argument
throw new IllegalStateException("Już wypożyczony: " + id);     // zły stan
try (BufferedReader r = ...) { ... }                           // zamyka zawsze
catch (NumberFormatException e) { throw new ImportException("Linia " + n, e); }   // z przyczyną
// NIE: catch (Exception e) { System.out.println(...); return List.of(); }
```

## Enum i rekord

```java
enum Op { ADD("+") { long apply(long a, long b) { return a + b; } }; ... abstract long apply(long a, long b); }
Op.values(); Op.valueOf("ADD"); op.name();       // ordinal() nie do zapisu
record Order(String number, List<Line> lines) {
    Order { if (number.isBlank()) throw new IllegalArgumentException(); lines = List.copyOf(lines); }
}
```

## Streamy

```java
list.stream().filter(...).map(...).sorted().distinct().limit(n).toList()
.mapToLong(Sale::revenue).sum()          .mapToInt(...).average().orElse(0.0)
.collect(Collectors.groupingBy(Sale::region, TreeMap::new, Collectors.summingLong(Sale::revenue)))
.collect(Collectors.groupingBy(f, Collectors.counting()))
.collect(Collectors.toMap(k, v, (a, b) -> b))     // bez 3. argumentu: wyjątek przy duplikacie
.max(Comparator.comparing(...))                     // Optional
```

Bez operacji końcowej stream nic nie robi. Stream jest jednorazowy.

## Optional

```java
return Optional.ofNullable(map.get(k));             // nigdy return null
opt.map(User::nickname).orElse("Gość")
opt.orElseGet(this::kosztowne)                      // orElse(kosztowne()) woła zawsze
opt.orElseThrow(() -> new NotFound(id))
```

## Pliki

```java
Files.readAllLines(path, StandardCharsets.UTF_8);  Files.readString(path, UTF_8)
Files.write(path, lines, StandardCharsets.UTF_8);   Files.writeString(path, s, UTF_8)
try (BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) { ... }
Path.of("dane").resolve("plik.txt")
```

Kodowanie zawsze jawnie, writer zawsze zamknięty.
