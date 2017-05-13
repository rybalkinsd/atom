FROM openjdk:8-jre-alpine
MAINTAINER Alex Pomosov
COPY build/libs/lecture11-1.0-SNAPSHOT.jar /services/billing.jar
#EXPOSE will not open port to outer world, just inside cloud
#Billing service port
EXPOSE 8080
CMD ["java", "-jar", "/services/billing.jar"]