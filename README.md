# songs
Songs API with iTunes support

## How to run
- It's required to run docker database first. cd to docker/db and execute docker-compose up -d
- Then you could either execute the app via IDE or cd to docker/web and execute docker-compose up

## How to use
- Docs could be found in http://localhost:8081/swagger-ui/
- Creating a user: http://localhost:8081/swagger-ui/#/user-controller/createUserUsingPOST
- There's an already created admin user. User name = admin. Password = admin.
- Logging in with credentials: send a POST request to http://localhost:8081/login/ and specify userName and password as JSON body. E.g. {"userName":"admin", "password":"admin"}
- You will get a bearer token in response. Use it for authorization in your requests to other endpoints. I suggest using Postman for sending requests, it lets you save the authorization token, so that you woudn't need to specify it every time.
