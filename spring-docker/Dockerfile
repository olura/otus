FROM maven:3.9.4-eclipse-temurin-17-alpine
LABEL authors="olura"
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package
ENTRYPOINT ["java","-jar","/app/target/spring-docker-1.0.0.jar"]