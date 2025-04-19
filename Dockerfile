# Étape 1 : Builder l'application sans tests
FROM maven:3.9.5-eclipse-temurin-21-alpine AS builder
WORKDIR /build
COPY . .
RUN mvn clean install -DskipTests

# Étape 2 : Image légère pour l'exécution
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copier uniquement le JAR compilé depuis le builder
COPY --from=builder /build/target/*.jar app.jar

# Exposer le port de l'application
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
