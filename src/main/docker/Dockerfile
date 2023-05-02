FROM openjdk:8-jdk-alpine

COPY target/userapi*.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]