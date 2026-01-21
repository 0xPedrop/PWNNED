FROM maven:3.9.6-amazoncorretto-21

WORKDIR /app

COPY pom.xml .
COPY src ./src

ENTRYPOINT ["mvn", "spring-boot:run"]