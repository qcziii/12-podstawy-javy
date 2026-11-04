# 12. Playlista usuwa nie ten utwór, a z zewnątrz da się ją zepsuć

Poziom: **łatwy/średni (2 błędy)** · Rodzaj: **napraw** · Teoria: [04. Klasy i obiekty](../../teoria/04-klasy-i-obiekty.md), [07. Kolekcje](../../teoria/07-kolekcje.md)

## Uruchomienie

```bash
./cwiczenia/12-remove-po-indeksie/check.sh
```

## Zgłoszenie

`Playlist` trzyma identyfikatory utworów w `List<Integer>`. Zgłoszenia:

1. `removeTrack(20)` na playliście `[10, 20, 30]` rzuca `IndexOutOfBoundsException`. `removeTrack(0)` na `[3, 0, 2]` usuwa utwór 3 zamiast 0. Ktoś dopisał wcześniej `contains`, „żeby nie było wyjątku”, ale to nie pomogło.
2. Inny moduł wziął listę z `trackIds()` i zrobił na niej `clear()`. Playlista użytkownika zniknęła.

**Oczekiwane:** `removeTrack` usuwa pierwsze wystąpienie utworu o podanym **id**, `removeAt` dalej usuwa po **pozycji**. Listy zwróconej z `trackIds()` nie da się użyć do zmiany playlisty.

## Pytania dodatkowe

1. `List<Integer>` ma dwie metody `remove`: `remove(int index)` i `remove(Object o)`. Którą wybiera kompilator dla `list.remove(id)`, gdy `id` jest `int`, a którą, gdy jest `Integer`?
2. Czym się różni `List.copyOf(list)` od `Collections.unmodifiableList(list)`? Co zobaczy wołający po późniejszej zmianie playlisty w każdym z tych przypadków?
3. Co to jest enkapsulacja i dlaczego zwracanie wewnętrznej, zmiennej listy ją łamie?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
