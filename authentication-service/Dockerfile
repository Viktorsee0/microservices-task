FROM bellsoft/liberica-openjdk-alpine-musl:17
WORKDIR /opt
COPY build/libs/*.jar auth.jar
CMD ["java","-jar","auth.jar"]