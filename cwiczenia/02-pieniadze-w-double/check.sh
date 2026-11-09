#!/usr/bin/env bash
# Check for exercise 02-pieniadze-w-double (the description is printed below)
# Run: ./cwiczenia/02-pieniadze-w-double/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 02-pieniadze-w-double: Faktura nie zgadza się o grosz"
run_tests "$CW_DIR"
summary
