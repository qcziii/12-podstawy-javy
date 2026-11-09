#!/usr/bin/env bash
# Check for exercise 08-duplikaty-w-secie (the description is printed below)
# Run: ./cwiczenia/08-duplikaty-w-secie/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 08-duplikaty-w-secie: HashSet przepuszcza duplikaty produktów"
run_tests "$CW_DIR"
summary
