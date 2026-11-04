# 03. String

Ćwiczenia: [04](../cwiczenia/04-niezmienny-string/ZADANIE.md), [05](../cwiczenia/05-numery-wersji/ZADANIE.md), [03](../cwiczenia/03-porownanie-przez-rowna-sie/ZADANIE.md)

## String jest niemutowalny

Obiektu `String` nie da się zmienić. Każda metoda, która „zmienia” tekst, zwraca **nowy** `String`:

```java
String s = "  Ala ";
s.trim();                 // wynik wyrzucony, s dalej "  Ala "
s.toUpperCase();          // tak samo
s = s.trim();             // dobrze: s wskazuje nowy obiekt "Ala"
String t = s.toUpperCase().replace('A', '4');  // łańcuch wywołań: "4L4"
```

Ten sam wzorzec mają `BigDecimal`, `LocalDate`, `Integer` i inne niemutowalne klasy. Jeśli metoda zwraca ten sam typ, na którym ją wołasz, prawie zawsze trzeba **użyć wyniku**.

Dlaczego niemutowalny? Bezpiecznie można go współdzielić (pula Stringów), używać jako klucza w `HashMap`, przekazywać między wątkami.

## Najważniejsze metody

| Metoda | Przykład | Wynik |
|---|---|---|
| `length()` | `"ala".length()` | `3` |
| `charAt(i)` | `"ala".charAt(1)` | `'l'` |
| `substring(od, do)` | `"kowalski".substring(0, 3)` | `"kow"` (do wyłącznie) |
| `indexOf(x)` | `"a@b.pl".indexOf('@')` | `1`, `-1` gdy brak |
| `contains`, `startsWith`, `endsWith` | `"plik.txt".endsWith(".txt")` | `true` |
| `trim()` / `strip()` | `"  a ".strip()` | `"a"` (`strip` rozumie też spacje Unicode) |
| `isEmpty()` / `isBlank()` | `"  ".isBlank()` | `true` (`isEmpty` tylko dla `""`) |
| `toLowerCase()` / `toUpperCase()` | `"ŁÓDŹ".toLowerCase()` | `"łódź"` |
| `replace(a, b)` | `"a b".replace(' ', '_')` | `"a_b"` (wszystkie wystąpienia, bez regex) |
| `replaceAll(regex, b)` | `"a  b".replaceAll("\\s+", " ")` | `"a b"` |
| `split(regex)` | `"a,b".split(",")` | `["a", "b"]` |
| `String.join` | `String.join(", ", list)` | `"a, b"` |
| `repeat(n)` | `"-".repeat(3)` | `"---"` |
| `equalsIgnoreCase` | `"Ala".equalsIgnoreCase("ALA")` | `true` |
| `compareTo` | `"a".compareTo("b")` | liczba < 0 |

`toLowerCase()` bez argumentu używa języka systemu. Dla tekstów technicznych (kody, nazwy plików) bezpieczniej `toLowerCase(Locale.ROOT)`: w języku tureckim `"I".toLowerCase()` to `"ı"` (i bez kropki).

## split przyjmuje wyrażenie regularne

```java
"1.2.3".split(".")      // [] ! "." w regex oznacza "dowolny znak": wszystko jest separatorem
"1.2.3".split("\\.")    // ["1", "2", "3"]
"a|b".split("\\|")      // ["a", "b"]
"1.2.3".split(Pattern.quote("."))  // też dobrze
```

Znaki specjalne w regex: `. | * + ? ( ) [ ] { } \ ^ $`. Trzeba je poprzedzić `\\`.

Inne szczegóły `split`:

- puste elementy na **końcu** są usuwane: `"a,b,,".split(",")` daje `["a", "b"]`; z limitem `-1` zostają: `split(",", -1)` daje `["a", "b", "", ""]`,
- puste elementy w środku zostają: `"a,,b".split(",")` daje `["a", "", "b"]`,
- spacje: `"  a   b ".split(" ")` daje `["", "", "a", "", "", "b"]`; zwykle chcesz `trim().split("\\s+")`.

## Porównywanie tekstów

- `equals` (treść) zamiast `==` (tożsamość), patrz rozdział 02.
- `compareTo` porównuje **znak po znaku** (kody znaków), czyli leksykograficznie: `"10".compareTo("9") < 0`, bo `'1' < '9'`. Teksty z liczbami (wersje, numery) trzeba najpierw zamienić na liczby.
- `compareTo` zna tylko kody znaków, więc wielkie litery są przed małymi (`"Z" < "a"`), a polskie litery za `z`. Do sortowania po polsku: `Collator.getInstance(new Locale("pl"))`.

## Sklejanie: `+`, StringBuilder, format

```java
String s = "Ala ma " + count + " koty";      // proste sklejanie: w porządku
```

W pętli każde `+=` tworzy nowy `String` i kopiuje cały dotychczasowy tekst, więc przy 10 000 iteracji to ok. 50 mln kopiowanych znaków. Do budowania w pętli służy `StringBuilder` (mutowalny):

```java
StringBuilder sb = new StringBuilder();
for (String word : words) {
    sb.append(word.charAt(0));
}
String result = sb.toString();
```

Formatowanie:

```java
String.format("%s ma %d lat, saldo %.2f zł", name, age, balance);
"%d,%02d zł".formatted(1230 / 100, 1230 % 100);   // "12,30 zł"
```

`%d` liczba całkowita, `%02d` co najmniej 2 cyfry z zerem z przodu, `%.2f` 2 miejsca po przecinku (uwaga: separator dziesiętny zależy od `Locale`), `%s` cokolwiek (`toString()`), `%n` nowa linia.

## Zamiana tekstu na liczbę i odwrotnie

```java
int n = Integer.parseInt("42");          // NumberFormatException dla "42a", "", " 42"
long l = Long.parseLong("-5");
double d = Double.parseDouble("3.14");   // zawsze kropka, niezależnie od Locale
BigDecimal b = new BigDecimal("19.99");
String s = String.valueOf(42);           // albo "" + 42, albo Integer.toString(42)
```

`NumberFormatException` dziedziczy po `IllegalArgumentException`.

## char

`char` to jeden znak UTF-16, a w praktyce liczba. `'a' + 1` to `98` (`int`). Przydatne: `Character.isDigit`, `Character.isLetter`, `Character.toUpperCase`, `Character.isWhitespace`.

## Text blocks (Java 15+)

```java
String json = """
        {
          "name": "Ala"
        }
        """;
```

## Typowe błędy

- `s.trim();` bez przypisania.
- `split(".")`, `split("|")`.
- `split(" ")` przy wielu spacjach.
- `substring(0, 1)` na pustym tekście (`StringIndexOutOfBoundsException`).
- Porównywanie numerów wersji jako tekstów.
- `+=` w długiej pętli.

## Pytania sprawdzające

1. Co wypisze: `String s = "abc"; s.toUpperCase(); System.out.println(s);`?
2. Co zwraca `"a.b.c".split(".").length`? Jak to poprawić?
3. Co zwraca `"x,,y,".split(",").length`?
4. Dlaczego `"10".compareTo("9")` jest ujemne?
5. Kiedy `StringBuilder`, a kiedy zwykłe `+`?
6. Czym się różni `replace` od `replaceAll`?
