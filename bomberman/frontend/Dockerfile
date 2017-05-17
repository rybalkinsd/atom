FROM jetty:9
MAINTAINER EGOR
COPY build/libs/bomberman/frontend-1.0-SNAPSHOT.war /var/lib/jetty/webapps/frontend.war
EXPOSE 8080
CMD ["java", "-jar", "/usr/local/jetty/start.jar"]