# CORS란 무엇이고, Spring-boot에서 해결하기 위한 방법

태그: 웹 개발

## CORS (Cross-Origin Resource Sharing) 이란?

<aside>
💡 A라는 도메인에서 제공되는 FE에서 → B라는 도메인으로 제공되는 BE에 HTTP 요청을 했을 경우,
브라우저는 이를 서로 다른 도메인에서 리소스를 공유하는 것이라 판단하고 그것을 방지하기 위해 호출을 금지하는 것이다.

</aside>

### Preflight Request

브라우저에서 실제 HTTP 요청을 보내기 전 브라우저 스스로 이 요청을 보내는 것이 안전한지 예비 요청을 하게 되는데 이게 바로 Preflight Request라고 부른다.

이 Preflight Request는 OPTION 메소드를 이용해 요청하는데 서버에서 보내준 응답 헤더에 `Access-Controller-*` 헤더들이 잘 구성되어있는지 확인합니다.

![Untitled](../../img/cors%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B4%EA%B3%A0-springboot%EC%97%90%EC%84%9C-%ED%95%B4%EA%B2%B0%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95-1.png)

1. GET, POST, HEAD 요청인지 파악합니다.
2. Custom-Header가 존재하는지 파악합니다.
3. Content-Type이 표준 타입인지 확인합니다.
4. 만약 `Simple Request`라고 판단이 되었다면 → 실제 요청을 전송합니다.
5. 그렇지 않다면, `Prefilght Request`를 전송합니다. 이때 Custom-Header와 Content-Type이 함께 전송됩니다.
6. 서버에서 응답해준 Access-Control-* 헤더들과 비교하여 브라우저가 이 요청이 안전하다고 판단되면 실제 요청을 전송, 그렇지 않다면 CORS 에러를 발생시킵니다.

### Simple Request는 Preflight가 발생하지 않는다.

모든 요청이 Preflight를 트리거 하지 않습니다. Simple Request를 제외한 요청에만 preflight가 동작하는데요, 다음 조건들을 만족하는 요청을 Simple Request라고 합니다.

- GET, HEAD, POST 메소드를 사용하는 요청
- User-Agent가 자동으로 설정한 헤더 외 수정으로 설정한 헤더가 존재하는 경우
    - Accept
    - Accept-Language
    - Content-Language
    - Content-Type (다음 값들만 허용, 그 외 값들은 Simple Request가 아니다.)
        - `application/x-www-form-urlencoded`
        - `multipart/form-data`
        - `text/plain`

Simple Request 외 요청들은 모두 `OPTION` 를 통해 다른 도메인의 리소스로 HTTP 요청을 보내 실제 요청이 안전한지 확인합니다. 이를 `Preflight`라고 하는 것입니다.

### Preflight + 실제 요청에 대한 시나리오

![Untitled](../../img/cors%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B4%EA%B3%A0-springboot%EC%97%90%EC%84%9C-%ED%95%B4%EA%B2%B0%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95-2.png)

- Preflight 요청에만 `Access-Control-Request-*` 헤더가 포함되고 실제 POST 요청에는 포함되지 않는 것을 볼 수 있습니다.
- `Access-Control-Request-Method: POST` → 실제 요청은 POST 메소드로 전송할 것이라고 알려줍니다.
- `Access-Control-Request-Headers: X-PINGOTHER, Content-type` → 실제 요청에 커스텀 헤더를 포함해 전송할 것임을 알려줍니다.
- 이제 서버는 이러한 상황에서 요청을 수락할지 결정할 수 있습니다.

### Access-Control-* Header 종류 및 설명

- ***Access-Control-Allow-Origin*:** 리소스에 접근할 수 있는 origin을 정의합니다. `“*”`는 모든 origin을 허용합니다.
- ***Access-Control-Allow-Methods*:** 원본 간 요청에 허용되는 HTTP 메소드를 의미합니다.
- ***Access-Control-Allow-Headers*:** Cross-Origin Requests에 대해 허용된 요청 헤더를 나타냅니다.
- ***Access-Control-Expose-Headers***: 브라우저가 접근할 수 있는 헤더를 서버의 화이트리스트에 추가합니다.
- ***Access-Control-Max-Age*:** preflight request 요청 결과를 캐시할 수 있는 시간을 나타냅니다.
- ***Access-Control-Allow-Credentials***: credentials 플래그가 true일 때, 요청에 대한 응답을 표시할 수 있는지 나타냅니다. 이 헤더가 없으면 브라우저에서 응답을 무시하고 웹 컨텐츠로 반환하지 않습니다.

현재는 서비스 단계가 아닌 개발 단계이기 때문에 서버에서 CORS를 전체 허용해주도록 설정하도록 하겠습니다.

## 개발 환경

FE(React) → Nginx(Proxy pass) → BE(Spring Boot)

- Nginx: 192.168.0.101:8080 (proxy → 8081)
- BE: 192.168.0.101:8081

React 에서 8081로 요청 시 아래와 같은 오류가 발생한다.

```java
Access to XMLHttpRequest at 'http://192.168.0.101:8080' from origin 'http://192.168.0.102:3000' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

# Spring-boot에서 CORS 허용

4가지 방법이 있다.

1. WebMvcConfigurer 상속 받아 CORS 설정 오버라이드(Security 사용하지 않을 때)
2. 커스텀 CorsFilter를 생성
3. CorsConfigurationSource Bean 생성
4. ~~Controller 클래스 레벨에 @CrossOrigin 어노테이션 추가 (다루지 않을 예정)~~

### 1. WebMvcConfigurer 상속 받아 CORS 설정 오버라이드

```java
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "*")
                .maxAge(3000);
    }

}
```

Security를 사용하지 않을 때 설정으로 적합한 것 같습니다.

### 2. CorsFilter 빈으로 등록

```java
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter {

private final Logger log = LoggerFactory.getLogger(SimpleCORSFilter.class);

public SimpleCORSFilter() {
    log.info("SimpleCORSFilter init");
}

@Override
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
    chain.doFilter(req, res);
}

