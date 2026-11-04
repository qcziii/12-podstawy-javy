#!/usr/bin/env bash
# Ćwiczenie 17-niezmienne-zamowienie: Rekord zamówienia da się zmienić z zewnątrz
# Uruchom: ./cwiczenia/17-niezmienne-zamowienie/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 17-niezmienne-zamowienie: Rekord zamówienia da się zmienić z zewnątrz"
run_tests "$CW_DIR"
summary
