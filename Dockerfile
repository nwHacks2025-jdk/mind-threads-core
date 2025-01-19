FROM openjdk:17-jdk

CMD ["./gradlew", "clean", "build"]

VOLUME /tmp

EXPOSE 8080

COPY ./build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]