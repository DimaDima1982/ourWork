#!/usr/bin/env bash

fuser -k 8080/tcp

cd "$(dirname "$0")"
mvn clean install

chmod +x ./target/quotes-0.0.1-SNAPSHOT.jar

java -Xms256m -Xmx1024m -XX:+UseSerialGC -jar ./target/*.jar