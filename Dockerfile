FROM maven:3.9.6-amazoncorretto-21 as build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM alpine:latest
RUN apk update && \
    apk add --no-cache openjdk21-jdk curl aws-cli bash
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
