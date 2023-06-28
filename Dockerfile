FROM --platform=linux/amd64 adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/*.jar
COPY ./target/spring-mongo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar" , "/app.jar"]
