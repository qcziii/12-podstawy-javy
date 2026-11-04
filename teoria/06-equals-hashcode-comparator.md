# 06. equals, hashCode, toString, Comparable, Comparator

Ćwiczenia: [08](../cwiczenia/08-duplikaty-w-secie/ZADANIE.md), [09](../cwiczenia/09-zgubiony-klucz/ZADANIE.md), [10](../cwiczenia/10-sortowanie-produktow/ZADANIE.md), [05](../cwiczenia/05-numery-wersji/ZADANIE.md)

## equals

`Object.equals` domyślnie porównuje referencje (jak `==`). Jeśli dwa różne obiekty mają oznaczać „to samo” (ten sam produkt, ta sama kwota), klasa musi nadpisać `equals`.

```java
@Override
public boolean equals(Object o) {
    if (this == o) {
        return true;
    }
    if (!(o instanceof Product other)) {   // także false dla null
        return false;
    }
    return sku.equals(other.sku);
}
```

Kontrakt `equals`:

1. **zwrotność**: `x.equals(x)` to `true`,
2. **symetria**: `x.equals(y)` wtedy i tylko wtedy, gdy `y.equals(x)`,
3. **przechodniość**: `x.equals(y)` i `y.equals(z)`, to `x.equals(z)`,
4. **spójność**: wynik się nie zmienia, jeśli obiekty się nie zmieniają,
5. `x.equals(null)` to `false`, bez wyjątku.

Które pola porównywać? Te, które stanowią **tożsamość** obiektu w danej domenie. Produkt z katalogu to SKU (cena się zmienia, a to dalej ten sam produkt). Kwota pieniędzy to wartość i waluta.

## hashCode

`HashMap` i `HashSet` nie przeszukują wszystkich elementów. Najpierw liczą `hashCode()` klucza i na tej podstawie wybierają „kubełek”, dopiero w nim porównują `equals`:

```
hashCode("SKU-1") % 16 = 5   ->   kubełek 5: [SKU-1 Kawa]
```

Stąd kontrakt: **jeśli `a.equals(b)`, to `a.hashCode() == b.hashCode()`**. Odwrotnie nie musi (różne obiekty mogą mieć ten sam hash: kolizja).

Gdy nadpiszesz `equals`, a nie `hashCode`, każdy obiekt ma inny hash (domyślny, z tożsamości), więc dwa „równe” produkty trafiają do różnych kubełków i `HashSet` nigdy ich nie porówna. Zasada: **zawsze oba albo żadne**, oparte na tych samych polach.

```java
@Override
public int hashCode() {
    return Objects.hash(sku);       // dla kilku pól: Objects.hash(a, b, c)
}
```

`hashCode` zwracający stałą (`return 42;`) jest poprawny według kontraktu, ale wszystkie elementy lądują w jednym kubełku i `HashMap` staje się listą (wolno).

IntelliJ generuje oba: Alt+Insert (Cmd+N) → `equals() and hashCode()`. Rekordy (rozdział 10) mają je od razu.

## Zmienny klucz w HashMap/HashSet

Jeśli zmienisz pole, od którego zależy `hashCode`, obiekt, który już jest w `HashSet`, leży w kubełku wyliczonym ze **starego** hasha. `contains` i `remove` szukają w kubełku z **nowego** hasha i nie znajdują go. Element „zniknął”, choć `size()` go liczy.

```java
Set<Customer> set = new HashSet<>();
Customer c = new Customer("ala@x.pl");
set.add(c);
c.setEmail("ala@y.pl");
set.contains(c);   // false
set.remove(c);     // false
```

Rozwiązania: klucze niemutowalne (najlepiej); klucz, który się nie zmienia (id zamiast e-maila); albo wyjąć, zmienić, włożyć z powrotem.

## toString

Domyślnie `Product@1b6d3586`, nieczytelne. Nadpisz, żeby logi, komunikaty testów i debugger pokazywały coś sensownego:

```java
@Override
public String toString() {
    return sku + " " + name;
}
```

## Comparable: naturalny porządek

```java
public final class Version implements Comparable<Version> {
    @Override
    public int compareTo(Version other) {
        if (major != other.major) {
            return Integer.compare(major, other.major);
        }
        ...
    }
}

Collections.sort(versions);   // używa compareTo
```

`compareTo` zwraca liczbę ujemną (ten < tamten), zero (równe), dodatnią (ten > tamten). Liczy się znak, nie wartość.

`compareTo` powinno być **zgodne z `equals`** (`compareTo == 0` dokładnie wtedy, gdy `equals`). `TreeSet` i `TreeMap` używają **tylko** `compareTo`/komparatora: dwa elementy z wynikiem 0 traktują jako jeden, nawet jeśli `equals` mówi co innego.

## Comparator: porządek z zewnątrz

Gdy porządków jest wiele (po cenie, po nazwie) albo klasa nie jest twoja:

```java
Comparator<Item> byPrice = Comparator.comparingDouble(Item::price);
Comparator<Item> byPriceThenName = Comparator.comparingDouble(Item::price)
                                             .thenComparing(Item::name);
Comparator<Item> mostExpensiveFirst = byPrice.reversed();

items.sort(byPriceThenName);
new TreeSet<>(Comparator.comparing(Item::name).thenComparingDouble(Item::price));
```

Przydatne: `comparing`, `comparingInt/Long/Double`, `thenComparing`, `reversed`, `Comparator.naturalOrder()`, `reverseOrder()`, `nullsFirst/nullsLast`, `Map.Entry.comparingByValue()`.

### Nie odejmuj w komparatorze

```java
(a, b) -> (int) (a.price() - b.price())   // 9.99 - 9.49 = 0.5, (int) 0.5 = 0: "równe"!
(a, b) -> a.age() - b.age()               // dla int działa... dopóki nie ma przepełnienia
                                          // (Integer.MIN_VALUE - 1 to wartość dodatnia)
```

Zawsze `Integer.compare`, `Double.compare` albo `Comparator.comparing...`.

### reversed() w łańcuchu

`reversed()` odwraca **cały** komparator zbudowany do tego miejsca:

```java
comparing(Item::price).thenComparing(Item::name).reversed()     // odwraca i cenę, i nazwę
comparing(Item::price, Comparator.reverseOrder()).thenComparing(Item::name)  // tylko cenę
```

## Typowe błędy

- `equals` bez `hashCode` (albo odwrotnie).
- `equals(Product other)` zamiast `equals(Object o)`.
- Pole w `equals`/`hashCode`, które się zmienia, gdy obiekt jest kluczem w mapie.
- Komparator przez odejmowanie i rzutowanie na `int`.
- `TreeSet` z komparatorem po jednym polu, który „gubi” elementy różniące się innym polem.

## Pytania sprawdzające

1. Jak `HashSet.contains` znajduje element? Gdzie jest `hashCode`, a gdzie `equals`?
2. Co się stanie, gdy `equals` jest nadpisane, a `hashCode` nie?
3. Dlaczego zmiana e-maila klienta, który jest w `HashSet`, „gubi” go?
4. Co zwraca `compareTo` i co znaczy wynik 0 dla `TreeSet`?
5. Dlaczego `(a, b) -> (int) (a.price() - b.price())` jest złym komparatorem?
6. Napisz komparator: najpierw po liczbie punktów malejąco, potem po nazwisku rosnąco.
