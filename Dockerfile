FROM openjdk:21-jdk-slim
COPY build/libs/splitty-server.jar ./splitty-server.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "./splitty-server.jar"]