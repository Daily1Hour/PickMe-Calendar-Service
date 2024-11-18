# OpenJDK 17의 경량 이미지 사용
FROM openjdk:17-jdk-slim

# 필요한 패키지와 도구 설치
# 파일 다운로드 도구
# ZIP 파일 압축 해제 도구
# 버전 관리 도구
# 불필요한 apt 캐시 제거로 이미지 크기 최소화
RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    git \
    && rm -rf /var/lib/apt/lists/*

# 사용할 Gradle 버전 정의
ENV GRADLE_VERSION=8.10.2

# Gradle 설치
# Gradle ZIP 파일 다운로드
# ZIP 파일 압축 해제
# 다운로드한 ZIP 파일 삭제
# gradle 명령어를 심볼릭 링크로 연결
RUN mkdir -p /opt/gradle && \
    curl -fsSL https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -o gradle.zip && \
    unzip gradle.zip -d /opt/gradle && \
    rm gradle.zip && \
    ln -s /opt/gradle/gradle-${GRADLE_VERSION}/bin/gradle /usr/local/bin/gradle

# 컨테이너 내부의 작업 디렉토리 설정
WORKDIR /workspace

# Gradle 설정 파일 복사
# build.gradle 및 settings.gradle 파일을 복사하여 의존성 캐시를 활성화
COPY build.gradle settings.gradle* ./

# Gradle을 사용해 의존성 미리 다운로드 (테스트는 제외)
# 빌드 실패해도 Docker 빌드가 계속 진행되도록 설정
RUN gradle build -x test --no-daemon || true

# 애플리케이션 소스 코드 복사
COPY . .

# 외부에 노출할 포트 설정 (Spring Boot 기본 포트)
EXPOSE 8080

# Spring Boot Devtools 활성화 (개발 중 변경 사항 자동 반영)
# 컬러 로그 활성화
# Spring Boot Devtools 활성화
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS="-Dspring.devtools.restart.enabled=true"

# Gradle 명령어로 애플리케이션 실행
CMD ["gradle", "bootRun"]