@Override
public void init(FilterConfig filterConfig) {
}

@Override
public void destroy() {
}

}
```

추가로 Security 설정 파일에서 아래와 같이 추가해줘야 합니다.

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors()
			...
		return http.build();
}
```

위 코드 하나로 어떻게 우리의 필터가 추가가 되는지는 아래에서 자세히 다루겠습니다.

### 3. CorsConfigurationSource Bean 생성

Spring Security [공식 문서](https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html)에서 제공하는 방법입니다.

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors()
			...
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
```

---

## 위에서 설정한 코드들이 어떤식으로 동작하는지 이해하기

### httpSecurity.cors()

![Untitled](../../img/cors%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B4%EA%B3%A0-springboot%EC%97%90%EC%84%9C-%ED%95%B4%EA%B2%B0%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95-3.png)

`httpSecurity.cors()`를 적용하게 되면 다음과 같이 동작하게 된다.

1. `corsFilter`라는 이름으로 빈이 등록이 되어있으면 해당 CorsFilter를 사용합니다.
2. 그렇지 않고, `corsConfigurationSource`가 빈으로 등록되어 있으면 해당 설정을 적용합니다.
3. 그렇지 않고, Spring MVC가 클래스 패스에 있는 경우 HandlerMappingIntrospector가 사용됩니다.

### CorsConfigurationSource를 Bean으로 등록

CorsConfigurationSource를 빈으로 등록하게 되면 cors() 메소드에서 설명한 것처럼 2번째 조건에 만족하게 되어 설정을 적용시킬 수 있습니다.

우리가 등록한 CorsConfigurationSource가 잘 등록되어있는지 확인해보려면 CorsFilter에 디버깅 지점으로 설정하고 살펴보도록 합니다.

```java
package org.springframework.web.filter;

public class CorsFilter extends OncePerRequestFilter {

	private final CorsConfigurationSource configSource;

	private CorsProcessor processor = new DefaultCorsProcessor();

	/**
	 * Constructor accepting a {@link CorsConfigurationSource} used by the filter
	 * to find the {@link CorsConfiguration} to use for each incoming request.
	 * @see UrlBasedCorsConfigurationSource
	 */
	public CorsFilter(CorsConfigurationSource configSource) {
		Assert.notNull(configSource, "CorsConfigurationSource must not be null");
		this.configSource = configSource; // ** 이 지점.
	}

	...
}
```

![Untitled](../../img/cors%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B4%EA%B3%A0-springboot%EC%97%90%EC%84%9C-%ED%95%B4%EA%B2%B0%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95-4.png)

우리가 시큐리티에서 설정해줬던 `ConfigurationSource`가 정상적으로 등록된 것을 확인할 수 있습니다.

# Nginx에서 CORS 허용

만약 Nginx를 웹서버로 사용중이라면, Spring-boot에서 CORS를 설정해주어도 CORS 문제가 해결되지 않을 수 있습니다.

아래 설정을 구성하여 CORS를 허용시킬 수 있습니다.

```bash
server {
        listen 8081 default_server;
        listen [::]:8081 default_server;

        root /var/www/html;

        # Add index.php to the list if you are using PHP
        index index.html index.htm index.nginx-debian.html;

        server_name _;

        location / {
                proxy_hide_header Access-Control-Allow-Origin;

								# preFlight 요청 허용
                if ($request_method = 'OPTIONS') {
                        add_header 'Access-Control-Allow-Origin' '*';
                        add_header 'Access-Control-Allow-Methods' 'GET, POST, DELETE, PATCH, OPTIONS';
                        add_header 'Access-Control-Allow-Headers' 'Content-Type, Authorization';
                        add_header 'Access-Control-Max-Age' 86400;
                        return 204;
                }

								# 그 외 요청에 대해서 헤더 노출
                if ($request_method != 'OPTIONS') {
                        add_header 'Access-Control-Allow-Origin' '*' always;
                        add_header 'Content-Type' 'application/json' always;
                }

                proxy_pass http://localhost:8082;
        }
	...
}
```

# REFERENCES

[https://docs.spring.io/spring-security/reference/6.0.0/servlet/integrations/cors.html](https://docs.spring.io/spring-security/reference/6.0.0/servlet/integrations/cors.html)

[https://docs.spring.io/spring-security/reference/servlet/architecture.html#servlet-security-filters](https://docs.spring.io/spring-security/reference/servlet/architecture.html#servlet-security-filters)

[https://www.baeldung.com/spring-security-cors-preflight](https://www.baeldung.com/spring-security-cors-preflight)

[https://greeng00se.tistory.com/119](https://greeng00se.tistory.com/119)

[https://jay-ji.tistory.com/72](https://jay-ji.tistory.com/72)

[https://developer.mozilla.org/ko/docs/Web/HTTP/CORS](https://developer.mozilla.org/ko/docs/Web/HTTP/CORS)

[https://developer.mozilla.org/en-US/docs/Glossary/Preflight_request](https://developer.mozilla.org/en-US/docs/Glossary/Preflight_request)

[https://evan-moon.github.io/2020/05/21/about-cors/](https://evan-moon.github.io/2020/05/21/about-cors/)