#!/bin/bash

# Gradle 실행 파일 경로를 설정 (일반적으로 프로젝트 루트 디렉토리에 있는 Gradle Wrapper를 사용)
GRADLE="./gradlew"

# Gradle 빌드 명령어
BUILD_COMMAND="build"

# Spring Boot 애플리케이션 jar 파일 이름 (필요에 따라 수정)
JAR_NAME="simple-sign-backend.jar"

# 빌드 실행
echo "Gradle 빌드를 시작합니다..."
$GRADLE $BUILD_COMMAND

# 빌드 성공 여부 확인
if [ $? -eq 0 ]; then
  echo "Gradle 빌드가 성공적으로 완료되었습니다."

  # Spring Boot 애플리케이션 JAR 파일 실행 (필요에 따라 수정)
  echo "Spring Boot 애플리케이션을 실행합니다..."
  java -jar build/libs/$JAR_NAME
else
  echo "Gradle 빌드에 실패했습니다. 빌드 스크립트를 확인하세요."
  exit 1
fi