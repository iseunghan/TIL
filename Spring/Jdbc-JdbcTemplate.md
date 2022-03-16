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
public class JdbcAccountRepository implements AccountRepository{

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
    private AccountRepository jdbcAccountRepository ;

    @GetMapping("/account")
    public List<Account> findAll() {
        return jdbcAccountRepository.findAll();
    }

    @GetMapping("/account/{id}")
    public Account findById(@PathVariable Long id) {
        return jdbcAccountRepository.findById(id);
    }

    @PostMapping("/account")
    public Long save(@RequestBody Account account) {
        return jdbcAccountRepository.save(account);
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


## 연관관계 매핑일 때, 데이터를 저장하는 2가지 방법 (JdbcTemplate vs SimpleInsert)
* `JdbcTemplate.update`
* 편리한 `SimpleInsert`

### JdbcTemplate 사용
* Account와 Article은 서로 연관관계가 있다고 가정한다.
    * 하나의 Account는 여러 개의 Article을 가진다.

연관관계가 있는 두 테이블을 저장하기 위해 `PreparedStatementCreator`, `KeyHolder`를 사용한다.

```java
public class Account {
	...
    List<Article> articles;
    ...
}

public class Article {
	Long id;
    String title;
    Date createdAt;
    ...
}
```

```java
@Repository
public class JdbcAccountRepository implements AccountRepository {
	
    @Autowired
    private JdbcTemplate jdbc;
    
    public Long save(Account account) {
        Long accountId = saveAccountInfo(account);
        account.setId(accountId);

        for (Article article : account.getArticles()) {
            saveArticleToAccount(article, accountId); 
        }
        return account.getId();
    }

    public Long saveAccountInfo(Account account) {
        PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
                "insert into Account (name, age) values (?, ?)",
                Types.VARCHAR, Types.INTEGER
        ).newPreparedStatementCreator(
                Arrays.asList(
                        account.getUsername(),
                        account.getAge()
                )
        );

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    private void saveArticleToAccount(Article article, Long accountId) {
        jdbcTemplate.update(
            "insert into Account_Article (account, article) values (?, ?)",
                accountId, article.getId());
    }
}
```
* 코드가 복잡하다.
* 생성된 `Account ID`값을 얻기 위해서는 `KeyHolder`가 필요한데, 이 `KeyHolder`를 사용하기 위해서는 꼭 `PreparedStatementCreator` 객체가 필요하다.


### SimpleJdbcInsert 사용
`JdbcTemplate` 래퍼 클래스이다.

**SimpleAccountRepository.java**
```java
@Repository
public class SimpleAccountRepository implements AccountRepository{

    private final ObjectMapper objectMapper;
    private final SimpleJdbcInsert accountInserter;
    private final SimpleJdbcInsert accountArticleInserter;

    public SimpleAccountRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.accountInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Account")
                .usingGeneratedKeyColumns("id");

        this.accountArticleInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Account_Articles");

        this.objectMapper = objectMapper;
    }
    
    @Override
    public Long save(Account account) {
        Long accountId = saveAccountInfo(account);
        account.setId(accountId);

        for (Article article : account.getArticles()) {
            saveArticleToAccount(article, accountId);
        }
        return null;
    }

    private void saveArticleToAccount(Article article, Long accountId) {
        // key 값은 테이블의 열 이름과 동일
        HashMap<String, Object> values = new HashMap<>();
        values.put("article", article.getId());
        values.put("account", accountId);

        accountArticleInserter.execute(values);
    }

    private Long saveAccountInfo(Account account) {
        Map<String, Object> values = objectMapper.convertValue(account, Map.class);

        return (Long) accountInserter.executeAndReturnKey(values);
    }
    
    ...
}
```

* `SimpleJdbcInsert`는 더욱 편리한 기능을 제공한다.
* 이전에는 key를 돌려받기 위해 `KeyHolder` + `PrepareStatementCreator`를 사용했다면, 이제는 `SimpleJdbcInsert`클래스의 `executeAndReturnkey`를 이용해 키를 돌려받을 수 있다.

---

# Spring Data JPA 사용
`Spring Jdbc`보다 훨씬 편리한 기능들 제공


* `dependency` 추가

```
<dependency>
	<groupId>org.springframework.data</groupId>
	<artifactId>spring-data-jpa</artifactId>
</dependency>
```

* 간단한 CRUD 구현
* `Repository`는 `interface`를 상속한다.

[JpaRepository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html) 인터페이스를 상속받는다.
```java
@Repository
public interface JpaAccountRepository extends JpaRepository<Account, Long> {
}
```

이제 `Account`를 `Jpa Entity`로 등록하기 위해서 다음 3가지가 필요하다.
* Jpa Entity인 것을 알려주기 위해 클래스 레벨에 `@Entity` 어노테이션을 붙여준다.
* 기본 생성자 (매개변수가 없는 생성자)
* id 필드에 `@Id` 어노테이션을 붙여준다.

```java
@Data
@RequiredArgsConstructor
@Builder
@Entity
public class Account {
    @Id
    private Long id;
    private String username;
    private int age;
    @OneToMany(mappedBy = "account")
    private List<Article> articles;
}
```

`Account`와 일대다 매핑이 되어있는 `Article`도 `Entity`로 등록해준다.
* Account(1) 입장에서는 Article(N)은 `다(N)`이기 때문에, `@ManyToOne`을 붙여준다.
* `@ManyToOne`에는 항상 JoinColumn으로 `1(일)`에 해당하는 Entity를 지정해줘야한다.
* `@JoinColumn` (생략가능) : `EntityName + _ + ID`, 생략하면 JPA가 알아서 구문 분석을해서 매핑해준다.
```java
@Data
@Builder
@NoArgsConstructor
@Entity
public class Article {
    @Id
    private Long id;
    private String title;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")	// 생략가능
    private Account account;
}
```