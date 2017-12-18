FROM openjdk:8-jre-alpine
COPY build/libs/mm-1.0-SNAPSHOT.jar /services/mm.jar
EXPOSE 8080
CMD ["java", "-jar", "/services/mm.jar"]