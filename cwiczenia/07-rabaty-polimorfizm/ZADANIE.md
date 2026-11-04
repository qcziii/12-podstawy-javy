# 07. Rabaty: interfejs i polimorfizm zamiast sprawdzania typu

Poziom: **średni** · Rodzaj: **napisz** · Teoria: [04. Klasy i obiekty](../../teoria/04-klasy-i-obiekty.md), [05. Dziedziczenie i interfejsy](../../teoria/05-dziedziczenie-i-interfejsy.md)

## Uruchomienie

```bash
./cwiczenia/07-rabaty-polimorfizm/check.sh
```

## Zadanie

Stara kasa liczyła rabaty tak:

```java
if (type.equals("PERCENT")) {
    price = price - price * value / 100;
} else if (type.equals("FIXED")) {
    price = Math.max(0, price - value);
} else if (type.equals("COMBINED")) {
    // ...30 more lines
}
```

Każdy nowy rodzaj rabatu oznaczał grzebanie w tym `if`-ie. Masz to napisać obiektowo: jest interfejs `Discount` (gotowy), a każdy rodzaj rabatu to osobna klasa, która go implementuje. Kasa (`Checkout`) nie wie, jakiego rodzaju rabat dostała, tylko woła `apply` i `description`.

Kwoty są w groszach (`long`): 1230 to 12,30 zł.

Uzupełnij klasy (szkielety są gotowe, metody rzucają `UnsupportedOperationException`):

1. `PercentageDiscount(int percent)`: rabat procentowy, `percent` od 0 do 100, inaczej `IllegalArgumentException`. Rabat zaokrąglamy w dół do grosza: 10% z 99 gr to 9 gr rabatu, cena 90 gr. Opis: `"Rabat 10%"`.
2. `FixedAmountDiscount(long amountInGrosze)`: rabat kwotowy, kwota >= 0, inaczej `IllegalArgumentException`. Cena po rabacie nie spada poniżej 0. Opis: `"Rabat 5,00 zł"`.
3. `CombinedDiscount(List<Discount> discounts)`: kilka rabatów po kolei, w podanej kolejności. Opis: opisy połączone `" + "`. Późniejsza zmiana listy przekazanej do konstruktora nie zmienia rabatu.
4. `Money.format(long grosze)`: `1230` -> `"12,30 zł"`, `5` -> `"0,05 zł"`.
5. `Checkout.total` (suma cen, potem rabat od sumy) i `Checkout.summary` (`"Do zapłaty: 45,00 zł (Rabat 10%)"`).

## Wymagania

- Bez `instanceof`, `switch` i `getClass()` (sprawdza `check.sh`). Kod, który używa rabatu, nie sprawdza jego typu.
- Pola klas rabatów są `private final`.

## Pytania dodatkowe

1. Dodaj w myślach nowy rabat „co drugi produkt gratis”. Które klasy trzeba zmienić, a które zostają bez zmian? Jak by to wyglądało w starej wersji z `if`-ami?
2. `CombinedDiscount` sam jest `Discount` i zawiera inne `Discount`y. Czy można zrobić `CombinedDiscount` z innego `CombinedDiscount`? (to wzorzec Composite)
3. Czym się różni interfejs od klasy abstrakcyjnej? Kiedy wybrać które?
4. `Discount` ma dwie metody. Czy da się go zapisać jako lambdę? Dlaczego nie? A gdyby miał jedną metodę?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
