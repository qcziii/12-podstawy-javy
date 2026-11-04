# 13. Pliki i wejście/wyjście

Ćwiczenia: [20](../cwiczenia/20-plik-z-polskimi-znakami/ZADANIE.md), [15](../cwiczenia/15-import-csv/ZADANIE.md)

## Path i Files (java.nio.file)

Nowoczesne API do plików (Java 7+). `Path` to ścieżka, `Files` to statyczne metody do operacji.

```java
Path dir = Path.of("dane");                  // względna do katalogu roboczego
Path file = dir.resolve("notatki.txt");      // dane/notatki.txt
Path abs = file.toAbsolutePath();
file.getFileName();                          // notatki.txt
file.getParent();                            // dane

Files.exists(file);
Files.createDirectories(dir);                // tworzy też brakujące katalogi nadrzędne
Files.size(file);
Files.delete(file);                          // NoSuchFileException, gdy nie ma
Files.deleteIfExists(file);
Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
Files.move(src, dst);
```

Stare `java.io.File` dalej działa i spotkasz je w starszym kodzie; zamiana: `file.toPath()`, `path.toFile()`.

## Czytanie i pisanie tekstu: najprościej

```java
String all = Files.readString(file, StandardCharsets.UTF_8);
List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

Files.writeString(file, "treść", StandardCharsets.UTF_8);              // nadpisuje
Files.write(file, lines, StandardCharsets.UTF_8);                       // linie, nadpisuje
Files.writeString(file, "dopisek\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND,
        StandardOpenOption.CREATE);
```

Te metody same otwierają i zamykają plik. Do małych i średnich plików wystarczą.

Duże pliki, linia po linii (stream trzeba zamknąć):

```java
try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
    long errors = lines.filter(l -> l.contains("ERROR")).count();
}
```

## Reader/Writer, buforowanie, zamykanie

```java
try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
    String line;
    while ((line = reader.readLine()) != null) {      // null = koniec pliku
        ...
    }
}

try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
    writer.write("linia");
    writer.newLine();
}
```

- **Strumienie bajtów** (`InputStream`, `OutputStream`): dane binarne.
- **Strumienie znaków** (`Reader`, `Writer`): tekst; zamieniają bajty na znaki według **kodowania**.
- `BufferedWriter` zbiera dane w buforze (domyślnie 8192 znaki) i zapisuje do pliku dopiero, gdy bufor się zapełni, przy `flush()` albo `close()`. **Niezamknięty writer = pusty albo niepełny plik.**
- Zamykanie zawsze przez try-with-resources (rozdział 09). Niezamknięte pliki to wyciek: system ma limit otwartych plików („Too many open files”), a na Windows otwartego pliku nie da się usunąć.

## Kodowanie znaków

Plik to bajty. Kodowanie mówi, jak bajty zamieniać na znaki:

| Kodowanie | `a` | `ą` | Uwagi |
|---|---|---|---|
| UTF-8 | 1 bajt | 2 bajty (`C4 85`) | standard; wszystkie języki |
| ISO-8859-2 (Latin-2) | 1 bajt | 1 bajt (`B1`) | stare polskie pliki |
| Windows-1250 | 1 bajt | 1 bajt (`B9`) | stare pliki z Windows |
| ISO-8859-1 (Latin-1) | 1 bajt | **brak** | zachodnioeuropejskie; `ą` zapisze się jako `?` |

Odczyt w złym kodowaniu daje „krzaki”: UTF-8 czytane jako ISO-8859-1 to `Ã³` zamiast `ó` (dwa bajty `C3 B3` czytane jako dwa znaki). Zapis znaku, którego kodowanie nie ma, daje `?`.

Zasady:

1. **Podawaj kodowanie jawnie**: `StandardCharsets.UTF_8`. Nie polegaj na domyślnym.
2. Od Javy 18 (JEP 400) domyślne kodowanie to UTF-8 na każdym systemie. Wcześniej zależało od systemu (na polskim Windows zwykle Windows-1250), więc ten sam program działał inaczej u różnych osób.
3. `new FileReader(file)` i `new FileWriter(file)` bez kodowania używają domyślnego. Od Javy 11 mają wersje z `Charset`.

## Scanner

Wygodny do czytania z konsoli i prostych formatów:

```java
Scanner sc = new Scanner(System.in);
int n = sc.nextInt();
String line = sc.nextLine();   // uwaga: po nextInt() reszta linii (sam Enter) zostaje do wczytania
```

Do plików lepsze są `Files` i `BufferedReader` (szybsze, przewidywalne).

## Typowe wyjątki

| Wyjątek | Kiedy |
|---|---|
| `NoSuchFileException` | plik nie istnieje (API `Files`) |
| `FileNotFoundException` | plik nie istnieje albo brak dostępu (stare API) |
| `AccessDeniedException` | brak uprawnień |
| `UncheckedIOException` | `IOException` opakowany przez API streamów (np. `Files.lines`) |
| `MalformedInputException` | bajty niepoprawne dla podanego kodowania (np. Windows-1250 czytany jako UTF-8) |

Wszystkie poza `UncheckedIOException` są `IOException`, czyli checked.

## Typowe błędy

- Brak `close()` (albo zamknięcie tylko na ścieżce sukcesu).
- Brak jawnego kodowania.
- ISO-8859-1 dla polskiego tekstu.
- Ścieżki sklejane ręcznie z `"\\"` (działa tylko na Windows); używaj `resolve`.
- `Files.lines` bez try-with-resources.

## Pytania sprawdzające

1. Dlaczego niezamknięty `BufferedWriter` może zostawić pusty plik?
2. Co zobaczysz, czytając plik UTF-8 jako ISO-8859-1? A zapisując `ą` w ISO-8859-1?
3. Jakie jest domyślne kodowanie w Javie 21?
4. Którą metodą najprościej zapisać listę linii do pliku?
5. Kiedy `Files.readAllLines`, a kiedy `Files.lines`?
