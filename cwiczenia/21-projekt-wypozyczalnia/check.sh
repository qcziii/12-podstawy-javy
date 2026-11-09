#!/usr/bin/env bash
# Check for exercise 21-projekt-wypozyczalnia (the description is printed below)
# Run: ./cwiczenia/21-projekt-wypozyczalnia/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 21-projekt-wypozyczalnia: Projekt: wypożyczalnia sprzętu"
run_tests "$CW_DIR"
summary
