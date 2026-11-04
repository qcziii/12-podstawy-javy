#!/usr/bin/env bash
# Ćwiczenie 04-niezmienny-string: Normalizacja loginu nic nie zmienia
# Uruchom: ./cwiczenia/04-niezmienny-string/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 04-niezmienny-string: Normalizacja loginu nic nie zmienia"
run_tests "$CW_DIR"
summary
