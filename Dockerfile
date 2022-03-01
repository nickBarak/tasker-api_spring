FROM openjdk:latest
COPY . .
RUN sudo ./mvnw clean verify
EXPOSE 8081
CMD ["java", "-jar", "/target/tasker-api-0.0.1-SNAPSHOT.jar"]