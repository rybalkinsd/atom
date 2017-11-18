FROM openjdk:8-jre-alpine
MAINTAINER Alex Pomosov
COPY build/libs/backend-1.0-SNAPSHOT.jar /usr/game/game-server.jar
EXPOSE 8090
CMD ["java", "-jar", "/usr/game/game-server.jar"]