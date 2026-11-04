#!/usr/bin/env bash
# Ćwiczenie 06-wspolny-koszyk: Nowy koszyk ma produkty z cudzego koszyka
# Uruchom: ./cwiczenia/06-wspolny-koszyk/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 06-wspolny-koszyk: Nowy koszyk ma produkty z cudzego koszyka"
run_tests "$CW_DIR"
summary
