#!/usr/bin/env bash
# Check for exercise 06-wspolny-koszyk (the description is printed below)
# Run: ./cwiczenia/06-wspolny-koszyk/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 06-wspolny-koszyk: Nowy koszyk ma produkty z cudzego koszyka"
run_tests "$CW_DIR"
summary
