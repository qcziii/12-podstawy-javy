#!/usr/bin/env bash
# Check for exercise 09-zgubiony-klucz (the description is printed below)
# Run: ./cwiczenia/09-zgubiony-klucz/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 09-zgubiony-klucz: Klient po zmianie e-maila znika z rejestru"
run_tests "$CW_DIR"
summary
