# 면접 캘린더 서비스

> API를 통해 사용자가 면접 일정 캘린더를 관리할 수 있도록 지원하는 서비스

## 🚩 목차

- [🛠️ 기술 스택](#️-기술-스택)
- [💡 주요 기능](#-주요-기능)
- [📄 API 명세서](#-API-명세서)
- [📊 다이어그램](#-다이어그램)
    - [🔹 유즈케이스 다이어그램](#-유즈케이스-다이어그램)
    - [🔀 데이터 흐름 다이어그램](#-데이터-흐름-다이어그램)
- [🚀 실행 방법](#-실행-방법)
- [📂 폴더 구조](#-폴더-구조)


## 🛠️ 기술 스택

![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=Java&logoColor=white)
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=springboot&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=flat&logo=MongoDB&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=white)

## 💡 주요 기능

## 📄 API 명세서

[![Swagger](https://img.shields.io/badge/Swagger-Green?style=flat&logo=swagger&logoColor=white)](https://daily1hour.github.io/PickMe-Calendar-Service/)

| Method | URI                  | Request Header                     | Query String                                                                 | Request Body                                                                 | Code                                                |
|--------|----------------------|------------------------------------|-----------------------------------------------------------------------------|------------------------------------------------------------------------------|-----------------------------------------------------|
| GET    | /calendar/interviews | Authorization:<br> Bearer \<token> | interviewDetailId?: string <br> name?: string <br> yearMonth?: string       |                                                                              | 200: 성공 <br> 400: 잘못된 요청 <br> 401: 권한 없음 <br> 404: 면접 일정 없음 |
| POST   | /calendar/interviews | Authorization:<br> Bearer \<token> |                                                                             | name: string <br> date: date <br> location: string                           | 200: 성공 <br> 400: 잘못된 요청 <br> 401: 권한 없음 <br> 404: 면접 일정 없음 |
| DELETE | /calendar/interviews | Authorization:<br> Bearer \<token> | interviewDetailId: string                                                   |                                                                              | 200: 성공 <br> 400: 잘못된 요청 <br> 401: 권한 없음 <br> 404: 면접 일정 없음 |
| PUT    | /calendar/interviews | Authorization:<br> Bearer \<token> | interviewDetailId: string                                                   | name?: string <br> date?: date <br> location?: string                        | 200: 성공 <br> 400: 잘못된 요청 <br> 401: 권한 없음 <br> 404: 면접 일정 없음 |

## 📊 다이어그램

### 🔹 유즈케이스 다이어그램
![Image](https://github.com/user-attachments/assets/fd688770-386b-4965-b953-8c018f145eb5)
>면접 캘린더 서비스의 주요 기능을 사용자 관점에서 정의한 유즈케이스 다이어그램입니다.
사용자는 면접 일정을 조회·등록·수정·삭제할 수 있습니다.
### 🔀 데이터 흐름 다이어그램
![Image](https://github.com/user-attachments/assets/17994f3b-ecdd-4fc1-91e2-256d583b4302)
>위 다이어그램은 면접 캘린더 서비스의 데이터 흐름을 나타낸 것입니다.
사용자의 이벤트 발생(조회, 저장, 수정, 삭제)은 프론트엔드(React)를 통해 REST API로 전달되며, Spring Boot 서버는 JWT 기반 인증 후 MongoDB와 연동하여 데이터를 처리합니다. 처리 결과는 다시 사용자에게 반환됩니다.
## 🚀 실행 방법

### 도커환경

```sh
# build
$ docker build -t my-image .

# run
$ docker run --env-file .env -p 8080:8080 my-image:latest
```

### 로컬환경

```sh
# Gradle 빌드 수행 (테스트 제외하고 빌드)
$ ./gradlew clean build -x test --no-daemon

# 빌드된 JAR 파일을 실행
$ java -jar build/libs/calendar-0.0.1-SNAPSHOT.jar
```

## 📂 폴더 구조

> Layered Architecture

```python
calendar
├─ .devcontainer
│  ├─ .dockerignore
│  └─ Dockerfile
├─ .gitattributes
├─ .gitconfig
├─ .github
│  ├─ auto-assign-config.yml
│  ├─ rulesets
│  │  └─ Main-Rule.json
│  ├─ swagger-index.html
│  └─ workflows
│     ├─ auto-assign.yml
│     ├─ generate-swagger.yml
│     └─ gradle-build.yml
├─ .gitignore
├─ .gitmessage
├─ Dockerfile
├─ gradle
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
├─ README.md
├─ setup.ps1
├─ setup.zsh
└─ src
   ├─ .gitkeep
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ pickme
   │  │        └─ calendar
   │  │           ├─ CalendarApplication.java
   │  │           ├─ config
   │  │           │  ├─ MongodbConfig.java
   │  │           │  ├─ security
   │  │           │  │  └─ JwtInterceptor.java
   │  │           │  ├─ SwaggerConfig.java
   │  │           │  └─ WebConfig.java
   │  │           ├─ controller
   │  │           │  └─ CalendarController.java
   │  │           ├─ dto
   │  │           │  ├─ get
   │  │           │  │  ├─ GetCalendarDTO.java
   │  │           │  │  ├─ GetCompanyDTO.java
   │  │           │  │  └─ GetInterviewDetailDTO.java
   │  │           │  ├─ post
   │  │           │  │  ├─ PostCompanyDTO.java
   │  │           │  │  └─ PostInterviewDetailDTO.java
   │  │           │  └─ put
   │  │           │     ├─ PutCompanyDTO.java
   │  │           │     └─ PutInterviewDetailDTO.java
   │  │           ├─ entity
   │  │           │  └─ Calendar.java
   │  │           ├─ exception
   │  │           │  ├─ CustomException.java
   │  │           │  ├─ ErrorCode.java
   │  │           │  └─ GlobalExceptionHandler.java
   │  │           ├─ repository
   │  │           │  ├─ CalendarMongoQueryProcessor.java
   │  │           │  └─ CalendarRepository.java
   │  │           └─ service
   │  │              ├─ CalendarService.java
   │  │              ├─ JwtService.java
   │  │              └─ mapper
   │  │                 └─ CalendarMapper.java
   │  └─ resources
   │     ├─ application-mongodb.properties
   │     ├─ application-mysql.properties
   │     ├─ application.properties
   │     ├─ static
   │     └─ templates
   └─ test
      └─ java
         └─ com
            └─ pickme
               └─ calendar
                  └─ CalendarApplicationTests.java
```