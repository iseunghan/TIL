Spring boot 프로젝트를 `docker image`로 만들어 보겠습니다.

# 간단한 spring boot 프로젝트 생성

### HomeController
```java

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/hello")
    public String hello(@Value("${test.customName}") String name) {
        return "hello, " + name + "!";
    }
}

```

### application.yml
```yaml
test:
    customName: ${CUSTOM_NAME}
```

* `"/hello"`로 요청이 들어오면 `application.yml`에 미리 정의한 `test.customName`을 함께 출력하는 메소드입니다.

# jar 파일 생성하기
> docker image를 생성하기 위해서는 먼저 jar 파일이 필요합니다.
해당 [🔗링크](https://github.com/iseunghan/TIL/tree/master/Spring/jar-%ED%8C%8C%EC%9D%BC-%EC%83%9D%EC%84%B1)를 참조하시길 바랍니다.


# Dockerfile 생성
`docker image`를 만들기 위해서는 `Dockerfile`이라는 파일에 어떤 base 이미지를 사용해서 만들지.. 등등을 명시 해줘야합니다.

* 프로젝트 최 상단 디렉토리에 `Dockerfile` 생성

```dockerfile
#!/bin/bash

# base 이미지 설정
FROM openjdk:8-jre-alpine

# jar 파일 위치를 변수로 설정
ARG JAR_FILE=target/*.jar

# 환경변수 설정
ENV CUSTOM_NAME default

# jar 파일을 컨테이너 내부에 복사
COPY ${JAR_FILE} test-app.jar

# 외부 호스트 8080 포트로 노출
EXPOSE 8080

# 실행 명령어
CMD ["java", "-Dtest.customName=${CUSTOM_NAME}", "-jar", "test-app.jar"]
```

# docker image 생성
위에서 만든 `Dockerfile`을 기반으로 `docker image`를 생성해보도록 하겠습니다.


* `docker build`
```bash
$ docker build -t {name}/{image} .
```

만약 `Docker hub`에 이미지를 업로드 하고 싶다면 name을 docker id와 동일하게 설정해야 합니다.

### docker scan 에러
![](https://images.velog.io/images/iseunghan/post/37b20401-9f7b-4523-8f48-c11afce5d66f/image.png)

도커 이미지를 생성하는 도중 `docker scan`을 실행하라는 메세지가 떴습니다.

해결 방법을 찾아보니 alpine 이미지를 scan해주면 해결된다고 합니다.

* `docker scan`
```bash
$ docker scan alpine
```

![](https://images.velog.io/images/iseunghan/post/ff56fa34-0255-4289-a2ea-74cd71cdcacd/image.png)

* 다시 `docker build`
```bash
$ docker build -t {name}/{image} .
```


* 이미지 조회
```bash
$ docker images
```

정상적으로 잘 생성된 것을 확인할 수 있습니다!


![](https://images.velog.io/images/iseunghan/post/94bcafa3-4e2d-4235-b183-a490923ad6b6/image.png)

# 실행해보기
```bash
$ docker run --it -e CUSTOM_NAME=iseunghan --name test-docker -p 8080:8080 {name}/{image}
```

![](https://images.velog.io/images/iseunghan/post/af64531b-1484-454d-86ab-2f287d723ae3/image.png)

* Docker 실행 시 넘겨줬던 환경변수가 잘 전달되어 출력된 것을 확인할 수 있습니다.
