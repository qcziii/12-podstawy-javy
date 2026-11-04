#!/usr/bin/env bash
# Ćwiczenie 15-import-csv: Import cennika zwraca pustą listę zamiast błędu i nie zamyka pliku
# Uruchom: ./cwiczenia/15-import-csv/check.sh (z dowolnego katalogu). Kompiluje ćwiczenie i uruchamia jego testy.
CW_DIR=$(cd "$(dirname "$0")" && pwd)
source "$CW_DIR/../../scripts/lib.sh"
echo "Ćwiczenie 15-import-csv: Import cennika zwraca pustą listę zamiast błędu i nie zamyka pliku"
run_tests "$CW_DIR"
forbid "$CW_DIR" "System\.(out|err)|catch *\( *(Exception|Throwable) " "Bez połykania wyjątków: bez catch (Exception e) i bez System.out/err"
summary
