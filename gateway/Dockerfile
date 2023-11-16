FROM bellsoft/liberica-openjdk-alpine-musl:17
WORKDIR /opt
COPY build/libs/*.jar gateway.jar
CMD ["java","-jar","gateway.jar"]