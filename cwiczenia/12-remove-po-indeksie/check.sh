#!/usr/bin/env bash
# Check for exercise 12-remove-po-indeksie (the description is printed below)
# Run: ./cwiczenia/12-remove-po-indeksie/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 12-remove-po-indeksie: Playlista usuwa nie ten utwór, a z zewnątrz da się ją zepsuć"
run_tests "$CW_DIR"
summary
