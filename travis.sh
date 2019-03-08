#!/bin/bash
set -euo pipefail


  echo "Building slang"
  
  export INITIAL_VERSION=$(cat gradle.properties | grep version | awk -F= '{print $2}')
  
  ./gradlew  build -x sonar-scala-plugin:check  --info


