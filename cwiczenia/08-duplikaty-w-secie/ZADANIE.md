# 08. HashSet przepuszcza duplikaty produktów

Poziom: **łatwy/średni (2 błędy)** · Rodzaj: **napraw** · Teoria: [05. Dziedziczenie i interfejsy](../../teoria/05-dziedziczenie-i-interfejsy.md), [06. equals, hashCode, Comparator](../../teoria/06-equals-hashcode-comparator.md)

## Uruchomienie

```bash
./cwiczenia/08-duplikaty-w-secie/check.sh
```

## Zgłoszenie

Import katalogu (`CatalogImport`) łączy pliki od kilku dostawców. Ten sam produkt (ten sam SKU) występuje w wielu plikach i miał być w katalogu raz. Klasa `Product` „ma przecież `equals`”, a mimo to:

- `HashSet<Product>` zawiera kilka razy ten sam SKU,
- `List.contains` nie znajduje produktu, który jest na liście,
- stan magazynu (`HashMap<Product, Integer>`) ma osobny wpis dla każdej sztuki.

**Oczekiwane:** dwa produkty z tym samym SKU są równe (nazwa i cena nie mają znaczenia), a kolekcje `HashSet`, `HashMap` i `List.contains` traktują je jak jeden produkt.

## Wskazówki

- Dopisz `@Override` nad `equals` i zobacz, co powie kompilator.
- Co robi `HashSet.add`, zanim w ogóle zawoła `equals`?

## Pytania dodatkowe

1. Czym się różni przeciążenie (*overload*) od nadpisania (*override*)? Która wersja `equals` jest wołana przez `HashSet`, a która przez `p1.equals(p2)`, gdy oba są typu `Product`?
2. Jaki jest kontrakt `equals` i `hashCode`? Co się stanie, gdy `hashCode` zwróci zawsze 42? Czy to będzie poprawne? Czy szybkie?
3. Dlaczego w `equals` porównujemy tylko SKU, a nie wszystkie pola?
4. Jak wyglądałaby ta klasa jako `record`? Czy wtedy equals dalej porównywałby tylko SKU?

## Kiedy skończysz

`check.sh` ma wypisać `WYNIK: wszystkie sprawdzenia przeszły`. Potem odpowiedz na pytania dodatkowe.
