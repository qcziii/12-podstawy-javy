#!/usr/bin/env bash
# Check for exercise 18-raport-sprzedazy (the description is printed below)
# Run: ./cwiczenia/18-raport-sprzedazy/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 18-raport-sprzedazy: Raport sprzedaży na streamach"
run_tests "$CW_DIR"
forbid "$CW_DIR" "(^|[^a-zA-Z])(for|while) *\(" "Bez pętli for i while (same streamy)"
summary
