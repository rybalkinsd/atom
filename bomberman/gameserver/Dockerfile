FROM openjdk:8-jre-alpine
MAINTAINER Dream Team
COPY build/libs/gameserver-1.0-SNAPSHOT.jar /services/gameserver.jar

EXPOSE 8090
CMD ["java", "-jar", "/services/gameserver.jar"]