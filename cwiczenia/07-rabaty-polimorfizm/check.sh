#!/usr/bin/env bash
# Ćwiczenie 07-rabaty-polimorfizm: Rabaty: interfejs i polimorfizm zamiast sprawdzania typu
# Uruchom: ./cwiczenia/07-rabaty-polimorfizm/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 07-rabaty-polimorfizm: Rabaty: interfejs i polimorfizm zamiast sprawdzania typu"
run_tests "$CW_DIR"
forbid "$CW_DIR" "instanceof|switch|getClass\(\)" "Bez instanceof, switch i getClass() (polimorfizm zamiast sprawdzania typu)"
summary
