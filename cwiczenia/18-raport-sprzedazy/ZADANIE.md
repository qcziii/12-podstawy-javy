# 18. Raport sprzedaży na streamach

Poziom: **średni** · Rodzaj: **napisz** · Teoria: [11. Lambdy i streamy](../../teoria/11-lambdy-i-streamy.md), [12. Optional i null](../../teoria/12-optional-i-null.md)

## Uruchomienie

```bash
./cwiczenia/18-raport-sprzedazy/check.sh
```

## Zadanie

Napisz `SalesReport`: raport z listy sprzedaży (`Sale`: region, sprzedawca, produkt, liczba sztuk, cena jednostkowa w groszach). Opis każdej metody jest w komentarzu w kodzie, przykładowe dane i wyniki w teście.

| Metoda | Wynik |
|---|---|
| `totalRevenue()` | suma przychodu |
| `revenueByRegion()` | region -> przychód, regiony alfabetycznie |
| `topProducts(n)` | n produktów z największą liczbą sprzedanych sztuk, przy remisie alfabetycznie |
| `sellersIn(region)` | sprzedawcy z regionu, alfabetycznie, bez powtórzeń |
| `bestSeller()` | sprzedawca z największym przychodem, `Optional` |
| `averageQuantity()` | średnia liczba sztuk na sprzedaż, 0.0 dla pustego raportu |
| `productsByRegion()` | region -> lista produktów w kolejności sprzedaży |
| `bigSales(min)` | opisy sprzedaży z przychodem >= min: `"Ala: Kawa x 3"` |

## Wymagania

- Każda metoda jednym albo dwoma streamami. Bez pętli `for` i `while` (sprawdza `check.sh`).
- Przydadzą się: `filter`, `map`, `mapToLong`, `sum`, `average`, `distinct`, `sorted`, `limit`, `toList`, `Collectors.groupingBy`, `summingLong`, `summingInt`, `mapping`, `Map.Entry.comparingByValue`.

## Pytania dodatkowe

1. Co zwraca `IntStream.average()` i dlaczego nie `double`?
2. `groupingBy(f)` zwraca `HashMap`. Jak dostać `TreeMap`? (trzyargumentowa wersja)
3. Dlaczego ten kod nic nie wypisuje: `list.stream().peek(System.out::println).map(x -> x * 2);`?
4. Czym się różni `Collectors.toMap` od `groupingBy`? Co robi `toMap`, gdy trafi na ten sam klucz drugi raz?
5. Czym się różni `stream.toList()` od `collect(Collectors.toList())`?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
