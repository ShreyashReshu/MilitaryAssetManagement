# Multi-stage build for Render
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy project files
COPY pom.xml ./
COPY src src

# Run maven build (using the installed maven, safer than wrapper)
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the built jar (using wildcard to be safe)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]