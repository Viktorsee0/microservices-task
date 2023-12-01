FROM bellsoft/liberica-openjdk-alpine-musl:17
WORKDIR /opt
COPY build/libs/*.jar rating-service.jar
CMD ["java","-jar","rating-service.jar"]