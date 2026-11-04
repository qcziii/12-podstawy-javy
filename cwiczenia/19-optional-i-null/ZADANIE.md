# 19. Profil użytkownika: NullPointerException i NoSuchElementException

Poziom: **łatwy/średni (3 błędy)** · Rodzaj: **napraw** · Teoria: [12. Optional i null](../../teoria/12-optional-i-null.md)

## Uruchomienie

```bash
./cwiczenia/19-optional-i-null/check.sh
```

## Zgłoszenie

Strona profilu korzysta z `UserDirectory`. Zgłoszenia:

1. Wejście na profil nieistniejącego użytkownika kończy się `NullPointerException` (choć metoda `find` zwraca `Optional`, „więc nie może być null”).
2. Użytkownik bez pseudonimu ma błąd 500 zamiast swojego loginu.
3. Monitoring CDN pokazuje, że domyślny avatar jest pobierany przy **każdym** wejściu na profil, także użytkowników z własnym avatarem.

**Oczekiwane:**

- `find` nigdy nie zwraca `null`,
- `displayName`: pseudonim, a gdy go nie ma, login; nieznany użytkownik: `"Nieznany użytkownik"`,
- `emailDomain`: domena albo pusty `Optional`,
- `avatar`: domyślny avatar jest pobierany tylko wtedy, gdy użytkownik nie ma własnego.

## Wymagania

- Nie zmieniaj `AvatarStore` i `UserAccount`.
- Bez `Optional.get()` i bez `isPresent()` + `get()`: użyj `map`, `orElse`, `orElseGet`.

## Pytania dodatkowe

1. Czym się różni `Optional.of(x)` od `Optional.ofNullable(x)`?
2. Czym się różni `orElse(metoda())` od `orElseGet(() -> metoda())`? Kiedy różnica ma znaczenie?
3. Dlaczego `Optional` nie powinien być polem klasy ani parametrem metody, tylko typem zwracanym?
4. Co zwraca `Optional.of("a").map(s -> null)`?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
