FROM amazoncorretto:21-alpine AS build
WORKDIR /app
RUN apk add --no-cache maven
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM alpine:latest
RUN apk update && \
    apk add --no-cache openjdk21-jdk curl aws-cli bash
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
