#!/usr/bin/env bash
# Ćwiczenie 10-sortowanie-produktow: Sortowanie po cenie miesza produkty, a katalog gubi warianty
# Uruchom: ./cwiczenia/10-sortowanie-produktow/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 10-sortowanie-produktow: Sortowanie po cenie miesza produkty, a katalog gubi warianty"
run_tests "$CW_DIR"
summary
