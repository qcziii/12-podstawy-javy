#!/usr/bin/env bash
# Check for exercise 17-niezmienne-zamowienie (the description is printed below)
# Run: ./cwiczenia/17-niezmienne-zamowienie/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 17-niezmienne-zamowienie: Rekord zamówienia da się zmienić z zewnątrz"
run_tests "$CW_DIR"
summary
