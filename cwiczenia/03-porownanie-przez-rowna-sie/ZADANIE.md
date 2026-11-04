# 03. Właściciel dokumentu nie ma do niego dostępu

Poziom: **łatwy (2 błędy)** · Rodzaj: **napraw** · Teoria: [02. Referencje i porównywanie](../../teoria/02-referencje-i-porownywanie.md), [03. String](../../teoria/03-string.md)

## Uruchomienie

```bash
./cwiczenia/03-porownanie-przez-rowna-sie/check.sh
```

## Zgłoszenie

`AccessChecker` decyduje, kto może edytować dokument: właściciel albo administrator. Na środowisku testowym wszystko działało (użytkownicy mieli id 1-20). Na produkcji:

- użytkownik o id 1000 nie może edytować własnego dokumentu,
- administratorzy nie mają uprawnień administratora, choć w tokenie mają rolę `"ADMIN"`.

W testach jednostkowych pisanych wcześniej przez zespół rola była podawana jako literał `"ADMIN"` i testy przechodziły.

**Oczekiwane:** właściciel i administrator mogą edytować dokument niezależnie od wartości id i od tego, skąd pochodzi tekst roli. Brak właściciela (`ownerId == null`) albo brak roli (`role == null`) nie powoduje wyjątku.

## Wskazówki

- Jakiego typu jest `ownerId()` w rekordzie `Document`: `long` czy `Long`? Co porównuje `==` dla obiektów?
- Dlaczego dla id 7 działało, a dla 1000 nie? Poszukaj w teorii „cache Integer/Long”.

## Pytania dodatkowe

1. Co wypiszą `Integer a = 127, b = 127; a == b` i `Integer c = 128, d = 128; c == d`? Dlaczego?
2. Dlaczego `"ADMIN" == "ADMIN"` w kodzie daje `true`, a ten sam tekst z tokenu już nie?
3. Dlaczego `"ADMIN".equals(role)` jest bezpieczniejsze niż `role.equals("ADMIN")`? Co robi `Objects.equals(a, b)`?
4. Co się stanie przy `long id = user.id();`, gdy `user.id()` zwraca `null`?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
