# 08. Generyki

Ćwiczenia: [14](../cwiczenia/14-generyczne-narzedzia/ZADANIE.md)

## Po co

Bez generyków (Java 1.4) kolekcja przechowywała `Object`:

```java
List names = new ArrayList();      // surowy typ (raw type)
names.add("Ala");
names.add(42);                     // kompilator nie protestuje
String s = (String) names.get(1);  // ClassCastException, dopiero w czasie działania
```

Z generykami typ elementu jest częścią typu kolekcji, kompilator pilnuje go za nas i rzutowanie znika:

```java
List<String> names = new ArrayList<>();   // <> : typ wywnioskowany z lewej strony
names.add(42);                            // błąd kompilacji
String s = names.get(0);                  // bez rzutowania
```

Surowe typy (`List`, `Map` bez `<...>`) są tylko dla zgodności ze starym kodem. Kompilator ostrzega o nich (`rawtypes`, `unchecked`). Nie ignoruj tych ostrzeżeń.

## Klasa generyczna

```java
public class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A first() { return first; }
    public B second() { return second; }

    public Pair<B, A> swap() {
        return new Pair<>(second, first);
    }
}

Pair<String, Integer> p = new Pair<>("Ala", 30);
String name = p.first();
int age = p.second();           // unboxing Integer -> int
```

Konwencja nazw parametrów typu: `T` (type), `E` (element), `K`, `V` (key, value), `R` (result), `A`, `B`.

Rekord też może być generyczny: `public record Pair<A, B>(A first, B second) {}`.

## Metoda generyczna

Parametr typu deklarowany przed typem zwracanym:

```java
public static <T> T firstOrDefault(List<T> list, T defaultValue) {
    return list.isEmpty() ? defaultValue : list.get(0);
}

String s = firstOrDefault(List.of("a"), "brak");   // T = String, wywnioskowane
```

Ograniczenie typu: `<T extends Comparable<T>>` znaczy „dowolny T, który da się porównać z T”:

```java
public static <T extends Comparable<T>> T max(List<T> list) { ... }
```

## Generyki nie są kowariantne

`Integer` jest podtypem `Number`, ale **`List<Integer>` nie jest podtypem `List<Number>`**. Gdyby był:

```java
List<Integer> ints = new ArrayList<>();
List<Number> nums = ints;       // (nie kompiluje się, i dobrze)
nums.add(2.5);                  // do listy Integerów trafiłby Double
Integer i = ints.get(0);        // ClassCastException
```

Dlatego metoda `double sum(List<Number> numbers)` nie przyjmie `List<Integer>`. Do tego służą wildcardy.

## Wildcardy: `? extends` i `? super`

- `List<? extends Number>`: lista **czegoś**, co jest `Number` albo podtypem (`List<Integer>`, `List<Double>`, `List<Number>`). Można z niej **czytać** jako `Number`. Nie można do niej nic dodać (poza `null`), bo nie wiadomo, jaki to dokładnie typ.
- `List<? super Integer>`: lista czegoś, co jest `Integer` albo **nadtypem** (`List<Integer>`, `List<Number>`, `List<Object>`). Można do niej **dodawać** `Integer`. Czytać tylko jako `Object`.
- `List<?>`: lista czegokolwiek, tylko do odczytu jako `Object`.

Reguła **PECS: Producer Extends, Consumer Super.**

- Parametr, z którego metoda **bierze** elementy (producent) to `? extends T`.
- Parametr, do którego metoda **wkłada** elementy (konsument) to `? super T`.

```java
public static <T> void copyAll(List<? extends T> source, List<? super T> target) {
    for (T element : source) {
        target.add(element);
    }
}

List<Number> nums = new ArrayList<>();
copyAll(List.of(1, 2), nums);        // T = Integer: źródło List<Integer>, cel List<Number>

public static <T> T max(Collection<? extends T> items, Comparator<? super T> comparator)
// comparator jest "konsumentem" T: dostaje T do porównania, więc Comparator<Fruit>
// nadaje się do porównywania jabłek
```

Tak wyglądają sygnatury w JDK: `Collections.max(Collection<? extends T>, Comparator<? super T>)`, `List.addAll(Collection<? extends E>)`.

Wildcardy pisz w **parametrach** metod. W typach zwracanych zwykle nie (utrudniają życie wołającemu).

## Wymazywanie typów (type erasure)

Parametry typów istnieją tylko w czasie kompilacji. W kodzie bajtowym `List<String>` i `List<Integer>` to ta sama klasa `List`. Skutki:

- nie da się `new T()` ani `new T[10]`,
- nie da się `obj instanceof List<String>` (można `instanceof List<?>`),
- nie da się przeciążyć `void f(List<String>)` i `void f(List<Integer>)`,
- typy proste nie mogą być argumentami typu: `List<int>` nie istnieje, jest `List<Integer>`.

## Typowe błędy

- Surowe typy (`List list = new ArrayList();`), ignorowanie ostrzeżeń.
- Parametr `List<Number>` tam, gdzie powinno być `List<? extends Number>`.
- `@SuppressWarnings("unchecked")`, żeby „uciszyć” kompilator, zamiast poprawić typy.
- Metoda zwracająca `Object`, a potem rzutowanie wszędzie przy wywołaniach.

## Pytania sprawdzające

1. Dlaczego `List<Object> list = new ArrayList<String>();` się nie kompiluje?
2. Do której listy można dodać `Integer`: `List<? extends Number>` czy `List<? super Number>`? Dlaczego?
3. Wyjaśnij PECS na przykładzie `copyAll(source, target)`.
4. Co to jest surowy typ i dlaczego kompilator ostrzega?
5. Dlaczego `new T()` się nie kompiluje?
