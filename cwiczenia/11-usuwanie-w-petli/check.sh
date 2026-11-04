#!/usr/bin/env bash
# Ćwiczenie 11-usuwanie-w-petli: Lista zakupów: wyjątek przy dodawaniu i przy odhaczaniu
# Uruchom: ./cwiczenia/11-usuwanie-w-petli/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 11-usuwanie-w-petli: Lista zakupów: wyjątek przy dodawaniu i przy odhaczaniu"
run_tests "$CW_DIR"
summary
