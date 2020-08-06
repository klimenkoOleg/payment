#!/usr/bin/env bash
mvn clean package
./docker-build.sh
./docker-run.sh