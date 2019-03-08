#!/bin/bash
set -euo pipefail

  echo "Building slang"

  
  ./gradlew build -x sonar-scala-plugin:check  --info


