# 1단계: 빌드 환경 (Gradle Wrapper 사용)
FROM eclipse-temurin:17-jdk-alpine AS build

# 필요한 패키지 설치
RUN apk add --no-cache bash

# 작업 디렉토리 설정
WORKDIR /app

# 소스 복사 및 Gradle Wrapper 실행
COPY . .
RUN ./gradlew clean build -x test --no-daemon

# 2단계: 실행 환경 (JRE)
FROM eclipse-temurin:17-jdk-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일을 복사
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]