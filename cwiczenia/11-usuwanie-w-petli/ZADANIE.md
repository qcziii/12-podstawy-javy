# 11. Lista zakupów: wyjątek przy dodawaniu i przy odhaczaniu

Poziom: **łatwy (2 błędy)** · Rodzaj: **napraw** · Teoria: [07. Kolekcje](../../teoria/07-kolekcje.md)

## Uruchomienie

```bash
./cwiczenia/11-usuwanie-w-petli/check.sh
```

## Zgłoszenie

`ShoppingList` to prosta lista zakupów. Zgłoszenia:

1. Dodanie produktu do listy kończy się `UnsupportedOperationException`.
2. Odhaczanie kupionych produktów (`tickOff`) też rzuca wyjątek. Po naprawie pierwszego błędu wyjątek będzie inny (`ConcurrentModificationException`), a w jednym przypadku wyjątku nie ma wcale, tylko jeden produkt zostaje na liście.
3. Gdy ktoś zmieni tablicę przekazaną do konstruktora, zmienia się lista zakupów.

**Oczekiwane:** do listy da się dodawać, odhaczanie usuwa wszystkie kupione produkty, lista nie zależy od tablicy, z której powstała.

## Wskazówki

- Jaką listę zwraca `Arrays.asList`? Przeczytaj jej dokumentację.
- Zanim naprawisz `tickOff`, wyjaśnij, dlaczego dla listy `[mleko, chleb, masło]` i kupionych `{chleb, masło}` pętla nie rzuca wyjątku, tylko kończy się za wcześnie.

## Pytania dodatkowe

1. Czym się różnią `Arrays.asList(...)`, `List.of(...)` i `new ArrayList<>(...)`? Która z nich pozwala na `add`, która na `set`, która na nic?
2. Jakie są trzy poprawne sposoby usuwania elementów w trakcie przechodzenia po liście? (`Iterator.remove`, `removeIf`, nowa lista)
3. Czy `ConcurrentModificationException` ma coś wspólnego z wątkami?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
