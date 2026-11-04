#!/usr/bin/env bash
# Ćwiczenie 13-dziennik-ocen: Dziennik ocen na mapach
# Uruchom: ./cwiczenia/13-dziennik-ocen/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 13-dziennik-ocen: Dziennik ocen na mapach"
run_tests "$CW_DIR"
summary
