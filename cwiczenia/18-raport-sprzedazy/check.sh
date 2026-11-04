#!/usr/bin/env bash
# Ćwiczenie 18-raport-sprzedazy: Raport sprzedaży na streamach
# Uruchom: ./cwiczenia/18-raport-sprzedazy/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 18-raport-sprzedazy: Raport sprzedaży na streamach"
run_tests "$CW_DIR"
forbid "$CW_DIR" "(^|[^a-zA-Z])(for|while) *\(" "Bez pętli for i while (same streamy)"
summary
