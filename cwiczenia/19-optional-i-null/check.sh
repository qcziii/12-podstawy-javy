#!/usr/bin/env bash
# Ćwiczenie 19-optional-i-null: Profil użytkownika: NullPointerException i NoSuchElementException
# Uruchom: ./cwiczenia/19-optional-i-null/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 19-optional-i-null: Profil użytkownika: NullPointerException i NoSuchElementException"
run_tests "$CW_DIR"
forbid "$CW_DIR" "\.get\(\)|isPresent\(\)" "Bez Optional.get() i isPresent()"
summary
