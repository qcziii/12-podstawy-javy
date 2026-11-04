# 14. Narzędzia do kolekcji bez generyków: testy się nie kompilują

Poziom: **średni** · Rodzaj: **napraw** · Teoria: [08. Generyki](../../teoria/08-generyki.md)

## Uruchomienie

```bash
./cwiczenia/14-generyczne-narzedzia/check.sh
```

## Zgłoszenie

`CollectionUtils` i `Pair` zostały napisane „po staremu”: wszystko jest `Object`, kolekcje bez parametrów typu. Logika jest dobra, ale:

- przy każdym użyciu trzeba rzutować (`(Integer) CollectionUtils.max(...)`), a pomyłka wychodzi dopiero jako `ClassCastException`,
- `sum(List<Number>)` nie przyjmuje `List<Integer>`,
- kompilator zgłasza ostrzeżenia o surowych typach (*raw types*). W tym ćwiczeniu ostrzeżenia są błędami, więc projekt się nie kompiluje.

Testy pokazują, jak te narzędzia mają się dać używać, **bez rzutowania**. Na początku `check.sh` wypisze błędy kompilacji (najpierw ostrzeżenia w `CollectionUtils`, po ich poprawieniu błędy w testach). To jest punkt wyjścia.

**Oczekiwane:** projekt kompiluje się bez ostrzeżeń, wszystkie testy przechodzą.

## Wymagania

- Zmieniasz tylko **sygnatury i typy** w `CollectionUtils` i `Pair`, logika zostaje.
- Nie zmieniaj testów i `pom.xml`. Bez `@SuppressWarnings`.
- `max` ma działać dla listy jabłek z komparatorem wszystkich owoców (test 2), a `copyAll` ma kopiować `List<Integer>` do `List<Number>` (test 4).

## Pytania dodatkowe

1. Dlaczego `List<Integer>` nie jest podtypem `List<Number>`, choć `Integer` jest podtypem `Number`? Pokaż na przykładzie, co by się zepsuło, gdyby był.
2. PECS: kiedy `? extends T`, kiedy `? super T`? Gdzie w tym ćwiczeniu jest „producent”, a gdzie „konsument”?
3. Co to jest wymazywanie typów (*type erasure*)? Dlaczego nie da się napisać `new T()` ani `if (list instanceof List<String>)`?
4. Czy `Pair` mógłby być rekordem? Jak wyglądałaby deklaracja?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
