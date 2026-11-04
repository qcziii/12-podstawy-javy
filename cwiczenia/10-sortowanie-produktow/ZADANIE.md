# 10. Sortowanie po cenie miesza produkty, a katalog gubi warianty

Poziom: **średni (2 błędy)** · Rodzaj: **napraw** · Teoria: [06. equals, hashCode, Comparator](../../teoria/06-equals-hashcode-comparator.md), [07. Kolekcje](../../teoria/07-kolekcje.md)

## Uruchomienie

```bash
./cwiczenia/10-sortowanie-produktow/check.sh
```

## Zgłoszenie

Dwa problemy w `ItemSorting`:

1. Lista „od najtańszych” jest dobrze posortowana tylko wtedy, gdy ceny różnią się o pełne złotówki. Produkty za 9,99, 9,49 i 9,50 zł zostają w przypadkowej kolejności. Przy tej samej cenie produkty mają być alfabetycznie, a nie są.
2. Katalog (`catalog`) miał pokazać wszystkie produkty posortowane po nazwie, a gubi warianty: z „Kawa 250 g” za 11,49 zł i „Kawa 1 kg” za 39,99 zł (oba mają nazwę `"Kawa"`) zostaje jeden.

**Oczekiwane:** `byPrice` sortuje po cenie rosnąco, przy równej cenie po nazwie. `catalog` sortuje po nazwie, przy tej samej nazwie po cenie, i gubi tylko pozycje identyczne (ta sama nazwa i cena).

## Wymagania

- Użyj metod `Comparator` (`comparing`, `comparingDouble`, `thenComparing`...) zamiast ręcznego odejmowania.

## Pytania dodatkowe

1. Ile wynosi `(int) (9.99 - 9.49)`? Co to oznacza dla komparatora?
2. Dlaczego nawet dla `int` komparator `(a, b) -> a - b` bywa błędny? (podpowiedź: `Integer.MIN_VALUE`)
3. Skąd `TreeSet` wie, że dwa elementy są „takie same”? Czy woła `equals`?
4. Jak posortować od najdroższych? (`reversed()`) Na co uważać przy `reversed()` w środku łańcucha `thenComparing`?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
