FROM openjdk:8-jre-alpine
COPY build/libs/web_hackaton-1.0-SNAPSHOT.jar /services/chat.jar
EXPOSE 8080
CMD ["java", "-jar", "/services/chat.jar"]