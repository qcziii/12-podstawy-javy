# 05. Dziedziczenie, interfejsy, polimorfizm

Ćwiczenia: [07](../cwiczenia/07-rabaty-polimorfizm/ZADANIE.md), [08](../cwiczenia/08-duplikaty-w-secie/ZADANIE.md)

## Dziedziczenie

```java
public class Animal {
    protected final String name;

    public Animal(String name) {
        this.name = name;
    }

    public String sound() {
        return "...";
    }
}

public class Dog extends Animal {
    public Dog(String name) {
        super(name);                // konstruktor klasy bazowej, musi być pierwszy
    }

    @Override
    public String sound() {         // nadpisanie
        return "Hau";
    }
}
```

- Klasa dziedziczy po **jednej** klasie (`extends`). Każda klasa dziedziczy pośrednio po `Object` (stąd `equals`, `hashCode`, `toString`).
- `super(...)` woła konstruktor bazowy, `super.metoda()` wersję metody z klasy bazowej.
- Dziedziczenie to relacja „jest”: pies **jest** zwierzęciem. Jeśli to relacja „ma” (samochód ma silnik), użyj pola (kompozycja), nie dziedziczenia.

## Polimorfizm

Zmienna typu bazowego może wskazywać obiekt klasy pochodnej, a wywołanie metody trafia do wersji z **rzeczywistej** klasy obiektu (wiązanie dynamiczne):

```java
List<Animal> animals = List.of(new Dog("Burek"), new Cat("Mruczek"));
for (Animal a : animals) {
    System.out.println(a.sound());   // "Hau", "Miau": kod nie wie i nie musi wiedzieć, jaki to zwierz
}
```

To jest sedno programowania obiektowego: zamiast pytać obiekt o typ i decydować w `if`-ach, każesz mu coś zrobić, a on robi to po swojemu. Nowy rodzaj obiektu to nowa klasa, bez zmiany kodu, który go używa.

Kod „przed” polimorfizmem:

```java
if (shape instanceof Circle c) {
    area = Math.PI * c.r() * c.r();
} else if (shape instanceof Square s) {
    area = s.side() * s.side();
}   // każdy nowy kształt: kolejny else if, w każdym miejscu, które liczy pole
```

„Po”: `area = shape.area();`, a każdy kształt ma własne `area()`.

## Nadpisanie (override) a przeciążenie (overload)

- **Nadpisanie**: metoda w klasie pochodnej ma **tę samą sygnaturę** (nazwa i typy parametrów) co w bazowej. Wybór wersji: w czasie działania, po rzeczywistym typie obiektu.
- **Przeciążenie**: kilka metod o tej samej nazwie, ale **innych parametrach**. Wybór wersji: w czasie kompilacji, po zadeklarowanych typach argumentów.

Klasyczna pułapka:

```java
public class Product {
    public boolean equals(Product other) { ... }   // PRZECIĄŻENIE, nie nadpisanie!
}
```

`Object.equals` ma parametr `Object`. Metoda z parametrem `Product` to nowa, osobna metoda. `HashSet`, `HashMap`, `List.contains` wołają `equals(Object)`, czyli wersję z `Object`, która porównuje referencje. Twoja metoda nie jest nigdy przez nie wołana.

**Zawsze pisz `@Override`** przy metodach, które mają nadpisywać. Wtedy kompilator zgłosi błąd, jeśli w rzeczywistości nic nie nadpisują.

## Klasy abstrakcyjne

```java
public abstract class Shape {
    public abstract double area();          // bez ciała: każda podklasa musi napisać

    public String describe() {              // wspólna implementacja
        return getClass().getSimpleName() + " o polu " + area();
    }
}
```

Nie można zrobić `new Shape()`. Klasa abstrakcyjna może mieć pola, konstruktory i gotowe metody.

## Interfejsy

Interfejs to kontrakt: lista metod, które klasa obiecuje mieć.

```java
public interface Discount {
    long apply(long price);
    String description();

    default boolean isFree(long price) {     // metoda domyślna (Java 8+): z gotową implementacją
        return apply(price) == 0;
    }
}

public class PercentageDiscount implements Discount { ... }
```

- Klasa może implementować **wiele** interfejsów.
- Metody interfejsu są domyślnie `public abstract`. Pola są stałymi (`public static final`).
- Metody `default` i `static` mają ciało.
- Interfejs z jedną metodą abstrakcyjną to **interfejs funkcyjny** i można go zaimplementować lambdą (rozdział 11).

| | Interfejs | Klasa abstrakcyjna |
|---|---|---|
| ile można „dziedziczyć” | wiele | jedną |
| stan (pola instancji) | nie | tak |
| konstruktor | nie | tak |
| kiedy | „potrafi coś robić” (kontrakt), różne niezwiązane klasy | wspólny kod i stan dla blisko spokrewnionych klas |

W praktyce: zaczynaj od interfejsu. Klasa abstrakcyjna, gdy kilka implementacji naprawdę dzieli kod i pola.

## Programowanie do interfejsu

```java
List<String> names = new ArrayList<>();      // dobrze: reszta kodu zna tylko List
ArrayList<String> names2 = new ArrayList<>(); // działa, ale wiąże z konkretną implementacją
```

Parametry i typy zwracane deklaruj jako interfejsy (`List`, `Map`, `Discount`), konkretną klasę wybieraj tylko przy `new`.

## Rzutowanie i instanceof

```java
Animal a = new Dog("Burek");
Dog d = (Dog) a;                 // rzutowanie w dół: działa
Cat c = (Cat) a;                 // ClassCastException w czasie działania

if (a instanceof Dog dog) {      // pattern matching (Java 16+): sprawdzenie i rzutowanie naraz
    dog.fetch();
}
```

`instanceof` jest potrzebne w `equals` (rozdział 06) i przy obsłudze danych z zewnątrz. Długie łańcuchy `instanceof` w logice biznesowej to zwykle sygnał, że brakuje polimorfizmu (albo że pasuje `sealed` + `switch`, rozdział 10).

## Klasy anonimowe

Jednorazowa implementacja interfejsu w miejscu użycia:

```java
Discount halfPrice = new Discount() {
    @Override
    public long apply(long price) { return price / 2; }

    @Override
    public String description() { return "Pół ceny"; }
};
```

## Typowe błędy

- Brak `@Override`, a w efekcie przeciążenie zamiast nadpisania (`equals(Product)`, `hashcode()` małą literą).
- Dziedziczenie tam, gdzie wystarczy pole (kompozycja).
- `if (x instanceof A) ... else if (x instanceof B) ...` zamiast metody w `A` i `B`.
- Wołanie z konstruktora metody, którą podklasa nadpisuje (podklasa nie jest jeszcze zainicjalizowana).

## Pytania sprawdzające

1. Czym się różni nadpisanie od przeciążenia? Kiedy wybierana jest wersja metody w każdym przypadku?
2. Co daje adnotacja `@Override`?
3. Dlaczego `public boolean equals(Product p)` nie działa z `HashSet`?
4. Interfejs czy klasa abstrakcyjna: co wybierzesz dla „rabatu”, a co dla „konta bankowego z historią operacji”, gdy są dwa rodzaje kont? Dlaczego?
5. Co to jest interfejs funkcyjny?
