# Brug en officiel Java runtime som base image
FROM openjdk:17-jdk-alpine

# Tilføj jar-filen fra din applikation (Gradle bygger til build/libs/)
ARG JAR_FILE=build/libs/intellij-0.0.1-SNAPSHOT.jar

# Kopier jar-filen til Docker containeren
COPY ${JAR_FILE} app.jar

# Kopier konfigurationsscriptet til Docker containeren
COPY set-env-vars.sh /usr/local/bin/set-env-vars.sh
RUN chmod +x /usr/local/bin/set-env-vars.sh

# Definer standardkommandoen til at køre scriptet, som derefter starter din Spring Boot applikation
ENTRYPOINT ["/usr/local/bin/set-env-vars.sh", "java", "-jar", "/app.jar"]