#!/usr/bin/env bash
# Ćwiczenie 09-zgubiony-klucz: Klient po zmianie e-maila znika z rejestru
# Uruchom: ./cwiczenia/09-zgubiony-klucz/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 09-zgubiony-klucz: Klient po zmianie e-maila znika z rejestru"
run_tests "$CW_DIR"
summary
