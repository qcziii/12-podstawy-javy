# 12. Optional i null

Ćwiczenia: [19](../cwiczenia/19-optional-i-null/ZADANIE.md), [18](../cwiczenia/18-raport-sprzedazy/ZADANIE.md), [21](../cwiczenia/21-projekt-wypozyczalnia/ZADANIE.md)

## Problem

```java
User user = repository.find(login);     // może zwrócić null
return user.getName();                  // NullPointerException, gdy nie ma użytkownika
```

Z sygnatury `User find(String)` nie widać, że wynik może być `null`. Każdy wołający musi o tym pamiętać.

## Optional

`Optional<T>` to pudełko, które **jawnie** mówi: „może być wartość, może jej nie być”.

```java
public Optional<User> find(String login) {
    return Optional.ofNullable(users.get(login));
}
```

Tworzenie:

| | |
|---|---|
| `Optional.of(x)` | `x` nie może być `null` (inaczej NPE) |
| `Optional.ofNullable(x)` | pusty, gdy `x == null` |
| `Optional.empty()` | pusty |

**Metoda zwracająca `Optional` nigdy nie zwraca `null`.** Zwrócenie `null` z takiej metody to najgorsze z obu światów.

## Używanie bez get()

```java
String name = find(login)
        .map(User::nickname)                 // Optional<String>; jeśli nickname null -> pusty
        .orElse("Gość");

String domain = find(login)
        .map(User::email)
        .map(e -> e.substring(e.indexOf('@') + 1))
        .orElse("brak");

find(login).ifPresent(u -> mailer.send(u));
find(login).ifPresentOrElse(u -> show(u), () -> showNotFound());

User user = find(login).orElseThrow(() -> new UserNotFoundException(login));

Optional<User> admin = find(login).filter(User::isAdmin);
```

| Metoda | Co robi |
|---|---|
| `map(f)` | przekształca wartość, jeśli jest; gdy `f` zwróci `null`, wynik jest pusty |
| `flatMap(f)` | jak `map`, gdy `f` sama zwraca `Optional` |
| `filter(p)` | zostawia wartość, jeśli spełnia warunek |
| `orElse(x)` | wartość albo `x` |
| `orElseGet(() -> x)` | wartość albo wynik funkcji (wołanej tylko gdy pusto) |
| `orElseThrow()` / `orElseThrow(() -> e)` | wartość albo wyjątek |
| `ifPresent(c)` | zrób coś, jeśli jest |
| `isPresent()`, `isEmpty()` | sprawdzenie |
| `get()` | wartość albo `NoSuchElementException`; unikaj |

`get()` bez sprawdzenia to ten sam błąd co NPE, tylko z inną nazwą. `if (opt.isPresent()) { x = opt.get(); }` działa, ale `map`/`orElse` są krótsze i czytelniejsze.

## orElse a orElseGet

```java
.orElse(loadDefaultAvatar())          // loadDefaultAvatar() wołane ZAWSZE, nawet gdy wartość jest
.orElseGet(() -> loadDefaultAvatar()) // wołane tylko, gdy Optional jest pusty
.orElseGet(this::loadDefaultAvatar)
```

Argument `orElse` jest wyliczany przed wywołaniem metody (tak działa każde wywołanie w Javie). Dla stałej (`orElse("Gość")`) to bez znaczenia. Dla kosztownego wywołania albo wywołania z efektem ubocznym (zapytanie do bazy, pobranie z sieci, zapis) użyj `orElseGet`.

## Kiedy Optional, a kiedy nie

Tak:

- typ zwracany metody, która może nie mieć wyniku (`find`, `max`, `findFirst`).

Nie:

- pole klasy (pole może być po prostu `null`, a getter może zwracać `Optional.ofNullable(pole)`),
- parametr metody (wołający musiałby opakowywać; lepiej przeciążenie albo `null` z dokumentacją),
- kolekcje: zamiast `Optional<List<X>>` zwracaj pustą listę,
- `Optional<Optional<X>>`.

Wersje dla typów prostych: `OptionalInt`, `OptionalLong`, `OptionalDouble` (`getAsInt()`, `orElse`). Zwracają je `IntStream.max()`, `average()` itp.

## Inne sposoby na null

```java
Objects.requireNonNull(param, "param");      // szybki błąd na wejściu metody
Objects.requireNonNullElse(x, "domyślny");   // x albo domyślny
Objects.equals(a, b);                        // porównanie bezpieczne dla null
"stała".equals(zmienna);                     // stała z lewej strony
map.getOrDefault(key, 0);                    // zamiast get + sprawdzenie
```

Dobre zasady w projekcie: nie zwracaj `null` z metod zwracających kolekcje (pusta kolekcja) ani z `find` (`Optional`); sprawdzaj `null` na granicach (wejście do metody publicznej, dane z zewnątrz).

## Typowe błędy

- Metoda typu `Optional` zwracająca `null`.
- `Optional.of(x)`, gdy `x` może być `null`.
- `opt.get()` bez sprawdzenia.
- `orElse(kosztowneWywołanie())`.
- `Optional` jako pole albo parametr.

## Pytania sprawdzające

1. Czym się różni `Optional.of` od `Optional.ofNullable`?
2. Zapisz bez `get()`: „pseudonim użytkownika, a gdy go nie ma, login; nieznany użytkownik: »Gość«”.
3. Kiedy `orElse` i `orElseGet` zachowują się inaczej?
4. Co zwraca `Optional.of("x").map(s -> null)`?
5. Dlaczego `Optional` nie powinien być polem klasy?
