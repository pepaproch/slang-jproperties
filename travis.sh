#!/bin/bash
set -euo pipefail


  echo "Building slang"
  
  export INITIAL_VERSION=$(cat gradle.properties | grep version | awk -F= '{print $2}')

  ./gradlew sonarqube \
  -Dsonar.projectKey=pepaproch_slang-jproperties \
  -Dsonar.organization=pepaproch-github \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login=8b0bbe7510d0076ee15dde458cf327c7b13cfcf5

#  ./gradlew --info --no-daemon --console plain \
#    -DbuildNumber=$TRAVIS_BUILD_NUMBER \
#     sonarqube \
#    -Dsonar.host.url=$SONAR_HOST_URL \
#    -Dsonar.login=$SONAR_TOKEN \
#    -Dsonar.projectVersion=$INITIAL_VERSION \
#    -Dsonar.analysis.buildNumber=$TRAVIS_BUILD_NUMBER \
#    -Dsonar.analysis.pipeline=$TRAVIS_BUILD_NUMBER \
#    -Dsonar.analysis.sha1=$TRAVIS_COMMIT \
#    -Dsonar.organization=pepaproch-github



