# ---- Stage 1: Build (JDK completo, base Debian)
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

COPY . .
RUN chmod +x mvnw && ./mvnw -B -DskipTests package

# ---- Stage 2: Runtime (Alpine)
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

# Instalar tzdata en Alpine con apk (no apt-get)
RUN apk add --no-cache tzdata

# (Opcional) Fijar zona horaria en la imagen
ENV TZ=Europe/Madrid

COPY --from=builder /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","-Xmx256M","/app/app.jar"]
