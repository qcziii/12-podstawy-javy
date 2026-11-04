#!/usr/bin/env bash
# Ćwiczenie 21-projekt-wypozyczalnia: Projekt: wypożyczalnia sprzętu
# Uruchom: ./cwiczenia/21-projekt-wypozyczalnia/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 21-projekt-wypozyczalnia: Projekt: wypożyczalnia sprzętu"
run_tests "$CW_DIR"
summary
