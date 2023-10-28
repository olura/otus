FROM maven:3.9.4-eclipse-temurin-17-alpine
LABEL authors="olura"
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package
RUN mv /app/target/spring-docker-latest.jar /app/spring-docker.jar
ENTRYPOINT ["java","-jar","/app/spring-docker.jar"]