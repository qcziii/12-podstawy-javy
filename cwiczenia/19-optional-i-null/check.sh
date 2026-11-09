#!/usr/bin/env bash
# Check for exercise 19-optional-i-null (the description is printed below)
# Run: ./cwiczenia/19-optional-i-null/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 19-optional-i-null: Profil użytkownika: NullPointerException i NoSuchElementException"
run_tests "$CW_DIR"
forbid "$CW_DIR" "\.get\(\)|isPresent\(\)" "Bez Optional.get() i isPresent()"
summary
