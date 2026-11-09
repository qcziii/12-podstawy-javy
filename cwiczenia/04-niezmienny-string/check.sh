#!/usr/bin/env bash
# Check for exercise 04-niezmienny-string (the description is printed below)
# Run: ./cwiczenia/04-niezmienny-string/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 04-niezmienny-string: Normalizacja loginu nic nie zmienia"
run_tests "$CW_DIR"
summary
