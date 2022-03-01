# intellij에서 Java 프로젝트 jar파일 만드는 법
자바 프로젝트를 intellij에서 jar 파일 만드는 법

* 간단한 프로젝트 생성을 했습니다. `hello world!`를 출력하는 메인 클래스입니다.
![img](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Ftlubo%2Fbtrrr5tJH4i%2FFKeknC7jZ27TB72qchEsf1%2Fimg.png)


* `cmd` + `:`로 프로젝트 구조 설정으로 들어갑니다. (Mac OS 단축키)


* `Artifacts` -> `+` -> `JAR` -> `From modules ...` 클릭!
![2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FFatnv%2FbtrrqZAw7rd%2FzJTKnceD821reuPsPKoe61%2Fimg.png)


* `Main Class`를 지정해줍니다. 이때 패키지명까지 다 적어줘야 합니다! 이후에 `Apply` -> `OK`를 눌러줍니다.
![3](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FTFJI5%2FbtrroFJ5ynB%2FEk4dkkc4MQM0EjwHjJW2UK%2Fimg.png)


* 이제 intellij 상단바에서 `build` -> `artifacts`를 클릭해줍니다.
![4](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FyKzCj%2Fbtrrod1jGxR%2FBeB5ZsHE0qIKkBA9YYCIz1%2Fimg.png)


* `build`를 클릭해줍니다.
![5](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FuxLIA%2Fbtrrr5mXnIE%2FTdZa8GH72CAvfFUpoiXzGK%2Fimg.png)


* `out/artifacts/[project명]`에 jar 파일이 생성된 것을 확인할 수 있습니다.
![6](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcocPgy%2FbtrrqnuUB6u%2FQJV846M2pOPpqyD6CxKtjk%2Fimg.png)


* jar 파일 실행하기
![7](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbW6tF1%2FbtrrpVk6QZV%2FigyFbWKnueq5ztoFY9ZyA1%2Fimg.png)


올바르게 실행된 것을 확인할 수 있습니다!