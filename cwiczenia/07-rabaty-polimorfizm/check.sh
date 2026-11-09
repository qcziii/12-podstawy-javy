#!/usr/bin/env bash
# Check for exercise 07-rabaty-polimorfizm (the description is printed below)
# Run: ./cwiczenia/07-rabaty-polimorfizm/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 07-rabaty-polimorfizm: Rabaty: interfejs i polimorfizm zamiast sprawdzania typu"
run_tests "$CW_DIR"
forbid "$CW_DIR" "instanceof|switch|getClass\(\)" "Bez instanceof, switch i getClass() (polimorfizm zamiast sprawdzania typu)"
summary
