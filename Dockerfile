FROM openjdk:latest
COPY . /api
EXPOSE 8080
CMD ["java", "-jar", "/api/target/tasker-api-0.0.1-SNAPSHOT.jar"]