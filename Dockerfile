# Step 1: Use Maven image to build the project
FROM maven:3.8.7-openjdk-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml /app/
RUN mvn dependency:go-offline

# Copy the source code to the container
COPY src /app/src

# Build the Spring Boot application
RUN mvn clean package -DskipTests

# Step 2: Use OpenJDK to run the Spring Boot application
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/springProject-0.0.1-SNAPSHOT.jar /app/springProject.jar

# Expose port 8080 for the Spring Boot app
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/springProject.jar"]
