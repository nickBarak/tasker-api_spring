FROM maven:latest
COPY . /api
RUN mvn clean verify

FROM openjdk:latest
EXPOSE 8081
CMD ["java", "-jar", "/api/target/tasker-api-0.0.1-SNAPSHOT.jar"]