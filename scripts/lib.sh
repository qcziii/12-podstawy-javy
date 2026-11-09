#!/usr/bin/env bash
# Shared functions for the exercises' check.sh scripts.
# Require bash, Maven (mvn) and Java 21. On Windows: Git Bash.

PASS=0
FAIL=0

ok()  { echo "  [OK]   $1"; PASS=$((PASS+1)); }
bad() { echo "  [FAIL] $1"; FAIL=$((FAIL+1)); }

# Turns XML entities from the surefire report into plain characters.
xml_decode() {
  sed -e 's/&#10;/\n/g' -e 's/&#13;//g' -e 's/&quot;/"/g' -e "s/&apos;/'/g" \
      -e 's/&lt;/</g' -e 's/&gt;/>/g' -e 's/&amp;/\&/g'
}

# run_tests <exercise dir>
# Compiles the exercise and runs its tests (mvn test), then prints the result of each test.
run_tests() {
  local dir log reports
  dir=$(cd "$1" && pwd)
  log="$dir/target/check-mvn.log"
  reports="$dir/target/surefire-reports"
  mkdir -p "$dir/target"
  rm -rf "$reports"
  echo "  ... kompilacja i testy (mvn test), pierwsze uruchomienie pobiera zależności"
  (cd "$dir" && mvn -B test > "$log" 2>&1)

  if ! ls "$reports"/TEST-*.xml > /dev/null 2>&1; then
    if grep -q 'COMPILATION ERROR' "$log"; then
      bad "Projekt się nie kompiluje. Błędy kompilacji:"
      { sed -n '/COMPILATION ERROR/,/Failed to execute goal/p' "$log" \
          | grep -vE 'COMPILATION ERROR|Failed to execute goal|^\[INFO\]|warnings found and -Werror'
        grep -E '^\[WARNING\] .*\.java:\[' "$log" | sed 's/^\[WARNING\]/[OSTRZEŻENIE]/'
      } | sed -e "s#$dir/##g" -e 's#\\#/#g' | head -12 | sed 's/^/           /'
      if grep -q 'warnings found and -Werror' "$log"; then
        echo "           (w tym ćwiczeniu ostrzeżenia kompilatora traktujemy jak błędy)"
      fi
    else
      bad "Testy nie wystartowały. Ostatnie błędy Mavena:"
      grep -E '^\[ERROR\]' "$log" | grep -vE 'Re-run Maven|Help 1|^\[ERROR\] *$|-> \[Help|For more information|full stack trace|full debug logging|http://cwiki' \
        | head -12 | sed 's/^/           /'
    fi
    echo "           (pełny log: target/check-mvn.log w katalogu ćwiczenia)"
    return
  fi

  local line status name msg
  while IFS=$'\t' read -r status name msg; do
    name=$(printf '%s' "$name" | xml_decode)
    if [ "$status" = "OK" ]; then
      ok "$name"
    else
      bad "$name"
      printf '%s\n' "$msg" | xml_decode | sed '/^[[:space:]]*$/d' | head -8 | sed 's/^/           /'
    fi
  done < <(cat "$reports"/TEST-*.xml | awk '
    /<testcase / {
      name = ""; if (match($0, /name="[^"]*"/)) name = substr($0, RSTART + 6, RLENGTH - 7)
      st = "OK"; msg = ""
      if ($0 ~ /\/>[[:space:]]*$/) { print st "\t" name "\t"; inCase = 0 } else { inCase = 1 }
      next
    }
    inCase && /<(failure|error)[ >]/ {
      st = "FAIL"
      type = ""; if (match($0, /type="[^"]*"/)) type = substr($0, RSTART + 6, RLENGTH - 7)
      if (match($0, /message="[^"]*"/)) {
        msg = substr($0, RSTART + 9, RLENGTH - 10)
        # Not an assertion failure but an exception from the code: show its type too.
        if (type != "" && type !~ /AssertionFailedError|MultipleFailuresError|AssertionError/) msg = type ": " msg
      }
      else if (type != "") msg = "wyjątek " type " (bez komunikatu)"
      else msg = "(brak komunikatu, szczegóły w target/surefire-reports)"
    }
    inCase && /<\/testcase>/ { print st "\t" name "\t" msg; inCase = 0 }
  ')
}

# forbid <exercise dir> <grep -E pattern> <description>
# Checks that the exercise code (src/main) does not use a forbidden construct.
forbid() {
  local hits
  hits=$(grep -rnE "$2" "$1/src/main/java" 2>/dev/null | sed -e "s#$1/##")
  if [ -n "$hits" ]; then
    bad "$3"
    printf '%s\n' "$hits" | head -5 | sed 's/^/           /'
  else
    ok "$3"
  fi
}

summary() {
  echo
  echo "Wynik: $PASS OK, $FAIL FAIL"
  if [ "$FAIL" -eq 0 ] && [ "$PASS" -gt 0 ]; then
    echo "WYNIK: wszystkie sprawdzenia przeszły"
  else
    echo "WYNIK: są błędy"
    exit 1
  fi
}
