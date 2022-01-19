# Dockerfile 명령어 정리

## Dockerfile이란 
Docker 이미지가 어떤 단계를 거쳐 빌드(build)가 되는 과정을 텍스트 파일로 표현한 것입니다. Docker 이미지에는 [Layer](https://eqfwcev123.github.io/2020/01/30/%EB%8F%84%EC%BB%A4/docker-image-layer/))가 있는데 이 명령어 라인 하나 하나가 실행되면서 `image Layer`를 이뤄서 결국에는 하나의 이미지가 되는 것입니다. (*추후 포스팅 예정)
*Dockerfile은 항상 최상위 디렉토리에 있어야 합니다.

## Dockerfile 명령문 형식 Key Value
```dockerfile
# 주석을 사용할 수 있습니다.
명령어(INSTRUCTION) 인자(arguments)
```
명령문은 명령어로 시작하고 그 뒤에는 여러 개의 인자를 줄 수 있습니다.
명령어는 대문자, 인자는 소문자로 구분지어 사용하는 것이 관례라고 합니다.

## FROM <image>:<tag>
```dockerfile
# tag 생략 시 default = latest
FROM <image>:<tag>
```
Docker 이미지에는 base 이미지가 존재해야 합니다. base 이미지에 또 다른 이미지를 올리고 여러 개의 Image Layer를 쌓아가며 만들 수 있습니다.

base 이미지를 선언해주는 것이기 때문에 보통 맨 위에 선언해줍니다. 일반적으로 공식 이미지를 많이 선택하고 아래와 같이 사용합니다.

* openjdk 1.8 (alpine)을 base 이미지로 사용
```dockerfile
FROM openjdk:8-alpine
```

* ubuntu 최신 버전을 base 이미지로 사용
```dockerfile
FROM ubuntu:latest
```

## WORKDIR
`WORKDIR` 명령어는 쉘(shell)에서 `cd` 명령어 처럼 컨테이너 상에서 작업 디렉토리 설정을 위해서 사용되는 명령어입니다. `WORKDIR`로 작업 디렉토리를 옮기면 해당 디렉토리 기준으로 앞으로 사용하는 명령어가 실행됩니다.

```dockerfile
WORKDIR /app/v0.0.1/bin
```

## RUN
```dockerfile
RUN ["<command>", "<parameter>", "<parameter>", ..]
RUN <전체 커맨드>
```
`RUN` 명령어는 쉘(shell)에서 커맨드를 실행하는 명령어입니다. 이미지 빌드 과정에서 필요한 커맨드를 실행하기 위해 사용됩니다. 보통 이미지 안에 특정 소프트웨어를 설치하기 위해 사용됩니다.

* npm 패키지 설치
```dockerfile
RUN npm install
```

## ENTRYPOINT
```dockerfile
ENTRYPOINT ["<command>", "<parameter>", "<parameter">, ..]
ENTRYPOINT <전체 커맨드>
```
`ENTRYPOINT` 명령어는 이미지를 컨테이너로 띄울 때 항상 실행되야 하는 커맨드를 지정할 때 사용됩니다. 

## CMD
```dockerfile
CMD ["<command>", "<parameter>", "<parameter>", ..]
CMD ["<parameter>", "<parameter>"]
CMD <전체 커맨드>
```

`CMD` 명령어는 해당 이미지를 컨테이너로 띄울 때나 default로 실행할 커맨드, `ENTRYPOINT` 명령어로 지정된 커맨드에 default로 넘길 파라미터를 지정할 때 사용됩니다.

`CMD` 명령어는 `ENTRYPOINT`와 함께 사용되기도 하는데, 이때 보통 `ENTRYPOINT`는 커맨드로 `CMD`는 default 파라미터로 지정해주면 유연하게 실행할 수 있게됩니다.

* 기본적으로 `node test1.js`를 실행하기 위한 Dockerfile
```dockerfile
# test image
ENTRYPOINT ["node"]
CMD ["test1.js"]
```

* `node test1.js` 실행
```dockerfile
docker run test
```

* `node test2.js` 실행
```dockerfile
docker run ter test2.js
```

#### `CMD`  vs  `RUN` 명령어
`RUN` 명령어는 이미지 빌드 시 항상 실행되며, 한 dockerfile 안에 여러 개의 `RUN` 명령어를 선언할 수 있습니다.
하지만, `CMD`는 딱 한번 실행을 할 수 있으며, 이 마저도 `docker run` 인자로 인해 덮여씌워지게 됩니다.

* 한번만 실행되는 `CMD` 명령문 (마지막 `CMD` 명령문 실행)
`last` 출력!
```dockerfile
CMD ["echo", "first"]
CMD ["echo", "last"]
```

* 또 다른 예시로, `hello world!`를 출력하는 커맨드
```dockerfile
# test2 image
CMD ["echo", "hello world!"]
```

* `hello world!` 출력
```dockerfile
docker run test2
```

* `bye world!` 출력
```dockerfile
docker run test2 echo bye world!
```

## Expose 명령어
```dockerfile
EXPOSE <port>
EXPOSE <port>/<protocol>
```

`EXPOSE` 명령어는 호스트가 컨테이너로 접속하기 위한 포트를 지정해줄 수 있습니다. 포트만 적으면 기본적으로 TCP가 적용이 됩니다.

> 주의할 점! `EXPOSE` 명령어는 호스트와 연결만 할 뿐 외부로 노출되지 않습니다. 외부나 호스트는 지정된 포트로 접근이 불가능합니다. 외부로 노출시키기 위해선 `docker run -p 8080:8080` 처럼 포트포워딩을 해줘야만 접근이 가능합니다. 

* 8080/TCP 연결
```dockerfile
EXPOSE 8080
```

* 8082/UDP 연결
```dockerfile
EXPOSE 8082/UDP
```

## COPY / ADD 명령어
```dockerfile
COPY <복사할 파일>... <복사한 파일을 이미지에 복사할 곳>
COPY ["<파일>", ... "<복사할 곳>"]
```

`COPY` 명령어는 호스트 컴퓨터에 있는 디렉토리나 파일을 Docker 이미지의 파일시스템으로 복사하기 위해서 사용됩니다.  절대경로와 상대경로 둘다 사용할 수 있습니다. 상대경로를 사용할 때에는 현재 `WORKDIR`가 어디인지 체크를 해야합니다.

* `hello.java` 파일을 복사
```dockerfile
COPY hello.java hello.java
```

* 이미지를 빌드한 디렉토리의 모든 파일을 컨테이너의 `app/` 디렉토리로 복사
```dockerfile
WORKDIR app/
COPY . .
```

`ADD` 명령문은 `COPY`명령어와 다르게 일반 파일 뿐만 아니라 압축 파일이나 네트워크 상의 파일도 사용할 수 있습니다. 이렇게 특수한 파일을 다루는게 아니라면 `COPY`를 사용하는게 좋습니다.

## ENV / ARG 명령어
### ENV
```dockerfile
ENV <KEY> <VALUE>
ENV <KEY>=<VALUE>
```

`ENV` 명령어는 환경 변수를 설정할 수 있습니다. `ENV` 명령어는 `ARG` 명령어와 다르게 컨테이너 내부에서도 접근이 가능합니다.

* `SERVER_PORT`를 기본값 8080으로 설정
```dockerfile
ENV SERVER_PORT 8080
```

* 환경변수 `SERVER_PORT`를 9090으로 설정
```sh
$ docker run -e SERVER_PORT=9090 ....
```

### ARG
```dockerfile
ARG <KEY>
ARG <KEY>=<default>
```

`ARG` 명령어는 `ENV` 명렁어와 다르게 이미지 빌드시에만 사용할 수 있습니다. 

* 빌드 시 `ARG` 설정
```dockerfile
$ docker build --build-arg SERVER_PORT=1234 ...
```

`CMD`와 함께 사용은 아래와 같이 할 수 있습니다.
```dockerfile
ARG SERVER_PORT=8080

CMD java -jar -Dserver.port=${SERVER_PORT} hello.jar
```

# REFERENCE
[시작하세요! 도커/쿠버네티스 - YES24](http://www.yes24.com/Product/Goods/84927385)
[Dockerfile에서 자주 쓰이는 명령어 | Engineering Blog by Dale Seo](https://www.daleseo.com/dockerfile/)
[도커 (Docker) - 환경변수(ENV) 전달하기 : 네이버 블로그](https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=complusblog&logNo=220975099502)
[docker 헷갈리는 Dockerfile 명령어 · Issue #90 · heowc/programming-study · GitHub](https://github.com/heowc/programming-study/issues/90)
[Docker Dockerfile 환경변수 설정(ENV)](https://kimjingo.tistory.com/78)