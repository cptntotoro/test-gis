FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar gateway.jar
ENTRYPOINT ["java","-jar","/gateway.jar"]

EXPOSE 6060