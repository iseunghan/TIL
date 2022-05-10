`Spring boot + Security` 를 사용하고 있는데 에러 코드 마다 페이지를 보여주고 싶은데 어떻게 해야하는지 방법을 생각해보다가 두가지 방법이 생각났습니다.

## 1. EntryPoint, Handler 사용
`authenticationEntryPoint`, `accessDeniedHandler`에서 사용자에게 해당 에러 페이지로 리다이렉션을 시키는 방법입니다.

#### Security Config
```java
/* Security Config */
@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        			// ..
                    .exceptionHandling()
                    .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                    .accessDeniedHandler(new MyAccessDeniedHandler())
                    // ..
		;
	}
```

#### AuthenticationEntryPoint
```java
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendRedirect("/unauthorized");
    }
}
```

#### AccessDeniedHandler
```java
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendRedirect("/access-denied");
    }
}
``` 

#### Controller 코드 추가
```java
public class MyErrorController {

    @GetMapping("/unauthorized")
    public String unauthorizedPage() {
        return "/todoList/unauthorized";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "/todoList/access-denied";
    }
}
```

#### 각 에러 페이지 생성 (생략)

해당 에러 내용을 사용자에게 표시해줄 수 있는 페이지를 만들어줍니다.

## 2. Spring Security에서 제공하는 기능 사용


찾아보니까 스프링 시큐리티에서 `ErrorController` 를 구현하면 에러 페이지마다 일일히 API를 생성할 필요 없이 하나의 API에서 처리할 수 있습니다.

```java
@Controller
public class HomeController implements ErrorController {
	
    /* 구현 해야하는 메소드 */
    @Override
    public String error() {
        return "/error";
    }
    
    /* error를 받아서 처리 */
    @GetMapping("/error")
    public String error(HttpServletRequest request) {
        Integer errorCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		/* Error Code 별로 페이지 제공 */
        switch (errorCode){
            case 401:
                return "/unauthorized";
            case 403:
                return "/access-denied";
            case 404:
                return "/not-found";
            default:
                return "/server-error";
        }
    }
}
```

## REFERENCES
* https://www.baeldung.com/spring-boot-custom-error-page
* https://jungguji.github.io/2020/04/15/custom-error-page/
* https://frontendshape.com/post/bootstrap-5-404-page-examples