FROM amazoncorretto:17-alpine

RUN mkdir /apps

WORKDIR /apps

COPY ./target/vertx-lotto-api.jar /apps/vertxApp.jar

EXPOSE 8080

CMD ["java", "-jar", "/apps/vertxApp.jar"]
