#!/usr/bin/env bash
# Check for exercise 11-usuwanie-w-petli (the description is printed below)
# Run: ./cwiczenia/11-usuwanie-w-petli/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 11-usuwanie-w-petli: Lista zakupów: wyjątek przy dodawaniu i przy odhaczaniu"
run_tests "$CW_DIR"
summary
