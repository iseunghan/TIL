# Spring H2 inmemory DB 사용하는 법
Spring boot에서 h2 인메모리 DB 사용하는 법을 알아보겠다.

### 의존성 추가
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

```

### application.yml 설정
```yaml
spring:
  h2:
    console:
      enabled: true
      path: /h2-console #(default 설정안해도 자동적용)
  datasource:
    url: jdbc:h2:mem:todoItem

```

이제 `http://localhost:8080/h2-console` 로 접속하게 되면, h2 콘솔을 볼 수 있다.

* saved setting: `Generic H2 (Embedded)`
* JDBC URL: `jdbc:h2:mem:todoItem`

당연히 인메모리 이기 때문에 프로젝트가 종료되면, 내부 데이터는 다 날아가니 주의하자.

