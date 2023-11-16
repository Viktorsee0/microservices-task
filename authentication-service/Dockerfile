FROM bellsoft/liberica-openjdk-alpine-musl:17
WORKDIR /opt
COPY build/libs/*.jar authentification.jar
CMD ["java","-jar","authentification.jar"]