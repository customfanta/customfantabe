FROM maven:3.8.6-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests


FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/customfanta-be-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
