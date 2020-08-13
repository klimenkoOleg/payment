#!/usr/bin/env bash
mvn clean package
docker build -t oklimenko/payment .
docker tag oklimenko/payment oklimenko/payment:1.0.12
#docker push oklimenko/payment:1.0.8