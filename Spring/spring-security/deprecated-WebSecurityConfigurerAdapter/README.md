# (Deprecated) WebSecurityConfigurerAdapter에 대응하기

**Spring Boot 2.7 (Spring 5.7.0-M2)** 부터 WebSecurityConfigurerAdapter는 Deprecated가 되었다.

[Spring Security without the WebSecurityConfigurerAdapter](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)

스프링 공식 블로그에서 제공하는 방식을 빠르게 정리해보겠다.  

## 변경된 시큐리티 설정

### HttpSecurity 설정

> 기존 설정 방식
> 

```java
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());
    }

}
```

> 스프링은 SecurityFilterChain 빈을 등록하는 방법을 소개한다.
> 

```java
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());
        return http.build();
    }

}
```

### WebSecurity 설정

> 기존 방식

```java
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/*.html", "/*.css", "/*.js");
    }

}
```

> WebSecurityCustomizer를 빈으로 등록하는 방식
> 

```java
@Configuration
public class SecurityConfiguration {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
												.ignoring()
												.antMatchers("/*.html", "/*.css", "/*.js");
    }

}
```

- 스프링 공식문서에서는 해당 방법을 권장하지 않는다.

ignoring을 한다면 애초에 시큐리티를 타지 않기 때문에 csrf 공격이나 다른 공격에 취약할 수 밖에 없다.

그러므로 스프링 시큐리티는 해당 방법을 권장하지 않는다.

권장하는 방법은 아래와 같다.

```json
(HttpSecurity http) {
    http
        .requestMatchers(...atCommonResource()).permitAll()
    ...
}
```

그냥 정적 리소스의 Path를 permitAll()을 해주면 되는 것이다. 정적 리소스에 대한 인증은 거치지 않으면서도 시큐리티의 기본적인 보안 기능을 적용시키는게 권장사항이다.

- 디버그도 함께 찍고 싶다면 아래 코드를 추가한다.
    
```java
@Bean
public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.`debug`(securityDebug)
        .ignoring()
        .antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
}
```


### AuthenticationManager 빈 등록

> 기존 방식
> 

```java
@Bean
public AuthenticationManager authenticationManagerBean() throws Exception {
  return super.authenticationManagerBean();
}
```

> 변경된 방식
> 

```java
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	return authenticationConfiguration.getAuthenticationManager();
}
```


# REFERENCES

- [https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html](https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html)
- [https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-requests.html](https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-requests.html)
- [https://www.baeldung.com/spring-deprecated-websecurityconfigureradapter](https://www.baeldung.com/spring-deprecated-websecurityconfigureradapter)
- [https://www.appsdeveloperblog.com/migrating-from-deprecated-websecurityconfigureradapter/](https://www.appsdeveloperblog.com/migrating-from-deprecated-websecurityconfigureradapter/)
- [https://honeywater97.tistory.com/264](https://honeywater97.tistory.com/264)