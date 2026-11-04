# Podstawy Javy: powtórka i ćwiczenia

Repo ma dwie części:

1. **Teoria** (`teoria/`): 13 krótkich rozdziałów od typów i operatorów do plików, z przykładami, typowymi błędami i pytaniami sprawdzającymi. Do tego jednostronicowa [ściąga](SCIAGA.md).
2. **Ćwiczenia** (`cwiczenia/`): 21 zadań z automatycznym sprawdzaniem. W większości dostajesz kod z błędem i opis zgłoszenia („napraw”), w kilku piszesz coś od zera („napisz”). Każde ćwiczenie ma test, który przejdzie dopiero po poprawnym rozwiązaniu.

Bez wielowątkowości (ta jest w osobnym repo), bez Springa, bazy i Dockera: czysta Java.

Zalecany sposób pracy: przeczytaj rozdział teorii, odpowiedz sobie na pytania sprawdzające na jego końcu, potem zrób ćwiczenia, które się do niego odwołują.

## Wymagania

- Java 21+ i Maven 3.9+
- `bash` (na Windows: Git Bash) do uruchamiania `check.sh`
- internet przy pierwszym uruchomieniu (Maven pobiera JUnit)

## Jak pracować

```bash
# automatyczne sprawdzenie jednego ćwiczenia, z katalogu głównego repo
./cwiczenia/01-srednia-ocen/check.sh
```

Przeczytaj `ZADANIE.md` w katalogu ćwiczenia, uruchom `check.sh`, zobacz, które testy nie przechodzą i z jakim komunikatem, znajdź przyczynę, popraw i sprawdź. Ćwiczenie jest rozwiązane, gdy `check.sh` wypisze `WYNIK: wszystkie sprawdzenia przeszły`.

`check.sh` kompiluje ćwiczenie i uruchamia jego testy (`mvn test`, klasa `CheckTest`), a potem wypisuje wynik każdego testu po polsku. Test możesz też uruchomić w IntelliJ (zielona strzałka przy `CheckTest`), także w debuggerze. Projekt otwierasz w IntelliJ jako projekt Maven: plik `pom.xml` w katalogu głównym otwiera wszystkie ćwiczenia naraz.

Test to też dokumentacja: jeśli nie wiesz, co dokładnie ma zwracać metoda, przeczytaj test (`src/test/java/.../CheckTest.java`).

## Teoria

| # | Rozdział | Ćwiczenia |
|---|---|---|
| 01 | [Typy i operatory](teoria/01-typy-i-operatory.md) (dzielenie, przepełnienie, rzutowanie, `double` a `BigDecimal`) | 01, 02 |
| 02 | [Referencje i porównywanie](teoria/02-referencje-i-porownywanie.md) (`==` a `equals`, `null`, autoboxing, cache `Integer`) | 03 |
| 03 | [String](teoria/03-string.md) (niemutowalność, `split`, `StringBuilder`, `compareTo`) | 04, 05 |
| 04 | [Klasy i obiekty](teoria/04-klasy-i-obiekty.md) (konstruktory, `static`, enkapsulacja, `final`, niemutowalność) | 06, 12, 17 |
| 05 | [Dziedziczenie, interfejsy, polimorfizm](teoria/05-dziedziczenie-i-interfejsy.md) (override a overload, `@Override`, klasy abstrakcyjne) | 07, 08 |
| 06 | [equals, hashCode, Comparator](teoria/06-equals-hashcode-comparator.md) (kontrakt, `HashSet` od środka, `Comparable`) | 08, 09, 10 |
| 07 | [Kolekcje](teoria/07-kolekcje.md) (`List`, `Set`, `Map`, usuwanie w pętli, listy niemodyfikowalne) | 11, 12, 13 |
| 08 | [Generyki](teoria/08-generyki.md) (klasy i metody generyczne, `? extends`, `? super`) | 14 |
| 09 | [Wyjątki](teoria/09-wyjatki.md) (checked/unchecked, przyczyna, try-with-resources) | 15 |
| 10 | [Enumy i rekordy](teoria/10-enumy-i-rekordy.md) (enum z polami i metodami, `EnumMap`, kompaktowy konstruktor) | 16, 17 |
| 11 | [Lambdy i streamy](teoria/11-lambdy-i-streamy.md) (interfejsy funkcyjne, `filter`/`map`/`collect`, `groupingBy`) | 18 |
| 12 | [Optional i null](teoria/12-optional-i-null.md) (`map`, `orElse` a `orElseGet`) | 19 |
| 13 | [Pliki](teoria/13-pliki.md) (`Path`, `Files`, kodowanie znaków, zamykanie) | 20 |

## Ćwiczenia

