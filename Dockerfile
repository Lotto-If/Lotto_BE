# OpenJDK 17 이미지 사용
FROM amazoncorretto:17

# 작업 디렉토리 생성
WORKDIR /app

# 'build/libs/' 경로에 생성된 JAR 파일을 컨테이너의 docker-springboot.jar로 복사
COPY ./build/libs/*.jar docker-springboot.jar

# 애플리케이션 실행 시 수행할 명령어
ENTRYPOINT ["java", "-jar", "docker-springboot.jar"]
