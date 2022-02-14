# WEB server vs WAS(Web Application Server) 차이점

### `Web server`
* 정적 컨텐츠(HTML, image ,,)을 처리한다.
* ex) `apache`, `nginx`

### `WAS (Web Application Server)`
* WAS =  `Web server + Web Container`
	
    * `Web Container`는 `JSP`, `Servlet`을 실행시킬 수 있는 소프트웨어입니다.
* 동적 컨텐츠를 처리 및 웹 응용프로그램 서비스 처리
* 즉, `web server`가 처리할 수 있는 형태로 가공하여 제공할 수 있는 웹 어플리케이션
* ex) `Tomcat` ,,

> 둘을 따로 분리하는 이유는? 
> -> 할일을 분리시켜 서버 부하 방지
