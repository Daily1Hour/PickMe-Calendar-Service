# 1단계: 빌드 환경 (Gradle로 빌드)
FROM gradle:7.6.2-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle에 의존성을 설치하고 빌드
COPY . .
RUN gradle clean build -x test --no-daemon

# 2단계: 실행 환경 (JRE)
FROM eclipse-temurin:17-jdk-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일을 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]