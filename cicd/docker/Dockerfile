FROM arm64v8/amazoncorretto:17-alpine3.18

WORKDIR /opt/apps

COPY ./target/vertx-lotto-api-1.0.0-SNAPSHOT.jar /opt/apps/vertxApp.jar

EXPOSE 8080

CMD ["java","-jar","/opt/apps/vertxApp.jar"]