# Spring

스프링을 공부한 내용을 정리합니다 :)

## 목차

| 목차 | 링크 |
| :-: | :-: |
| Spring Framework란? | [🔗](#스프링-프레임워크란-무엇인가요) |
| Spring Boot란? | [🔗](#스프링-부트란-무엇인가요) |
| Spring 빈과 IoC 컨테이너에 대해서 | [🔗](./스프링-빈-IoC컨테이너) |
| Spring MVC에 대해서 | [🔗](./spring-mvc란) |
| `application.yml` 값 받아오기 | [🔗](./application.yml에서-값-받아오기) |
| Spring 프로젝트 jar 파일 생성 | [🔗](./jar-파일-생성) |
| Jdbc-JdbcTemplate-JPA | [🔗](./Jdbc-JdbcTemplate.md) |
| Spring H2 인메모리 DB 설정 | [🔗](./h2-setting.md) |

## 스프링 프레임워크란 무엇인가요?

* 자바 엔터프라이즈 개발을 편하게 해주는 경량급 오픈소스 애플리케이션 프레임워크입니다.
* 스프링 프레임워크가 하부구조를 알아서 처리해주기 때문에 개발자는 Application 개발에만 집중할 수 있습니다.
* 스프링은 좋은 객체 지향 애플리케이션을 개발할 수 있게 도와주는 프레임워크입니다.\
  가장 중요한 특징은 `의존성 주입(DI : Dependency Injection)`이 있습니다.

***

## 스프링 부트란 무엇인가요?

* 스프링 부트는 스프링에서 제공하는 많은 라이브러리들을 자동으로 설정해줍니다.
* 내장 톰캣 서버로 인해 간단한 배포 서버 구축 가능합니다.
* 3rd party(외부) 라이브러리의 버전을 자동으로 구성해주기 때문에 신경쓸 필요가 없습니다.

핵심: `spring-boot-starter`
