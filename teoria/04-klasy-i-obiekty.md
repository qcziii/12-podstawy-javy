# 04. Klasy i obiekty

Ćwiczenia: [06](../cwiczenia/06-wspolny-koszyk/ZADANIE.md), [07](../cwiczenia/07-rabaty-polimorfizm/ZADANIE.md), [12](../cwiczenia/12-remove-po-indeksie/ZADANIE.md), [17](../cwiczenia/17-niezmienne-zamowienie/ZADANIE.md)

## Klasa i obiekt

Klasa to szablon: jakie dane (pola) i jakie zachowanie (metody). Obiekt to konkretny egzemplarz utworzony przez `new`.

```java
public class Account {
    private final String number;   // pole instancji: każdy obiekt ma własne
    private long balance;

    public Account(String number) {     // konstruktor
        this.number = number;           // this.number: pole, number: parametr
    }

    public void deposit(long amount) {  // metoda instancji
        if (amount <= 0) {
            throw new IllegalArgumentException("Kwota musi być dodatnia: " + amount);
        }
        balance += amount;
    }

    public long balance() {
        return balance;
    }
}

Account a = new Account("PL01");
Account b = new Account("PL02");
a.deposit(100);           // b.balance() dalej 0
```

## Konstruktory

- Klasa bez żadnego konstruktora dostaje domyślny, bezargumentowy. Gdy dopiszesz jakikolwiek konstruktor, domyślny znika.
- Konstruktor może wołać inny konstruktor tej samej klasy przez `this(...)` (w pierwszej linii).
- Pola niezainicjalizowane mają wartości domyślne: `0`, `false`, `null`. Zmienne lokalne nie mają wartości domyślnych (kompilator każe je zainicjalizować).
- Konstruktor to dobre miejsce na **walidację**: obiekt, który powstał, ma być poprawny.

## `static`: należy do klasy, nie do obiektu

Pole `static` istnieje **w jednej kopii** dla całej klasy, wspólnej dla wszystkich obiektów. Pole zwykłe (instancji) każdy obiekt ma własne.

```java
public class Cart {
    private static int nextId = 1;    // jedno na całą klasę: licznik numerów
    private final int id;             // każdy koszyk ma swój numer
    private final List<String> products = new ArrayList<>();  // każdy koszyk ma swoją listę

    public Cart() {
        id = nextId++;
    }
}
```

Gdyby `products` było `static`, wszystkie koszyki dzieliłyby jedną listę. To jeden z najczęstszych błędów początkujących: `static` dopisane, „bo inaczej nie kompilowało się w `main`”.

Metoda `static` nie ma `this`, więc nie widzi pól instancji. Woła się ją przez nazwę klasy: `Math.max(a, b)`, `Cart.createdCount()`.

Kiedy `static` jest dobre:

- stałe: `public static final int MAX_DAYS = 30;`
- metody pomocnicze, które nie potrzebują stanu: `Math.abs`, `List.of`, `Money.format`,
- metody fabrykujące: `Version.parse("1.2.3")`, `Pair.of(a, b)`,
- świadomie wspólny stan, jak licznik `nextId`.

## Modyfikatory dostępu i enkapsulacja

| Modyfikator | Widoczne w |
|---|---|
| `private` | tylko w tej klasie |
| (brak) | w tym samym pakiecie |
| `protected` | w pakiecie i w klasach dziedziczących |
| `public` | wszędzie |

**Enkapsulacja**: pola są `private`, a obiekt sam pilnuje swoich reguł (saldo nie spada poniżej zera, ocena jest od 1 do 6). Z zewnątrz można zrobić tylko to, na co pozwalają metody publiczne.

Enkapsulację łamie zwracanie wewnętrznej, zmiennej kolekcji:

```java
public List<Integer> trackIds() {
    return trackIds;                 // źle: wołający może zrobić clear() na naszej liście
}

public List<Integer> trackIds() {
    return List.copyOf(trackIds);    // dobrze: niemodyfikowalna kopia
}
```

To samo w drugą stronę: lista przekazana do konstruktora należy do wołającego, który może ją potem zmienić. Jeśli obiekt ma być od niej niezależny, robi **kopię defensywną**: `this.lines = List.copyOf(lines);`.

## `final`

- `final` przy polu: można przypisać tylko raz (w deklaracji albo w konstruktorze).
- `final` przy zmiennej lokalnej: jak wyżej.
- `final` przy klasie: nie można po niej dziedziczyć (`String` jest `final`).
- `final` przy metodzie: nie można jej nadpisać.

`final` dotyczy **referencji**, nie obiektu: `private final List<String> items = new ArrayList<>();` nie pozwala przypisać innej listy, ale `items.add("x")` działa.

## Niemutowalność

Obiekt niemutowalny nie zmienia się po utworzeniu. Przepis:

1. wszystkie pola `private final`,
2. brak setterów; „zmiana” zwraca nowy obiekt (`withX(...)`),
3. kopie defensywne zmiennych argumentów i zwracanych kolekcji,
4. klasa `final` (albo rekord, rozdział 10).

Zalety: można go bezpiecznie przekazywać i współdzielić, używać jako klucza w `HashMap`, nie trzeba się zastanawiać, kto go zmienił.

## Gettery i nazewnictwo

Klasyczna konwencja JavaBeans: `getName()`, `isActive()`, `setName(...)`. Rekordy i część nowszego kodu używają krótszej: `name()`. Ważniejsze od wyboru jest trzymanie się jednej w projekcie. Nie dopisuj setterów „na zapas”: każdy setter to miejsce, w którym obiekt może stać się niepoprawny.

## Typowe błędy

- Pole `static`, które powinno być polem instancji.
- Pole publiczne zamiast prywatnego.
- Getter zwracający wewnętrzną listę.
- Brak walidacji w konstruktorze; obiekt może powstać w niepoprawnym stanie.
- Zmienna lokalna o tej samej nazwie co pole, bez `this.` (przypisanie do parametru zamiast do pola).

## Pytania sprawdzające

1. Ile kopii pola `static int counter` istnieje przy 100 obiektach klasy? A pola `int value`?
2. Dlaczego metoda `static` nie może odczytać pola instancji?
3. Co jest nie tak z: `public List<String> getItems() { return items; }`?
4. `private final List<String> items = new ArrayList<>();`: czy `items.add("x")` się skompiluje? A `items = new ArrayList<>()`?
5. Wymień cztery cechy klasy niemutowalnej.
