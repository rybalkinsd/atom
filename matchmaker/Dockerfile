FROM jetty:9.4.5-alpine
MAINTAINER Alex Pomosov
COPY build/libs/matchmaker-1.0-SNAPSHOT.jar /services/matchmaker.jar
#EXPOSE will not open port to outer world, just inside cloud
#Billing service port
EXPOSE 8080
CMD ["java", "-jar", "/services/matchmaker.jar"]