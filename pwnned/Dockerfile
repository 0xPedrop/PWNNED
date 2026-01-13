FROM maven:3.9.6-amazoncorretto-21

WORKDIR /app

# Copia o pom.xml e as fontes
COPY pom.xml .
COPY src ./src

# Compila e roda o projeto
ENTRYPOINT ["mvn", "spring-boot:run"]