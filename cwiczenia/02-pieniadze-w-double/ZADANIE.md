# 02. Faktura nie zgadza się o grosz

Poziom: **łatwy/średni (3 błędy)** · Rodzaj: **napraw** · Teoria: [01. Typy i operatory](../../teoria/01-typy-i-operatory.md)

## Uruchomienie

```bash
./cwiczenia/02-pieniadze-w-double/check.sh
```

## Zgłoszenie

Po ostatnim refaktoringu ktoś zmienił w `Invoice` pole na `double`, „bo szybciej niż `BigDecimal`”. Od tego czasu księgowość zgłasza:

- 3 pozycje po 0,10 zł dają netto `0.30000000000000004`,
- 1000 pozycji po 0,01 zł daje `9.999999999999831` zamiast 10,00 zł,
- brutto 1,50 zł + 23% VAT (czyli 1,845 zł) wychodzi 1,84 zł zamiast 1,85 zł,
- klient zapłacił „0.3” za fakturę na „0.30” i system twierdzi, że to nie jest dokładna zapłata.

**Oczekiwane:** wszystkie kwoty są liczone dokładnie, brutto jest zaokrąglone do grosza „po szkolnemu” (od 0,005 w górę) i ma zawsze 2 miejsca po przecinku, a porównanie kwot nie zależy od liczby zer na końcu.

## Wymagania

- Nie zmieniaj publicznych metod `Invoice` (parametry i typy zwracane zostają `BigDecimal`).
- W klasie nie powinno być żadnego `double`.

## Pytania dodatkowe

1. Dlaczego `0.1 + 0.2 != 0.3` w `double`? Czy to błąd Javy?
2. Czym się różni `new BigDecimal("0.1")` od `new BigDecimal(0.1)`? A `BigDecimal.valueOf(0.1)`?
3. Dlaczego `new BigDecimal("0.3").equals(new BigDecimal("0.30"))` to `false`? Kiedy użyć `equals`, a kiedy `compareTo`?
4. Inny popularny sposób na pieniądze to liczenie w groszach w `long`. Jakie ma zalety i wady w porównaniu z `BigDecimal`?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
