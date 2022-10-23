FROM openjdk:11
ARG jarsrc=build/libs/vaadin-petstore-0.0.1-SNAPSHOT.jar
ADD $jarsrc app.jar
ENTRYPOINT ["java", "-jar","app.jar"]
EXPOSE 9998
