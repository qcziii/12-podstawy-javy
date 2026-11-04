# 09. Klient po zmianie e-maila znika z rejestru

Poziom: **średni** · Rodzaj: **napraw** · Teoria: [06. equals, hashCode, Comparator](../../teoria/06-equals-hashcode-comparator.md), [07. Kolekcje](../../teoria/07-kolekcje.md)

## Uruchomienie

```bash
./cwiczenia/09-zgubiony-klucz/check.sh
```

## Zgłoszenie

`CustomerRegistry` trzyma klientów w `HashSet`. Klient jest identyfikowany po e-mailu (`equals` i `hashCode` w `Customer` są oparte na e-mailu i są poprawne). Po zmianie e-maila przez `changeEmail`:

- `isRegistered(klient)` zwraca `false`, choć klient jest w rejestrze (`count()` go liczy),
- `remove(klient)` nie usuwa klienta,
- da się zarejestrować drugiego klienta z tym samym (nowym) e-mailem.

Dodatkowo `changeEmail` pozwala zmienić e-mail na już zajęty przez innego klienta i zmienić e-mail komuś, kto nie jest zarejestrowany.

**Oczekiwane:** po zmianie e-maila rejestr działa normalnie. `changeEmail` rzuca `IllegalArgumentException`, gdy klient nie jest zarejestrowany albo gdy nowy e-mail jest zajęty, i wtedy niczego nie zmienia.

## Wymagania

- Nie zmieniaj `Customer` (w szczególności `equals` i `hashCode`).

## Wskazówki

- Narysuj `HashSet` jako tablicę „kubełków”. W którym kubełku leży klient po dodaniu? W którym `contains` go szuka po zmianie e-maila?

## Pytania dodatkowe

1. Dlaczego zmiana pola, od którego zależy `hashCode`, psuje `HashSet` i `HashMap`?
2. Jakie są dwa sposoby, żeby tego uniknąć na etapie projektu klasy? (podpowiedź: niemutowalność; klucz, który się nie zmienia, np. id)
3. Czy `TreeSet` z komparatorem po e-mailu miałby ten sam problem?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
