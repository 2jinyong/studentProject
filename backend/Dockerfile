# 톰캣 이미지만 가져옴 (빌드 도구 X, 메이븐 X, 오직 톰캣만!)
FROM tomcat:9.0-jdk11-openjdk-slim

# 로컬에 있는 WAR 파일을 톰캣의 webapps 폴더로 복사
COPY ./ROOT.war /usr/local/tomcat/webapps/ROOT.war

# 서버 실행
EXPOSE 8080
CMD ["catalina.sh", "run"]