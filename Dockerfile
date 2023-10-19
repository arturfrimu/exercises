# Utilizarea unei imagini de bază
FROM openjdk:21-slim

# Variabile de mediu
ARG JAR_FILE=target/*.jar

# Copierea fișierului .jar în container
COPY ${JAR_FILE} app.jar

# Deschiderea portului 8081
EXPOSE 8081

# Comanda de rulare a aplicației
ENTRYPOINT ["java", "-jar", "/app.jar"]