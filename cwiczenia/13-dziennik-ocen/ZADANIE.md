# 13. Dziennik ocen na mapach

Poziom: **średni** · Rodzaj: **napisz** · Teoria: [07. Kolekcje](../../teoria/07-kolekcje.md)

## Uruchomienie

```bash
./cwiczenia/13-dziennik-ocen/check.sh
```

## Zadanie

Napisz `GradeBook`: dziennik ocen uczniów z przedmiotów. Oceny są od 1 do 6. Szkielet klasy jest gotowy, metody rzucają `UnsupportedOperationException`. Pola wybierz sam.

| Metoda | Co robi |
|---|---|
| `addGrade(student, subject, grade)` | dodaje ocenę; ocena spoza 1..6: `IllegalArgumentException` |
| `grades(student, subject)` | oceny w kolejności dodania; brak ocen: pusta lista (nie `null`) |
| `average(student)` | średnia wszystkich ocen ucznia; nieznany uczeń: `IllegalArgumentException` |
| `averagesBySubject(student)` | średnia z każdego przedmiotu, przedmioty alfabetycznie; nieznany uczeń: pusta mapa |
| `students()` | uczniowie alfabetycznie, bez powtórzeń |
| `histogram()` | ile razy padła każda ocena, oceny rosnąco, tylko te, które padły |

Zwrócone listy nie mogą pozwalać na zmianę dziennika.

## Wymagania

- Bez streamów (będą w ćwiczeniu 18): pętle i metody `Map`.
- Użyj przynajmniej raz `computeIfAbsent`, `getOrDefault` i `merge`.

## Wskazówki

- Dobra struktura: `Map<String, Map<String, List<Integer>>>` (uczeń -> przedmiot -> oceny). Która implementacja mapy daje klucze posortowane?

## Pytania dodatkowe

1. Czym się różni `HashMap`, `LinkedHashMap` i `TreeMap`? W jakiej kolejności zwracają klucze?
2. Zapisz `addGrade` bez `computeIfAbsent` (przez `get`, sprawdzenie `null` i `put`). Która wersja jest czytelniejsza?
3. Co zwraca `map.get(klucz)` dla klucza, którego nie ma? Dlaczego to częsta przyczyna `NullPointerException`?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
