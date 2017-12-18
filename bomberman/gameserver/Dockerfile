FROM openjdk:8-jdk-alpine
MAINTAINER ze best
COPY build/libs/gameserver-1.0-SNAPSHOT.jar services/gameserver.jar
WORKDIR services/gameserver.jar
CMD ["java", "-jar", "services/gameserver.jar"]
EXPOSE 8090