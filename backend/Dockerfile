FROM maven:3.9.6-amazoncorretto-21

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

EXPOSE 8080

ENTRYPOINT ["mvn", "spring-boot:run"]