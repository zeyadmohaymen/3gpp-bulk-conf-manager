FROM openjdk:21-jdk
COPY build/libs/configs-manager-0.1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]