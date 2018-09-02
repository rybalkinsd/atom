FROM openjdk:8-jdk-alpine
COPY build/libs/matchmaker-1.0-SNAPSHOT.jar /services/matchmaker.jar
EXPOSE 8080
CMD ["java", "-jar", "/services/matchmaker.jar"]