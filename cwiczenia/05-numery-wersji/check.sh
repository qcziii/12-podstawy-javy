#!/usr/bin/env bash
# Ćwiczenie 05-numery-wersji: Numer wersji 1.2.3 jest „niepoprawny”, a 1.10 starsze od 1.9
# Uruchom: ./cwiczenia/05-numery-wersji/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 05-numery-wersji: Numer wersji 1.2.3 jest „niepoprawny”, a 1.10 starsze od 1.9"
run_tests "$CW_DIR"
summary
