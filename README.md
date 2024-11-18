# Description
Gets song information from iTunes API and optionally saves it to the local DB. Supports users (who can collect their list of songs), user roles (normal, admin) and authorization using JWT

I wrote this to test many capabilities of Spring Boot in a single project

## How to run
- It's required to run docker database first: go to __docker/db__ folder and do `docker-compose up -d`
- Then you could either execute the app via IDE or go to __docker/web__ folder and do `docker-compose up`

## How to use
- Docs could be found in http://localhost:8081/swagger-ui/
- Creating a user: http://localhost:8081/swagger-ui/#/user-controller/createUserUsingPOST
- There's an already created admin user which name and password is __admin__
- Logging in with credentials: send POST request to http://localhost:8081/login specifying __userName__ and __password__ in JSON body<br>
  You can use the pre-created admin user: `{"userName":"admin", "password":"admin"}`
- In the response header, you will see a bearer token. Use it for __Authorization__ in your request header to other endpoints.
  I suggest using Postman for sending requests, it lets you save the authorization token so that you wouldn't need to specify it every time
