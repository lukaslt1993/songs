# Description
Gets song information from iTunes API and optionally saves it to the local DB

I wrote this to test many Spring Boot tools in a single project. Includes roles and authorization

## How to run
- It's required to run docker database first. __cd__ to __docker/db__ and execute __docker-compose up -d__
- Then you could either execute the app via IDE or __cd__ to __docker/web__ and execute __docker-compose up__

## How to use
- Docs could be found in http://localhost:8081/swagger-ui/
- Creating a user: http://localhost:8081/swagger-ui/#/user-controller/createUserUsingPOST
- There's an already created admin user which name and password is __admin__
- Logging in with credentials: send a POST request to http://localhost:8081/login and specify __userName__ and __password__ as JSON body.
  You can use the pre-created admin user: {"userName":"admin", "password":"admin"}
- In the response header, you will see a bearer token. Use it for __Authorization__ in your request header to other endpoints.
  I suggest using Postman for sending requests, it lets you save the authorization token so that you wouldn't need to specify it every time
