FROM adoptopenjdk/openjdk16
VOLUME /tmp
COPY target/bookapi-demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]