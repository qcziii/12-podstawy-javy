#!/usr/bin/env bash
# Ćwiczenie 20-plik-z-polskimi-znakami: Notatki zapisane do pliku znikają albo tracą polskie litery
# Uruchom: ./cwiczenia/20-plik-z-polskimi-znakami/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 20-plik-z-polskimi-znakami: Notatki zapisane do pliku znikają albo tracą polskie litery"
run_tests "$CW_DIR"
summary
