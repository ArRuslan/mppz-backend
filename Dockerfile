FROM openjdk:21-jdk-bookworm AS build
WORKDIR /build
COPY . .
COPY src/main/resources/docker.properties src/main/resources/application.properties
RUN chmod +x gradlew
RUN ./gradlew bootJar

FROM openjdk:21-bookworm
WORKDIR /app
COPY --from=build /build/build/libs/mppz-backend-0.0.1-SNAPSHOT.jar ./
EXPOSE 8080
CMD ["java", "-jar", "./mppz-backend-0.0.1-SNAPSHOT.jar"]