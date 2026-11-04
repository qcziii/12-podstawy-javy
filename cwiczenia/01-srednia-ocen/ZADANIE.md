# 01. Średnia ocen jest zaokrąglona, a suma czasu wychodzi ujemna

Poziom: **łatwy (2 błędy)** · Rodzaj: **napraw** · Teoria: [01. Typy i operatory](../../teoria/01-typy-i-operatory.md)

## Uruchomienie

```bash
./cwiczenia/01-srednia-ocen/check.sh
```

## Zgłoszenie

Panel kursu (`CourseStats`) pokazuje dwie rzeczy i obie są złe:

1. Średnia ocen `[4, 5]` wychodzi `4.0` zamiast `4.5`. Średnia `[3, 4, 4]` to `3.0`. Wygląda, jakby ktoś zaokrąglał w dół, choć metoda zwraca `double`.
2. Łączny czas oglądania filmów w milisekundach dla popularnych kursów jest za mały albo wręcz ujemny (np. `-1294967296`), choć metoda zwraca `long`.

**Oczekiwane:** średnia jest dokładna (`4.5`, `3.666...`), a czas oglądania w milisekundach jest poprawny także dla miliardów milisekund.

## Wskazówki

- Sprawdź, **jakiego typu** jest każde wyrażenie po prawej stronie, a nie tylko, do jakiej zmiennej trafia wynik. `double x = 9 / 2;` to nadal dzielenie liczb całkowitych.
- Ile maksymalnie mieści `int`? Policz, ile to sekund (zanim przemnożysz przez 1000).
- Zmiana samego typu zmiennej `total` na `long` to za mało. Dlaczego? Test 6 to pokazuje.

## Pytania dodatkowe

1. Co wypisze `System.out.println(7 / 2 + " " + 7 / 2.0 + " " + (double) 7 / 2 + " " + (double) (7 / 2));`?
2. Dlaczego Java przy przepełnieniu `int` nie zgłasza błędu? Jak sprawić, żeby zgłaszała (podpowiedź: `Math.addExact`, `Math.multiplyExact`)?
3. Co oznacza `L` w `1000L`? Jaki typ ma literał `3_000_000_000` bez `L` i czy to się skompiluje?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
