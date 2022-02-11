## Servlet (서블릿) 이란 무엇인가요?
> 자바를 이용하여 동적인 웹을 만들기 위해 필요한 기술입니다.

서블릿은 **클라이언트의 요청**을 처리하고 **처리 결과**를 클라이언트에게 **전송**하는 기술, 자바를 사용하여 웹페이지를 동적으로 생성하는 서버측 자바 프로그램(`JVM` 필요)..이라고 이해하면 될 것 같습니다.

원래 웹 페이지는 정적인 페이지로만 응답을 할 수 있었습니다.
하지만 이 서블릿을 이용하면 정적인 페이지를 응답할 수 있습니다.

### 서블릿 컨테이너(Servlet Container) 동작 방식
![](https://images.velog.io/images/iseunghan/post/879c07be-7e3f-4b47-bb02-021631c0c11f/image.png)

* `서블릿 컨테이너`가 이 서블릿의 생명주기를 관리합니다.
* 서블릿 컨테이너는 웹 서버와 소켓을 만들어 통신을 하여 클라이언트의 요청과 응답을 처리할 수 있습니다.

1. 사용자(클라이언트) 요청이 들어오면 서블릿 컨테이너가 받아서 `HTTPServletRequest`, `HTTPServletResponse` 객체를 생성
2. 서블릿 컨테이너가 해당 요청과 **매핑된 서블릿**을 찾게 됩니다.
3. 해당 서블릿의 존재유무를 확인하고 해당 서블릿에게 **요청을 위임**합니다.
	* 서블릿이 있다면 그대로 사용
    * 없다면, `init()` 호출해서 새로 생성
4. 해당 서블릿이 `service()` 메소드를 호출한 후, 클라이언트의 `GET`, `POST` 여부에 따라 `doGet()`, `doPost()`를 호출합니다.
5. `doXXX()` 메소드는 동적 페이지를 생선한 후 `HTTPServletResponse` 객체에 응답을 보냅니다.
6. 응답이 끝나면 두 객체를 소멸시키고 끝! ( `destroy()` 호출)

#### 생명 주기

```java
서블릿 생성 -> init() 메소드 호출 -> service() -> doXXX() 호출 -> destroy() -> 서블릿 종료
```

그렇다면 매핑된 서블릿을 어떻게 찾을까요? 바로 `web.xml(설정 파일)`을 참고하여 매핑할 **servlet**을 확인합니다.


### 서블릿 설정 파일 (web.xml)

```xml
<servlet>
	<servlet-name>HelloServlet</servlet-name>
	<servlet-class>servlet.HelloServlet</servlet-class>
</servlet>

<servlet-mapping>
	<servlet-name>HelloServlet</servlet-name>
	<url-pattern>/hello</url-pattern>
</servlet-mapping>
```

1. `"/hello"` 라는 요청이 들어오면 -> `HelloServlet`이라는 서블릿으로 처리하겠다.
2. `HelloServlet`은 `servlet 패키지 아래 HelloServlet 클래스파일`로 정의되어있다.
