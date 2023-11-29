FROM bellsoft/liberica-openjdk-alpine-musl:17
WORKDIR /opt
COPY build/libs/*.jar todolist.jar
CMD ["java","-jar","todolist.jar"]