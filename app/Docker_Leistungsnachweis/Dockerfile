# Use an official OpenJDK 22 image as the base
FROM openjdk:22-jdk-slim as builder

# Set the working directory
WORKDIR /app/Docker_Leistungsnachweis

# Copy the Gradle build files and source code into the image
COPY build.gradle settings.gradle gradlew gradlew.bat ./
COPY gradle ./gradle
COPY src ./src

# Build the application
RUN ./gradlew build --no-daemon

# Final image
FROM openjdk:22-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder image
COPY --from=builder /app/build/libs/*.jar app.jar

# Start the application
CMD ["java", "-jar", "app.jar"]