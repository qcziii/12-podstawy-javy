#!/usr/bin/env bash
# Check for exercise 20-plik-z-polskimi-znakami (the description is printed below)
# Run: ./cwiczenia/20-plik-z-polskimi-znakami/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 20-plik-z-polskimi-znakami: Notatki zapisane do pliku znikają albo tracą polskie litery"
run_tests "$CW_DIR"
summary
