#!/bin/sh
mvn clean package && docker build -t com.mycompany/Lab1J2EE .
docker rm -f Lab1J2EE || true && docker run -d -p 9080:9080 -p 9443:9443 --name Lab1J2EE com.mycompany/Lab1J2EE