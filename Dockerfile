FROM openjdk:12-alpine

ARG APP_NAME=target/*.jar
COPY ${APP_NAME} api.jar

ENV MONGO_DB=${MONGO_DB}

EXPOSE 8080

ENTRYPOINT ["java","-Xmx512m","-Dserver.port=${PORT}", "-jar", "/api.jar"]