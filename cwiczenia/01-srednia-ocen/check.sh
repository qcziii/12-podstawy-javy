#!/usr/bin/env bash
# Check for exercise 01-srednia-ocen (the description is printed below)
# Run: ./cwiczenia/01-srednia-ocen/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 01-srednia-ocen: Średnia ocen jest zaokrąglona, a suma czasu wychodzi ujemna"
run_tests "$CW_DIR"
summary
