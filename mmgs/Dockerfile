FROM openjdk:8-jre-alpine
COPY build/libs/mmgs-1.0-SNAPSHOT.jar /services/mmgs.jar
EXPOSE 8090
CMD ["java", "-jar", "/services/mmgs.jar"]