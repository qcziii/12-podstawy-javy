#!/usr/bin/env bash
# Check for exercise 14-generyczne-narzedzia (the description is printed below)
# Run: ./cwiczenia/14-generyczne-narzedzia/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 14-generyczne-narzedzia: Narzędzia do kolekcji bez generyków: testy się nie kompilują"
run_tests "$CW_DIR"
summary
