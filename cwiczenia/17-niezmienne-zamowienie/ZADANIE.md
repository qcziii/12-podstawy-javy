# 17. Rekord zamówienia da się zmienić z zewnątrz

Poziom: **łatwy/średni (3 błędy)** · Rodzaj: **napraw** · Teoria: [10. Enumy i rekordy](../../teoria/10-enumy-i-rekordy.md), [04. Klasy i obiekty](../../teoria/04-klasy-i-obiekty.md)

## Uruchomienie

```bash
./cwiczenia/17-niezmienne-zamowienie/check.sh
```

## Zgłoszenie

`Order` i `OrderLine` są rekordami, „więc są niemutowalne”. Okazało się, że nie:

1. Kod, który utworzył zamówienie, potem zmieniał swoją listę i zmieniało się zamówienie (także już zapisane w historii).
2. Przez `order.lines().add(...)` można dopisać pozycję do dowolnego zamówienia.
3. `withLine` miało zwracać **nowe** zamówienie, a zmienia stare (albo rzuca `UnsupportedOperationException`, gdy lista jest z `List.of`).
4. Da się utworzyć zamówienie bez numeru i pozycję z ilością 0 albo ujemną ceną.

**Oczekiwane:** zamówienie po utworzeniu nie zmienia się w żaden sposób. Walidacja w konstruktorach: pusty numer, ilość < 1, cena < 0, pusta nazwa produktu to `IllegalArgumentException`; `null` w numerze, nazwie produktu albo liście pozycji to `NullPointerException`.

## Wymagania

- Zostają rekordy. Walidacja i kopia w **kompaktowym konstruktorze** rekordu.

## Pytania dodatkowe

1. Co rekord generuje sam? (konstruktor, akcesory, `equals`, `hashCode`, `toString`) Czego nie robi?
2. Rekord ma pola `final`. Dlaczego to nie wystarcza do niemutowalności?
3. Czym się różni kompaktowy konstruktor od zwykłego (kanonicznego)? Dlaczego w kompaktowym piszemy `lines = List.copyOf(lines)`, a nie `this.lines = ...`?
4. Jakie są zalety niemutowalnych obiektów? (podpowiedź: klucze w `HashMap`, ćwiczenie 09; bezpieczne przekazywanie)

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
