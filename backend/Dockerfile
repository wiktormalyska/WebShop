FROM gradle:8.7.0-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM eclipse-temurin:21
WORKDIR .
EXPOSE 5000
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/backend.jar /app/
ENTRYPOINT ["java", "-jar", "/app/backend.jar"]
