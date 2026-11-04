#!/usr/bin/env bash
# Ćwiczenie 01-srednia-ocen: Średnia ocen jest zaokrąglona, a suma czasu wychodzi ujemna
# Uruchom: ./cwiczenia/01-srednia-ocen/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 01-srednia-ocen: Średnia ocen jest zaokrąglona, a suma czasu wychodzi ujemna"
run_tests "$CW_DIR"
summary
