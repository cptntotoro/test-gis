FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar config.jar
ENTRYPOINT ["java","-jar","/config.jar"]

RUN apk update && apk add curl curl-dev bash
#RUN apk add curl curl-dev bash
HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8888/actuator/health || exit 1

EXPOSE 8888