FROM openjdk:8-jre-alpine
COPY build/libs/gameservice-1.0-SNAPSHOT.jar /services/gs.jar
EXPOSE 8080
CMD ["java", "-jar", "/services/gs.jar"]