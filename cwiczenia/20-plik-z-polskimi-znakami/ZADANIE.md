# 20. Notatki zapisane do pliku znikają albo tracą polskie litery

Poziom: **łatwy (2 błędy)** · Rodzaj: **napraw** · Teoria: [13. Pliki](../../teoria/13-pliki.md), [09. Wyjątki](../../teoria/09-wyjatki.md)

## Uruchomienie

```bash
./cwiczenia/20-plik-z-polskimi-znakami/check.sh
```

## Zgłoszenie

`NotesFile` zapisuje notatki do pliku tekstowego, jedna notatka w linii. Te same pliki otwierają inne programy (aplikacja na telefon), które zapisują i czytają w UTF-8. Zgłoszenia:

1. Po zapisie plik jest pusty (0 bajtów). Czasem, przy bardzo dużej liczbie notatek, zapisuje się tylko początek.
2. Pliki z telefonu wczytują się z „krzakami”: `ÅÃ³dÅº` zamiast `Łódź`.
3. Gdy w końcu coś się zapisało, polskie litery zamieniły się w `?`.

**Oczekiwane:** zapis i odczyt w UTF-8, wszystko, co zapisane, jest w pliku zaraz po powrocie z `save`. `load` nieistniejącego pliku rzuca `NoSuchFileException`.

## Wymagania

- Kodowanie podane jawnie (`StandardCharsets.UTF_8`), nie zależne od ustawień systemu.
- Zamykanie przez try-with-resources (albo metody `Files`, które zamykają same).

## Pytania dodatkowe

1. Dlaczego niezamknięty `BufferedWriter` zostawia pusty plik? Co robi `flush()`, a co `close()`?
2. Ile bajtów ma litera `ą` w UTF-8, a ile w ISO-8859-2? Dlaczego ISO-8859-1 w ogóle jej nie ma?
3. Jakie jest domyślne kodowanie w Javie 18+ (JEP 400), a jakie było wcześniej na Windows?
4. Czym się różni `Files.readAllLines` od `Files.lines`? Kiedy to drugie trzeba zamknąć?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
