# UsernamePasswordAuthenticationFilter 동작 방식에 대해서

SpringSecurity Filterchain 에는 여러 종류에 필터가 존재하지만, 이번 시간에는 로그인 인증을 처리하는 UsernamePasswordAuthenticationFilter에 대해서 알아보겠습니다.

\


### Login 인증 로직 flow



![이미지 출처: https://catsbi.oopy.io/c0a4f395-24b2-44e5-8eeb-275d19e2a536](https://blog.kakaocdn.net/dn/brMNeJ/btrjD3d0bUH/SKFU4t4VIxxd4vkLcEJjS1/img.png)

\


### POST "/login" 으로 요청을 보내면?

Postman으로 body에 username, password를 넣고 "/login"으로 요청을 보내면,

![](https://blog.kakaocdn.net/dn/cEvcVB/btrjBxNlft7/HY7PWmnqlTNOymROBN2P2K/img.png)

* UsernamePasswordAuthenticationFilter가 요청을 낚아채고 username, password를 검증합니다.
* 그 과정에서 우리가 오버라이딩한 UsernamePasswordAuthenticationFilter.attemptAuthentication 메소드가 호출이 됩니다.

\


UsernamePasswordAuthenticationFilter.attempAuthentication

![](https://blog.kakaocdn.net/dn/KuHGK/btrjEV0U91M/LMUKe9EUMOmKzMhRXyvH5K/img.png)

* POST로 요청이 왔는지 먼저 확인합니다.
* request에서 username과 password를 추출하여 UsernamePasswordAuthenticationToken을 생성합니다.
* 해당 토큰을 ProviderManager(AuthenticationManager 구현체)에게 인증 책임을 위임합니다.

\


ProviderManager.authenticate

![](https://blog.kakaocdn.net/dn/NClGs/btrjFAhQjrw/gH0tNzY0gKkyQq4Mpv0sKk/img.png)

* ProviderManager는 자신이 가진 provider들을 순회하면서 인증을 처리할 수 있는 provider를 찾아 인증 책임을 위임합니다.

\


DaoAuthenticationProvider.retrieveUser

![](https://blog.kakaocdn.net/dn/IAlaY/btrjJzvAFor/CPA7njPpNp1KK1OhfDHEvk/img.png)

* 그 중 DaoAuthenticationProvider가 빈으로 등록된 UserDetailsService의 loadUserByUsername()을 호출합니다.
* 이후에 비밀번호 검증을 하여, 성공적으로 검증이 되었으면 Authenticate 객체를 생성하여 리턴시켜줍니다.

\


### UsernamePasswordAuthenticationFilter는 기본적으로 doFilter를 호출 하지 않습니다.

하지만 인증 성공/실패 여부에 따라 successfulAuthentication, unsuccessfulAuthentication 메소드가 호출이 됩니다.

AbstractAuthenticationProcessingFilter.successfulAuthentication

![](https://blog.kakaocdn.net/dn/8Pibh/btrjEUOvTn0/g8PZda9DpkerbOSuc4VeS1/img.png)

* 인증이 성공적으로 완료되었을 경우에 SecurityContextHolder에 저장하고, successHandler를 호출합니다.
* successHandler에서는 지정된 경로로 redirect를 시키도록 되어있습니다.
* 그렇기 때문에 POST /login 으로 들어온 요청은 다음 필터를 호출하지 않습니다.

그렇기 때문에 UsernamePasswordAuthenticationFilter를 상속하여 사용하는 것입니다.

\


### REFERENCE

[https://tech.junhabaek.net/spring-security-usernamepasswordauthenticationfilter%EC%9D%98-%EB%8D%94-%EA%B9%8A%EC%9D%80-%EC%9D%B4%ED%95%B4-8b5927dbc037#e7c0](https://tech.junhabaek.net/spring-security-usernamepasswordauthenticationfilter%EC%9D%98-%EB%8D%94-%EA%B9%8A%EC%9D%80-%EC%9D%B4%ED%95%B4-8b5927dbc037#e7c0)[\
](https://tech.junhabaek.net/spring-security-usernamepasswordauthenticationfilter%EC%9D%98-%EB%8D%94-%EA%B9%8A%EC%9D%80-%EC%9D%B4%ED%95%B4-8b5927dbc037#e7c0)

\
