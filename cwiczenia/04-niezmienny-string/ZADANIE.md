# 04. Normalizacja loginu nic nie zmienia

Poziom: **łatwy (2 metody)** · Rodzaj: **napraw** · Teoria: [03. String](../../teoria/03-string.md)

## Uruchomienie

```bash
./cwiczenia/04-niezmienny-string/check.sh
```

## Zgłoszenie

Formularz rejestracji tworzy login i inicjały z imienia i nazwiska (`NameFormatter`):

- `login("  Anna NOWAK ")` ma dać `"anna_nowak"`, a zwraca tekst bez żadnych zmian,
- `initials("jan kowalski")` ma dać `"JK"`, a zwraca pusty tekst,
- `initials("  jan    kowalski ")` (kilka spacji) kończy się `StringIndexOutOfBoundsException`.

**Oczekiwane:** jak w komentarzach w kodzie i w testach.

## Wymagania

- W `initials` buduj wynik przez `StringBuilder`.

## Pytania dodatkowe

1. Co to znaczy, że `String` jest niemutowalny? Co w takim razie robi `s.toUpperCase()`?
2. Co zwraca `"  a  b ".split(" ")`? Ile elementów ma tablica i jakie?
3. Dlaczego sklejanie tekstu przez `+=` w długiej pętli jest wolne, a `StringBuilder` nie?
4. Czym się różnią `trim()` i `strip()`? A `isEmpty()` i `isBlank()`?
5. Dlaczego czasem pisze się `toLowerCase(Locale.ROOT)`? (podpowiedź: język turecki i litera `I`)

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
