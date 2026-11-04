# 05. Numer wersji 1.2.3 jest „niepoprawny”, a 1.10 starsze od 1.9

Poziom: **łatwy (2 błędy)** · Rodzaj: **napraw** · Teoria: [03. String](../../teoria/03-string.md), [06. equals, hashCode, Comparator](../../teoria/06-equals-hashcode-comparator.md)

## Uruchomienie

```bash
./cwiczenia/05-numery-wersji/check.sh
```

## Zgłoszenie

Klasa `Version` miała służyć do sprawdzania, czy jest nowsza wersja aplikacji. Problemy:

1. `Version.parse("1.2.3")` rzuca `IllegalArgumentException: Niepoprawna wersja: 1.2.3`. Każda wersja jest „niepoprawna”.
2. Gdy ktoś to obszedł, okazało się, że wersja 1.10.0 jest uważana za starszą od 1.9.0, a 10.0.0 za starszą od 2.0.0.

**Oczekiwane:** `parse` przyjmuje dokładnie trzy nieujemne liczby rozdzielone kropkami, a porządek wersji jest numeryczny: `0.9.9 < 1.2.9 < 1.2.10 < 1.10.0 < 2.0.0`.

## Wskazówki

- Przeczytaj dokumentację `String.split`: co jest jego parametrem? Co znaczy kropka w wyrażeniu regularnym?
- Jak porównuje teksty `String.compareTo`? Porównaj ręcznie `"1.10.0"` i `"1.9.0"` znak po znaku.

## Pytania dodatkowe

1. Jak inaczej niż `"\\."` można podzielić tekst po kropce? (`Pattern.quote`)
2. Co zwraca `"1.2.3.".split("\\.")`, a co `"1.2.3.".split("\\.", -1)`? Czy to ma znaczenie w tej klasie?
3. Zapisz `compareTo` przez `Comparator.comparingInt(...).thenComparingInt(...)`.
4. Klasa nadpisuje `equals` i `hashCode`. Czy `compareTo` jest z nimi zgodne (`a.compareTo(b) == 0` wtedy i tylko wtedy, gdy `a.equals(b)`)? Dlaczego to ważne np. dla `TreeSet`?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
