#!/bin/sh
mvn clean package && docker build -t com.mycompany/Lab4EE .
docker rm -f Lab4EE || true && docker run -d -p 9080:9080 -p 9443:9443 --name Lab4EE com.mycompany/Lab4EE