#!/usr/bin/env bash
# Check for exercise 13-dziennik-ocen (the description is printed below)
# Run: ./cwiczenia/13-dziennik-ocen/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 13-dziennik-ocen: Dziennik ocen na mapach"
run_tests "$CW_DIR"
summary
