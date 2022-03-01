# Spring jar 파일 생성


## jar 파일 생성
프로젝트 root 디렉토리에서 아래 명령어 실행

* maven
```bash
$ ./mvnw package
# skip Test
$ ./mvnw package -DskipTests
```

* gradle
```bash
$ ./gradlew bootJar
```

* 결과
![](https://images.velog.io/images/iseunghan/post/cd7ab00c-3827-4a0a-a753-b4da2b12b3ae/image.png)

* jar 파일
![](https://images.velog.io/images/iseunghan/post/a758e6dd-e32a-44ac-9114-fe3ec381ca72/image.png)


## 실행해보기

```bash
$ cd target
$ java -jar practice-docker-image-0.0.1-SNAPSHOT.jar
```

* 실행결과
![](https://images.velog.io/images/iseunghan/post/6620f318-f820-43cf-87ff-831ebb5484f8/image.png)
