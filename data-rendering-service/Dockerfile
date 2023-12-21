FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar data-rendering-service.jar
ENTRYPOINT ["java","-jar","/data-rendering-service.jar"]

EXPOSE 5050