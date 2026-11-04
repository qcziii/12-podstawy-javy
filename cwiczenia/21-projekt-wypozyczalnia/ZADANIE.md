# 21. Projekt: wypożyczalnia sprzętu

Poziom: **trudny** · Rodzaj: **napisz** · Teoria: [07. Kolekcje](../../teoria/07-kolekcje.md), [09. Wyjątki](../../teoria/09-wyjatki.md), [10. Enumy i rekordy](../../teoria/10-enumy-i-rekordy.md), [11. Lambdy i streamy](../../teoria/11-lambdy-i-streamy.md), [12. Optional i null](../../teoria/12-optional-i-null.md)

## Uruchomienie

```bash
./cwiczenia/21-projekt-wypozyczalnia/check.sh
```

## Zadanie

Napisz `RentalService`: wypożyczalnię sprzętu sportowego. Gotowe są: `EquipmentType` (enum z ceną za dzień w groszach), rekordy `Equipment` i `Rental`, wyjątek `EquipmentNotFoundException`. Ćwiczenie łączy wszystko z poprzednich: mapy, rekordy, enumy, wyjątki, `Optional`, streamy.

Reguły:

1. `addEquipment`: dodaje sprzęt. Numer (id) już zajęty: `IllegalArgumentException`.
2. `rent(id, klient, dni)`: wypożycza. Nieznany numer: `EquipmentNotFoundException`. Już wypożyczony: `IllegalStateException`. Liczba dni spoza 1..30: `IllegalArgumentException`. Po błędzie nic się nie zmienia.
3. `returnEquipment(id)`: zwrot, zwraca koszt: dni x cena za dzień; od 7 dni 10% taniej (rabat zaokrąglony w dół do grosza). Nieznany numer: `EquipmentNotFoundException`. Niewypożyczony: `IllegalStateException`.
4. `rentalOf(id)`: aktywne wypożyczenie jako `Optional`.
5. `available(typ)`: wolny sprzęt danego typu, posortowany po numerze.
6. `revenue()`: suma kosztów wszystkich zwrotów.
7. `activeRentalsByCustomer()`: klient -> liczba aktywnych wypożyczeń, klienci alfabetycznie.
8. `availableCountByType()`: typ -> liczba wolnych sztuk, wszystkie typy (także 0), w kolejności stałych.

## Wymagania

- Kolejność sprawdzeń w `rent`: najpierw czy sprzęt istnieje, potem czy jest wolny, potem liczba dni.
- Stałe (30 dni, 7 dni, 10%) jako nazwane stałe `private static final`, nie „magiczne liczby” w kodzie.
- Metody prywatne zamiast powtarzania kodu (np. „znajdź sprzęt albo rzuć wyjątek”).

## Pytania dodatkowe

1. Jakie struktury danych wybrałeś i dlaczego? Jaka jest złożoność `rent` i `available`?
2. Dlaczego `EquipmentNotFoundException` jest unchecked? Kiedy zrobiłbyś go checked?
3. Gdzie trzymasz stan wypożyczenia: w `Equipment`, w osobnej mapie, w czymś innym? Jakie są zalety rekordów niemutowalnych przy takim wyborze?
4. Co by trzeba zmienić, żeby wypożyczalnia pamiętała historię wszystkich wypożyczeń klienta?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
