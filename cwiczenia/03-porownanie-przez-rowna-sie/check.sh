#!/usr/bin/env bash
# Ćwiczenie 03-porownanie-przez-rowna-sie: Właściciel dokumentu nie ma do niego dostępu
# Uruchom: ./cwiczenia/03-porownanie-przez-rowna-sie/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 03-porownanie-przez-rowna-sie: Właściciel dokumentu nie ma do niego dostępu"
run_tests "$CW_DIR"
summary
