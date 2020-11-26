FROM maven:3.6.3-jdk-11 AS build
COPY src src
COPY pom.xml pom.xml
RUN mvn clean install -DskipTests

FROM openjdk:11
EXPOSE 8081
COPY --from=build target/*.jar songs.jar
ADD /src/main/resources/response.json /src/main/resources/response.json
ENTRYPOINT ["java", "-jar", "/songs.jar"]