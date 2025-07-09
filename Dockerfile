# Сборка
FROM maven:3.9.10-eclipse-temurin-21 AS build
COPY pom.xml /build/
WORKDIR /build/
RUN mvn dependency:go-offline
COPY src /build/src/
RUN mvn clean package -DskipTests


# Запуск
FROM openjdk:21
COPY --from=build /build/target/*.war app.war
COPY src/main/resources/application-cloudconfig.yaml /config/application.yml

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.war"]