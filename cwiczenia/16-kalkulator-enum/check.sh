#!/usr/bin/env bash
# Check for exercise 16-kalkulator-enum (the description is printed below)
# Run: ./cwiczenia/16-kalkulator-enum/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 16-kalkulator-enum: Kalkulator na enumie"
run_tests "$CW_DIR"
forbid "$CW_DIR" "switch|ordinal\(\)" "Bez switch i ordinal() (każda stała enuma liczy sama)"
summary
