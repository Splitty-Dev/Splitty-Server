FROM openjdk:21-jdk-slim
COPY api/build/libs/splitty-api.jar splitty-api.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "splitty-api.jar"]