| # | Ćwiczenie | Rodzaj | Poziom |
|---|---|---|---|
| 01 | [Średnia ocen jest zaokrąglona, a suma czasu wychodzi ujemna](cwiczenia/01-srednia-ocen/ZADANIE.md) | napraw | łatwy (2 błędy) |
| 02 | [Faktura nie zgadza się o grosz](cwiczenia/02-pieniadze-w-double/ZADANIE.md) | napraw | łatwy/średni |
| 03 | [Właściciel dokumentu nie ma do niego dostępu](cwiczenia/03-porownanie-przez-rowna-sie/ZADANIE.md) | napraw | łatwy (2 błędy) |
| 04 | [Normalizacja loginu nic nie zmienia](cwiczenia/04-niezmienny-string/ZADANIE.md) | napraw | łatwy |
| 05 | [Numer wersji 1.2.3 jest „niepoprawny”, a 1.10 starsze od 1.9](cwiczenia/05-numery-wersji/ZADANIE.md) | napraw | łatwy (2 błędy) |
| 06 | [Nowy koszyk ma produkty z cudzego koszyka](cwiczenia/06-wspolny-koszyk/ZADANIE.md) | napraw | łatwy |
| 07 | [Rabaty: interfejs i polimorfizm zamiast sprawdzania typu](cwiczenia/07-rabaty-polimorfizm/ZADANIE.md) | napisz | średni |
| 08 | [HashSet przepuszcza duplikaty produktów](cwiczenia/08-duplikaty-w-secie/ZADANIE.md) | napraw | łatwy/średni (2 błędy) |
| 09 | [Klient po zmianie e-maila znika z rejestru](cwiczenia/09-zgubiony-klucz/ZADANIE.md) | napraw | średni |
| 10 | [Sortowanie po cenie miesza produkty, a katalog gubi warianty](cwiczenia/10-sortowanie-produktow/ZADANIE.md) | napraw | średni (2 błędy) |
| 11 | [Lista zakupów: wyjątek przy dodawaniu i przy odhaczaniu](cwiczenia/11-usuwanie-w-petli/ZADANIE.md) | napraw | łatwy (2 błędy) |
| 12 | [Playlista usuwa nie ten utwór, a z zewnątrz da się ją zepsuć](cwiczenia/12-remove-po-indeksie/ZADANIE.md) | napraw | łatwy/średni (2 błędy) |
| 13 | [Dziennik ocen na mapach](cwiczenia/13-dziennik-ocen/ZADANIE.md) | napisz | średni |
| 14 | [Narzędzia do kolekcji bez generyków: testy się nie kompilują](cwiczenia/14-generyczne-narzedzia/ZADANIE.md) | napraw | średni |
| 15 | [Import cennika zwraca pustą listę zamiast błędu i nie zamyka pliku](cwiczenia/15-import-csv/ZADANIE.md) | napraw | średni (2 błędy) |
| 16 | [Kalkulator na enumie](cwiczenia/16-kalkulator-enum/ZADANIE.md) | napisz | średni |
| 17 | [Rekord zamówienia da się zmienić z zewnątrz](cwiczenia/17-niezmienne-zamowienie/ZADANIE.md) | napraw | łatwy/średni |
| 18 | [Raport sprzedaży na streamach](cwiczenia/18-raport-sprzedazy/ZADANIE.md) | napisz | średni |
| 19 | [Profil użytkownika: NullPointerException i NoSuchElementException](cwiczenia/19-optional-i-null/ZADANIE.md) | napraw | łatwy/średni |
| 20 | [Notatki zapisane do pliku znikają albo tracą polskie litery](cwiczenia/20-plik-z-polskimi-znakami/ZADANIE.md) | napraw | łatwy (2 błędy) |
| 21 | [Projekt: wypożyczalnia sprzętu](cwiczenia/21-projekt-wypozyczalnia/ZADANIE.md) | napisz | trudny |

## Zasady

- Zmieniasz kod w `src/main` ćwiczenia. Nie zmieniaj testów (`src/test`), `pom.xml`, `check.sh` ani `scripts/`.
- Wymagania z `ZADANIE.md` są częścią zadania (np. „bez `switch`”, „bez pętli”). Niektóre sprawdza `check.sh`.
- Zanim cokolwiek zmienisz, **wyjaśnij sobie, dlaczego kod nie działa**: która reguła z teorii jest złamana. Poprawka „na próbę” (zmiana typu tu i tam, aż test przejdzie) niczego nie uczy, a na omówieniu padnie pytanie „dlaczego”.
- Czytaj komunikaty błędów i stack trace do końca. Od Javy 14 `NullPointerException` mówi dokładnie, co było nullem.
- Każde `ZADANIE.md` ma pytania dodatkowe. Odpowiedz na nie, zanim uznasz ćwiczenie za skończone.
- Pracuj na własnej gałęzi, żeby łatwo wrócić do wersji wyjściowej (`git checkout -- cwiczenia/NN-...`).

Wszystkie ćwiczenia kompiluje naraz `mvn -fae compile` w katalogu głównym (14 na początku celowo się nie kompiluje, `-fae` każe Mavenowi mimo to skompilować pozostałe). Wszystkie testy naraz: `./scripts/check-all.sh` (na początku wszystkie są czerwone, i tak ma być).
