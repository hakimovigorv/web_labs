@echo off
call mvn clean package
call docker build -t com.mycompany/Lab1J2EE .
call docker rm -f Lab1J2EE
call docker run -d -p 9080:9080 -p 9443:9443 --name Lab1J2EE com.mycompany/Lab1J2EE