# Brug en officiel Java runtime som base image
FROM openjdk:17-jdk-alpine

# Tilføj jar-filen fra din applikation (Gradle bygger til build/libs/)
ARG JAR_FILE=build/libs/intellij-0.0.1-SNAPSHOT.jar

# Kopier jar-filen til Docker containeren
COPY ${JAR_FILE} app.jar

# Definer standardkommandoen til at køre scriptet, som derefter starter din Spring Boot applikation
ENTRYPOINT ["java", "-jar", "/app.jar"]