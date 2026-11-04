# 06. Nowy koszyk ma produkty z cudzego koszyka

Poziom: **łatwy** · Rodzaj: **napraw** · Teoria: [04. Klasy i obiekty](../../teoria/04-klasy-i-obiekty.md)

## Uruchomienie

```bash
./cwiczenia/06-wspolny-koszyk/check.sh
```

## Zgłoszenie

Sklep ma klasę `Cart`. Zgłoszenia od klientów:

- „Założyłem konto, wchodzę w koszyk, a tam mleko i chleb, których nie dodawałem”,
- na podsumowaniu każdego koszyka jest nazwisko klienta, który ostatnio założył koszyk.

Numery koszyków (1, 2, 3...) i licznik `createdCount()` działają dobrze.

**Oczekiwane:** każdy koszyk ma swoje produkty i swojego właściciela. Numeracja i licznik koszyków działają dalej.

## Pytania dodatkowe

1. Czym się różni pole `static` od zwykłego pola? Ile jest kopii każdego z nich przy 1000 koszyków?
2. Dlaczego `nextId` **ma** być `static`, a `products` nie?
3. Czy z metody `static` można odczytać `this.owner`? Dlaczego?
4. Co daje `final` przy polu `products`? Czy przy `final` da się dodać element do listy?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
