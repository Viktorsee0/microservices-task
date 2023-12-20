FROM bellsoft/liberica-openjdk-alpine-musl:17
WORKDIR /opt
COPY build/libs/*.jar user-service.jar
CMD ["java","-jar","user-service.jar"]