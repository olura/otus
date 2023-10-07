FROM bellsoft/liberica-openjre-alpine:21
LABEL authors="olura"
COPY /target/spring-docker-1.0.0.jar /spring-docker-1.0.0.jar
ENTRYPOINT ["java","-jar","/spring-docker-1.0.0.jar"]