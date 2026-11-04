#!/usr/bin/env bash
# Ćwiczenie 14-generyczne-narzedzia: Narzędzia do kolekcji bez generyków: testy się nie kompilują
# Uruchom: ./cwiczenia/14-generyczne-narzedzia/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 14-generyczne-narzedzia: Narzędzia do kolekcji bez generyków: testy się nie kompilują"
run_tests "$CW_DIR"
summary
