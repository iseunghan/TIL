## Goal
* 스프링 JdbcTemplate 사용해보기
* SimpleJdbcInsert를 사용해서 데이터 추가해보기
* 스프링 데이터를 사용해서 JPA 선언하고 사용해보기

---

## 순수 JDBC
Jdbc란, `Java DataBase Connectivity`, 데이터베이스를 연결하기 위한 API이다.

Jdbc를 이용해 `select` 쿼리 작성해보면 아래와 같다.

```java
@Override
public Account findById(String id) {
	Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    
    String sql = "select id, name from Account where id = ?";
    
    try {
    	conn = datasource.getConnection();
        st = conn.prepareStatement(sql);
        st.setString(1, id);	// ?에는 id가 들어간다.
        
        ...
    } catch (SQLException e) {
    	...
	} finally {
    	if (rs != null) {
        	try {
            	rs.close();
            } catch (SQLException e) {}
        }
        if (st != null) { ... }	  // st.close
        if (conn != null) { ... } // conn.close()
    }
    return null;
}
```

JDBC를 사용하면 DB 연결부터 마지막에 연결해제까지 일일히 다 해줘야한다. 
이런 불편함을 해소하기 위해 Spring에서 제공하는 `JdbcTemplate` 클래스가 있다.

## Spring에서 제공하는 JdbcTemplate

* dependency 추가
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```

* **JdbcTemplate**
    * `query("sql", RowMapper)`
    * `queryForObject("sql", RowMapper, Object... args)`
    * `update("sql", Object... args)`

`Object... args`는 `select * from Account where id = ?` 와 같이 ?에 해당하는 값을 인자로 넘겨주는 것이다.

```java
@Repository
public class AccountJdbcRepository implements AccountRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
    public Long save(Account account) {
    	jdbcTemplate.update("insert into Account values(?, ?)",
        					account.getId(),
                            account.getUsername());
		return account.getId();
    }

    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query("select * from Account", this::rowToAccount);
    }

    @Override
    public Account findById(Long id) {
        return jdbcTemplate.queryForObject("select * from Account where id = ?",
                                            this::rowToAccount,
                                            id);
    }

    @Override
    public Account findByUsername(String username) {
        return jdbcTemplate.queryForObject("select * from Account where username = ?",
                                            this::rowToAccount,
                                            username);
    }

    private Account rowToAccount(ResultSet rs, int rowNum) throws SQLException {
        return new Account(rs.getLong("id"),
                    rs.getString("username"));
    }
}
```

아래 처럼 익명함수로 만들어도 된다.

```java
jdbc.queryForObject(sql, new RowMapper<Account>() {
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Account(rs.getString("id"),
                            rs.getString("username"));

	}
}
```

아까 순수 JDBC 사용한 [`select`](#순수-jdbc) 쿼리랑 비교해보자.

엄청나게 편리해졌다.

순수 `Jdbc`에서는 DB 커넥션을 생성하고 했다면, 이제 `JdbcTemplate`을 사용할 때에는 `DataSource`라는 정보를 설정해줘야 한다.

아래 두가지 방법을 사용할 수 있다. (더 많을수도 있다.)
* `DataSource`를 직접 `Bean`으로 등록
* `application.yml`에 설정

### DataSource를 Bean으로 등록

@Configuration이 붙은 Class 아래에 작성해준다.

```java
@Bean
public BasicDataSource source() {
	BasicDataSource source = new BasicDataSource();
	source.setUrl("jdbc:h2:tcp://localhost/~/databaseName");
    source.setDriverClassName("org.h2.Driver");
    source.setUsername("sa");
    source.setPassword("");

	return source;
}

@Bean
public JdbcTemplate dataSource(BasicDataSource source) {
	return new JdbcTemplate(source);
}
```

빈으로 주입받아서 사용하면 된다.
```java
@Autowired
private JdbcTemplate jdbc;
```


### application.yml 등록
* Spring boot에서 제공해주는 방법이다. 편리한 방법이다.


```yaml
# DataSource 설정
spring:
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:tcp://localhost/~/prac_jdbc
    username: sa
    password:

# SQL 생성을 위한 설정
  sql:
    init:
      schema-locations: classpath:scheme.sql
      mode: always
```

## 테스트 해보기
* 간단한 `Controller` 생성

```java
// AccountController.java

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountJdbcRepository ;

    @GetMapping("/account")
    public List<Account> findAll() {
        return accountJdbcRepository.findAll();
    }

    @GetMapping("/account/{id}")
    public Account findById(@PathVariable Long id) {
        return accountJdbcRepository.findById(id);
    }

    @PostMapping("/account")
    public Long save(@RequestBody Account account) {
        return accountJdbcRepository.save(account);
    }
}
```

#### 실행결과
> insert 문은 생략.

* GET `/account`

![](https://images.velog.io/images/iseunghan/post/7f8d53ff-2cea-4b62-828b-8f65fedaa50a/image.png)

* GET `/account/{id}`

![](https://images.velog.io/images/iseunghan/post/080d8a17-3350-45ac-bad2-6caf0282f26e/image.png)

* POST `/account`

![](https://images.velog.io/images/iseunghan/post/1fe9ea9c-d65b-4f2b-97de-ea8f9562cc5d/image.png)


## 데이터를 저장하는 2가지 방법 (JdbcTemplate vs SimpleInsert)
* `JdbcTemplate.update`
* `SimpleInsert`

### JdbcTemplate 사용
* Account와 Article은 서로 연관관계가 있다고 가정한다.

```java
@Repository
public class JdbcArticleRepository implements ArticleRepository {
	
    @Autowired
    private JdbcTemplate jdbc;
    
    @Override
    public Article save(Article article) {
    	Long id = saveArticleInfo(article);
    }
    
    private Long saveArticleInfo(Article article) {
    	article.setCreatedAt(new Date());
        PreparedStatementCreator psc = 
        	new PreparedStatementCreatorFactory(
            )
    }
}
```
