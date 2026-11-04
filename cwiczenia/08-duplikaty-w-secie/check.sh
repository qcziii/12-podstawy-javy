#!/usr/bin/env bash
# Ćwiczenie 08-duplikaty-w-secie: HashSet przepuszcza duplikaty produktów
# Uruchom: ./cwiczenia/08-duplikaty-w-secie/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 08-duplikaty-w-secie: HashSet przepuszcza duplikaty produktów"
run_tests "$CW_DIR"
summary
