FROM openjdk:8-jdk-alpine
COPY build/libs/GameServer-1.0-SNAPSHOT.jar /services/GameServer.jar
EXPOSE 8090
CMD ["java", "-jar", "/services/GameServer.jar"]