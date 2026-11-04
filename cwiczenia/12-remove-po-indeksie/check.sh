#!/usr/bin/env bash
# Ćwiczenie 12-remove-po-indeksie: Playlista usuwa nie ten utwór, a z zewnątrz da się ją zepsuć
# Uruchom: ./cwiczenia/12-remove-po-indeksie/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 12-remove-po-indeksie: Playlista usuwa nie ten utwór, a z zewnątrz da się ją zepsuć"
run_tests "$CW_DIR"
summary
