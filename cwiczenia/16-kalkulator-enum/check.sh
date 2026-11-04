#!/usr/bin/env bash
# Ćwiczenie 16-kalkulator-enum: Kalkulator na enumie
# Uruchom: ./cwiczenia/16-kalkulator-enum/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 16-kalkulator-enum: Kalkulator na enumie"
run_tests "$CW_DIR"
forbid "$CW_DIR" "switch|ordinal\(\)" "Bez switch i ordinal() (każda stała enuma liczy sama)"
summary
