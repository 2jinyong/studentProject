# 빌드 환경 (Maven) 이미 설정 세팅된 이미지
FROM maven:3.8.5-openjdk-11 AS build

# 작업폴더 app 생성
WORKDIR /app

# 내작업폴더 기준모든것을 컨터이너로 복사(/app)
COPY . .

# .war 파일 빌드시 테스트코드 실행은 건너뛰기
RUN mvn clean package -DskipTests

# 실행 환경 (톰캣 9 + JDK 11)
FROM tomcat:9.0-jdk11-openjdk-slim

# 기존 톰캣에 기본으로 들어있는 예제 파일들을 제거합니다.
RUN rm -rf /usr/local/tomcat/webapps/*

# 빌드된 ROOT.war를 톰캣 webapps 경로로 복사(빌드이름 ROOT로 이미 설정해놨기 때문)
COPY --from=build /app/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

# 8080 포트 개방
EXPOSE 8080

# 톰캣 실행 (별도 CMD 필요 없음, tomcat 이미지에 기본 설정되어 있음)
CMD ["catalina.sh", "run"]