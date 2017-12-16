FROM openjdk:8-jre-alpine
ENV JAVA_OPTIONS "-Xmx300m"
COPY build/libs/matchmaker-1.0-SNAPSHOT.jar /services/garbager.jar
EXPOSE 8080
CMD ["java", "-jar", "/services/garbager.jar"]