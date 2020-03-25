FROM openjdk:13

COPY src pinguin-app/src
COPY gradle pinguin-app/gradle

COPY gradlew build.gradle settings.gradle pinguin-app/

WORKDIR pinguin-app

EXPOSE 8080
RUN ./gradlew test build

ENTRYPOINT ./gradlew bootRun