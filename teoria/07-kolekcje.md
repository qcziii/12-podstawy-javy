# 07. Kolekcje

Ćwiczenia: [11](../cwiczenia/11-usuwanie-w-petli/ZADANIE.md), [12](../cwiczenia/12-remove-po-indeksie/ZADANIE.md), [13](../cwiczenia/13-dziennik-ocen/ZADANIE.md), [09](../cwiczenia/09-zgubiony-klucz/ZADANIE.md), [10](../cwiczenia/10-sortowanie-produktow/ZADANIE.md)

## Trzy podstawowe interfejsy

| Interfejs | Co to jest | Duplikaty | Kolejność | Typowa implementacja |
|---|---|---|---|---|
| `List` | ciąg elementów z indeksami | tak | kolejność wstawiania | `ArrayList` |
| `Set` | zbiór bez powtórzeń | nie | zależy od implementacji | `HashSet` |
| `Map` | klucz -> wartość | klucze nie, wartości tak | zależy od implementacji | `HashMap` |

Implementacje i kolejność:

| | Hash... | Linked... | Tree... |
|---|---|---|---|
| Set | `HashSet`: bez gwarancji kolejności | `LinkedHashSet`: kolejność wstawiania | `TreeSet`: posortowane |
| Map | `HashMap`: bez gwarancji | `LinkedHashMap`: kolejność wstawiania | `TreeMap`: klucze posortowane |
| wymaga | `equals` + `hashCode` | `equals` + `hashCode` | `Comparable` albo `Comparator` |
| `add`/`get`/`contains` | O(1) | O(1) | O(log n) |

`ArrayList` ma szybki dostęp po indeksie (O(1)) i dodawanie na końcu; wstawianie i usuwanie w środku przesuwa elementy (O(n)). `LinkedList` prawie nigdy nie jest lepsza. `ArrayDeque` do kolejek i stosów.

`EnumMap` i `EnumSet`: najszybsze dla kluczy będących enumami, kolejność jak kolejność stałych.

## List: najważniejsze operacje

```java
List<String> list = new ArrayList<>();
list.add("a");                 // na końcu
list.add(0, "z");              // na pozycji 0
list.get(1);                   // "a"
list.set(1, "b");              // zamiana
list.remove(0);                // usuwa po INDEKSIE
list.remove("b");              // usuwa po WARTOŚCI (pierwsze wystąpienie), zwraca boolean
list.contains("a");            // woła equals
list.indexOf("a");             // -1, gdy brak
list.size(); list.isEmpty();
Collections.sort(list); list.sort(Comparator.reverseOrder());
```

### Pułapka: `remove(int)` i `remove(Object)` w `List<Integer>`

```java
List<Integer> ids = new ArrayList<>(List.of(10, 20, 30));
ids.remove(20);                   // IndexOutOfBoundsException: usuwa po INDEKSIE 20
ids.remove(Integer.valueOf(20));  // usuwa wartość 20
ids.remove((Object) 20);          // to samo
```

Kompilator wybiera przeciążenie najbliższe typowi argumentu: dla `int` to `remove(int index)`, bez autoboxingu.

## Listy niemodyfikowalne i „dziwne”

| Skąd | `add`/`remove` | `set` | `null` | Widzi zmiany źródła |
|---|---|---|---|---|
| `new ArrayList<>(...)` | tak | tak | tak | nie (kopia) |
| `Arrays.asList(tablica)` | **nie** (`UnsupportedOperationException`) | tak | tak | **tak**: to widok na tablicę |
| `List.of(...)`, `List.copyOf(...)`, `stream.toList()` | nie | nie | nie (NPE) | nie |
| `Collections.unmodifiableList(list)` | nie | nie | tak | **tak**: to widok na `list` |

`Arrays.asList` zwraca listę o stałym rozmiarze, opartą na przekazanej tablicy. Chcesz normalną listę: `new ArrayList<>(Arrays.asList(...))` albo `new ArrayList<>(List.of(...))`.

`UnsupportedOperationException` przy `add` prawie zawsze znaczy: „ta lista jest niemodyfikowalna”.

