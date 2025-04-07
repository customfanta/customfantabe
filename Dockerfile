# FROM maven:3.8.7-eclipse-temurin-19 AS build
#
# WORKDIR /app
#
# COPY pom.xml .
# COPY src ./src
#
# RUN mvn package -DskipTests
#
#
# FROM openjdk:17-jdk-slim
#
# WORKDIR /app
#
# COPY --from=build /app/target/customfanta-be-1.0.0.jar app.jar
#
# EXPOSE 8080
#
# ENTRYPOINT ["java", "-jar", "app.jar"]



FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/customfanta-be-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
