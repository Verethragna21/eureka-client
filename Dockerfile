
FROM gradle:8-jdk21-alpine AS build
WORKDIR /app
COPY gradle-wrapper.* build.gradle settings.gradle /app/
COPY src /app/src/
RUN gradle build --no-daemon

FROM openjdk:21-jdk-slim as runtime
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
