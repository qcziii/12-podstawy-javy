#!/usr/bin/env bash
# Check for exercise 05-numery-wersji (the description is printed below)
# Run: ./cwiczenia/05-numery-wersji/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 05-numery-wersji: Numer wersji 1.2.3 jest „niepoprawny”, a 1.10 starsze od 1.9"
run_tests "$CW_DIR"
summary
