# openjdk 이미지를 사용하겠다
FROM openjdk:8-jdk-alpine

# contact 경로
MAINTAINER williamson@naver.com

# 컨테이너의 데이터들을 다음과 같은 경로에 마운트함
VOLUME /tmp

# 실행시킬 컨테이너의 어떤 포트를 열어 둘 것인지
EXPOSE 8080

# 변수 설정
# maven의 경우 target 폴더 하위에 artifactId 의 이름으로 jar 파일이 존재한다
ARG JAR_FILE=target/SpringFilter-0.0.1-SNAPSHOT.jar

# jar 파일을 컨테이너에 추가하는 과정
ADD ${JAR_FILE} SpringFilter.jar

# jar 파일을 실행시키는 명령어 모음
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/SpringFilter.jar"]