#!/bin/bash
GRADLE="./gradlew"
BUILD_COMMAND="build"

echo "설정파일을 가져옵니다..."
cp ~/application.properties ./src/main/resources

echo "Gradle 빌드를 시작합니다..."
$GRADLE $BUILD_COMMAND

if [ $? -eq 0 ]; then
  echo "Gradle 빌드가 성공적으로 완료되었습니다."  
else
  echo "Gradle 빌드에 실패했습니다. 빌드 스크립트를 확인하세요."
  exit 1
fi
