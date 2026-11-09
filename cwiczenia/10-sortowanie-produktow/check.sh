#!/usr/bin/env bash
# Check for exercise 10-sortowanie-produktow (the description is printed below)
# Run: ./cwiczenia/10-sortowanie-produktow/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 10-sortowanie-produktow: Sortowanie po cenie miesza produkty, a katalog gubi warianty"
run_tests "$CW_DIR"
summary
