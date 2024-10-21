# Use an official OpenJDK 22 image as the base
FROM openjdk:22-jdk-slim as builder

# Set the working directory
WORKDIR /app/Docker_Leistungsnachweis

# Copy the Gradle build files and source code into the image
COPY build.gradle.kts settings.gradle.kts gradlew gradlew.bat ./
COPY gradle ./gradle
COPY src ./src

# Build the application
RUN ./gradlew build --no-daemon

# Final image
FROM openjdk:22-jdk-slim

# Set the working directory
WORKDIR /app/Docker_Leistungsnachweis