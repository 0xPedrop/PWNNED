FROM openjdk:21-jdk-slim

WORKDIR /app

COPY pom.xml .
RUN apt-get update && apt-get install -y maven git && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.fork=false"]
