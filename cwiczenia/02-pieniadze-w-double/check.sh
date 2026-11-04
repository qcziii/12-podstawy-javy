#!/usr/bin/env bash
# Ćwiczenie 02-pieniadze-w-double: Faktura nie zgadza się o grosz
# Uruchom: ./cwiczenia/02-pieniadze-w-double/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 02-pieniadze-w-double: Faktura nie zgadza się o grosz"
run_tests "$CW_DIR"
summary