## Iterowanie i usuwanie

```java
for (String s : list) { ... }                    // for-each: najczęściej
for (int i = 0; i < list.size(); i++) { ... }    // gdy potrzebny indeks
list.forEach(s -> System.out.println(s));
```

**Nie zmieniaj listy (add/remove) w pętli for-each po niej samej.** for-each używa iteratora, który sprawdza, czy lista nie zmieniła się „pod nim”, i rzuca `ConcurrentModificationException` (nie ma to nic wspólnego z wątkami). Gorzej: gdy usuniesz przedostatni element, pętla kończy się bez wyjątku i **pomija** ostatni element.

Poprawnie:

```java
list.removeIf(item -> bought.contains(item));      // najprościej

Iterator<String> it = list.iterator();              // ręcznie
while (it.hasNext()) {
    if (bought.contains(it.next())) {
        it.remove();                                // usuwa przez iterator: dozwolone
    }
}
```

Albo zbudować nową listę z elementami, które zostają.

## Set

```java
Set<String> tags = new HashSet<>();
tags.add("java");            // true
tags.add("java");            // false: już jest
tags.contains("java");
Set<String> common = new HashSet<>(a); common.retainAll(b);   // część wspólna
```

`HashSet` decyduje o duplikatach przez `hashCode` + `equals` (rozdział 06), `TreeSet` przez komparator.

## Map

```java
Map<String, Integer> stock = new HashMap<>();
stock.put("kawa", 3);                    // zwraca poprzednią wartość albo null
stock.get("kawa");                       // 3
stock.get("herbata");                    // null !
stock.getOrDefault("herbata", 0);        // 0
stock.containsKey("kawa");
stock.remove("kawa");
stock.putIfAbsent("cukier", 1);

for (Map.Entry<String, Integer> e : stock.entrySet()) {
    System.out.println(e.getKey() + " = " + e.getValue());
}
stock.keySet(); stock.values();
```

Metody, które oszczędzają `if`-ów:

```java
// licznik: dodaj 1, a gdy klucza nie ma, wstaw 1
counts.merge(word, 1, Integer::sum);

// mapa list: weź listę dla klucza, a gdy jej nie ma, utwórz i wstaw
byStudent.computeIfAbsent(student, k -> new ArrayList<>()).add(grade);
```

To samo bez nich:

```java
List<Integer> list = byStudent.get(student);
if (list == null) {
    list = new ArrayList<>();
    byStudent.put(student, list);
}
list.add(grade);
```

`int n = map.get(key);` przy braku klucza to `NullPointerException` (unboxing `null`).

## Kolekcje jako pola klasy

- Pole: `private final List<X> items = new ArrayList<>();`
- Getter: `return List.copyOf(items);` albo `Collections.unmodifiableList(items)`.
- Konstruktor z listą: `this.items = new ArrayList<>(items);` (gdy obiekt ją zmienia) albo `List.copyOf(items)` (gdy nie).
- Zwracaj pustą kolekcję zamiast `null`.

## Typowe błędy

- `Arrays.asList(...)` i potem `add`.
- `list.remove(item)` w for-each.
- `list.remove(id)` dla `List<Integer>` i `int id`.
- `HashMap`, a potem zdziwienie, że kolejność jest „losowa”.
- `map.get(key)` bez obsługi `null`.
- Zwracanie wewnętrznej listy z gettera.

## Pytania sprawdzające

1. Którą implementację wybierzesz: lista zadań w kolejności dodania; zbiór unikalnych tagów; słownik słowo -> liczba wystąpień, wypisywany alfabetycznie?
2. Co się stanie przy `Arrays.asList("a", "b").add("c")`? A przy `List.of("a").set(0, "b")`?
3. Dlaczego usuwanie w for-each rzuca `ConcurrentModificationException`? Podaj dwa poprawne sposoby.
4. Co usunie `list.remove(1)` dla `List<Integer> list = List.of(5, 1, 7)` (gdyby była modyfikowalna)?
5. Zapisz zliczanie słów przez `merge`.
6. Czym się różni `List.copyOf` od `Collections.unmodifiableList`?
