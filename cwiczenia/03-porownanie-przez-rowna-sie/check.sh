#!/usr/bin/env bash
# Check for exercise 03-porownanie-przez-rowna-sie (the description is printed below)
# Run: ./cwiczenia/03-porownanie-przez-rowna-sie/check.sh (from any directory). Compiles the exercise and runs its tests.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 03-porownanie-przez-rowna-sie: Właściciel dokumentu nie ma do niego dostępu"
run_tests "$CW_DIR"
summary
