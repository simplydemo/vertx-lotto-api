# vertx-lotto-api

vertx-lotto 백앤드 애플리케이션을 vertx 프레임워크로 빌드하고 github-actions 워크플로우를 사용하여 eks 클러스터에 배포 합니다.

## Git

```
git clone https://github.com/simplydemo/vertx-lotto-api.git
```

## Build Artifact

```
cd vertx-lotto-api
mvn clean package -DskipTests=true
```

## Build Image

```
docker build -t "vertx-lotto-api:local" -f ./cicd/docker/Dockerfile.corretto . 
```

## Run Container
```
docker run --rm --name=vertx-lotto-api -p 8080:8080 vertx-lotto-api:local
```

## Test

```
curl -X GET http://localhost:8080/lotto/v1/take
```

## Appendix
[Introduction to Vert.x](https://www.baeldung.com/vertx)
