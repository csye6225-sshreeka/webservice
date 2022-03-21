**CSYE 6225 - Spring 2022**
Name:Shreekara SS 
NUID: 001545668
Email: ss.s@northeastern.edu

**Technology Stack** 

This web application is developed using spring boot and uses rest controller for achieving any use case.


**Build Instructions**

Pre-Requisites: Need to have postman installed

- Clone this repository:git@github.com:csye6225-sshreeka/webservice.git into the local system using SSH Key.



**Deploy Instructions**

Create user

>curl -X POST \
  http://ec2-35-170-60-205.compute-1.amazonaws.com/v1/user/ \
  -H 'Accept: */*' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -H 'Host: ec2-35-170-60-205.compute-1.amazonaws.com:8080' \
  -H 'accept-encoding: gzip, deflate' \
  -H 'cache-control: no-cache' \
  -d '{
   	"fname": "Piyush",
   	"lname": "Kumar",
        "emailId" : "Piyush@gmail.com",
        "password" : "Piyush123$$$"
}'

Response: 
{"id":33,"fname":"Piyush","lname":"Kumar","emailId":"Piyush@gmail.com","account_created":"2022-02-16T15:26:55.042+00:00","account_updated":null}%                                            ➜  ~ 

Get user

>curl -X GET \
  http://ec2-35-170-60-205.compute-1.amazonaws.com:8083/v1/user/self\
  -H 'Accept: */*' \
  -H 'Authorization: Basic UGl5dXNoQGdtYWlsLmNvbTpQaXl1c2gxMjMkJCQ=' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:8083' \
  -H 'accept-encoding: gzip, deflate' \
  -H 'cache-control: no-cache'

Response: 
{"id":33,"fname":"Piyush","lname":"Kumar","emailId":"Piyush@gmail.com","account_created":"2022-02-16T15:26:55.042+00:00","account_updated":null}%                                             
d

.
