**CSYE 6225 - Spring 2022**
Name:Shreekara SS 
NUID: 001545668
Email: ss.s@northeastern.edu

**Technology Stack** 

This web application is developed using spring boot and uses rest controller for achieving any use case.


**Build Instructions**

Pre-Requisites: Need to have postman installed

- Clone this repository:git@github.com:csye6225-sshreeka/webservice.git into the local system using SSH Key.
- Traverse to the folder csye6225/dev/ccwebapp/webapp
- Run WebappApplication by going to spring-boot-first-web-application/src/main/java/com/firstwebapp/springboot/web/Main.java



**Deploy Instructions**

Create user

>curl -X POST \
  http://localhost:9001/users \
  -H 'Accept: */*' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:9001' \
  -H 'accept-encoding: gzip, deflate' \
  -H 'cache-control: no-cache' \
  -d '{
        "name": "Shrre",
        "age": 25
}'

Response: 
Succesfully created userUser [name=Shrre, age=25]


Get user

>curl -X GET \
   http://localhost:9001/users \
  -H 'Accept: */*' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:9001' \

Response: 
{"name":"Shrre","age":25}% 

