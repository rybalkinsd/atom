FROM openjdk:8-jre-alpine
MAINTAINER Alex Pomosov
COPY build/libs/backend-1.0-SNAPSHOT.jar /usr/game/game-server.jar
#EXPOSE will not open port toouter world, just inside cloud
EXPOSE 8090 #WebSocket connection port
CMD ["java", "-jar", "/usr/game/game-server.jar"]