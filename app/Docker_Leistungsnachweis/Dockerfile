# Use an official OpenJDK 22 image as the base for building the application
FROM openjdk:22-jdk-slim as builder
# Use an official Gradle image to build the Spring Boot app
FROM gradle:8.10.2-jdk21 AS builder


# Set the working directory
WORKDIR /app/Docker_Leistungsnachweis

# Copy the Gradle build files and source code into the image
COPY build.gradle.kts settings.gradle.kts gradlew gradlew.bat ./
COPY gradle ./gradle
COPY src ./src

# Build the application
RUN gradle clean shadowJar --no-daemon


# Use a new image for the final stage
FROM openjdk:22-jdk-slim

# Set the working directory in the new container
WORKDIR /app/Docker_Leistungsnachweis

# Copy the built JAR file from the builder stage
COPY --from=builder /app/Docker_Leistungsnachweis/build/libs/*.jar app.jar

# Command to run the application
CMD ["java", "-jar", "app.jar"]
