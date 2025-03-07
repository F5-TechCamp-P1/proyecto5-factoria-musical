# Use an official OpenJDK image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory
WORKDIR /src/main/java/com/f5/factoria_musical

# Copy Maven Wrapper, config, and pom.xml
COPY mvnw mvnw.cmd ./
COPY .mvn .mvn/
COPY pom.xml ./

# Give execution permission
RUN chmod +x mvnw

# Install dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port used by the application
EXPOSE 8000

# Copy the built JAR file
COPY target/factoria-musical-1.0-SNAPSHOT.jar /src/main/java/com/f5/factoria_musical/app

# Set execution permissions for the JAR
RUN chmod +rx /src/main/java/com/f5/factoria_musical/app/factoria-musical-1.0-SNAPSHOT.jar

# Run the application
CMD ["java", "-jar", "/src/main/java/com/f5/factoria_musical/app/factoria-musical-1.0-SNAPSHOT.jar"]
