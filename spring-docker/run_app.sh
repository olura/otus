#!/bin/bash
mvn clean
mvn package -DskipTests=true
docker-compose -f ./spring-docker/docker-compose.yml up