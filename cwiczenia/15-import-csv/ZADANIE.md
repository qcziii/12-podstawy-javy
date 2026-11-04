# 15. Import cennika zwraca pustą listę zamiast błędu i nie zamyka pliku

Poziom: **średni (2 błędy)** · Rodzaj: **napraw** · Teoria: [09. Wyjątki](../../teoria/09-wyjatki.md), [13. Pliki](../../teoria/13-pliki.md)

## Uruchomienie

```bash
./cwiczenia/15-import-csv/check.sh
```

## Zgłoszenie

`PriceListImporter` wczytuje cennik w CSV (`sku;price`, pierwsza linia to nagłówek). Zgłoszenia:

1. Gdy w pliku jest błąd (np. cena `siedem` w linii 3), import „się udaje”, tylko zwraca pustą listę. Użytkownik myśli, że cennik był pusty. W logach jest tylko `Błąd importu: For input string: "siedem"`, bez informacji, w której linii.
2. Po kilkuset importach serwer zgłasza „Too many open files”: plik nie jest zamykany, ani po udanym imporcie, ani po błędzie.

**Oczekiwane:**

- Każdy błąd w pliku (zła cena, brak średnika, ujemna cena) kończy się `ImportException` (gotowa, checked) z numerem linii w komunikacie i oryginalnym wyjątkiem jako przyczyną (`getCause()`).
- Błąd odczytu (`IOException`) też kończy się `ImportException` z `IOException` jako przyczyną.
- `Reader` jest zamknięty zawsze: po sukcesie i po każdym błędzie.
- Poprawny plik importuje się jak dotąd (puste linie pomijamy, spacje wokół wartości obcinamy).

## Wymagania

- `importFrom` deklaruje `throws ImportException`.
- Żadnego `catch (Exception e)`, które połyka wyjątek, i żadnego `System.out.println` w obsłudze błędów.
- Zamykanie przez try-with-resources.

## Pytania dodatkowe

1. Czym się różni wyjątek checked od unchecked? Dlaczego `ImportException` jest checked, a `IllegalArgumentException` nie?
2. Co daje przekazanie przyczyny (`new ImportException(msg, e)`)? Jak to wygląda w stack trace (`Caused by:`)?
3. Jak zamknąć plik bez try-with-resources (`finally`)? Dlaczego try-with-resources jest lepsze, gdy `close()` też rzuci wyjątek? (*suppressed exceptions*)
4. Kiedy złapanie wyjątku i zwrócenie wartości domyślnej **jest** w porządku?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
