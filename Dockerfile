# build stage

FROM maven:latest AS build

COPY pom.xml pom.xml

COPY src src

RUN mvn clean verify


# package stage

FROM openjdk:latest

COPY --from=build target/tasker-api-0.0.1-SNAPSHOT.jar tasker-api.jar

EXPOSE 8081

CMD ["java", "-jar", "tasker-api.jar"]