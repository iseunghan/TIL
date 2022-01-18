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