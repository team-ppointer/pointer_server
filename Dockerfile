FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ARG PROFILE=dev
ENV SPRING_PROFILES_ACTIVE=${PROFILE}

ENTRYPOINT ["java","-jar","/app.jar"]