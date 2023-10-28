FROM arm64v8/amazoncorretto:17-alpine3.18

RUN mkdir /apps

WORKDIR /apps

COPY ./target/vertx-lotto-api-1.0.0-SNAPSHOT.jar /apps/vertxApp.jar

EXPOSE 8080

CMD ["java", "-jar", "/apps/vertxApp.jar"]
