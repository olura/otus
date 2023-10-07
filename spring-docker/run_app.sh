!#/bin/bash
mvn clean
mvn package
docker-compose -f ./spring-docker/docker-compose.yml up