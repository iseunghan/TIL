# Jdbc에 대해서

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
public Item findById(String id) {
	Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    
    String sql = "select id, name from Item where id = ?";
    
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

* `queryForObject(SQL문, RowMapper, 인자)`

```java
private JdbcTemplate jdbc;

@Override
public Item findById(String id) {
    String sql = "select id, name from Item where id = ?";
    
	return jdbc.queryForObject(sql, this::mapRowToItem, id);
    // 두번째 인자에서 this(현재 객체의)::mapRowToItem(이라는 메소드의 참조를 넘기고 있다.)
}

private Item mapRowToItem(ResultSet rs, int rowNum) throws SQLException {
	return new Item(rs.getString("id"),
    				rs.getString("name"));
}
```

아래 처럼 익명함수로 만들어도 된다.

```java
jdbc.queryForObject(sql, new RowMapper<Item>() {
	public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Item(rs.getString("id"),
                            rs.getString("name"));

	}
}
```

아까 순수 JDBC 사용한 [`select`](#순수-jdbc) 쿼리랑 비교해보자.

엄청나게 편리해졌다.

순수 `Jdbc`에서는 DB 커넥션을 생성하고 했다면, 이제 `JdbcTemplate`을 사용할 때에는 `DataSource`라는 정보를 설정해줘야 한다.

아래 두가지 방법을 사용할 수 있다. (더 많을수도 있다.)
* `DataSource`를 직접 `Bean`으로 등록
* `application.yml`에 설정

#### DataSource를 Bean으로 등록

@Configuration이 붙은 Class 아래에 작성해준다.

```java
@Bean
public BasicDataSource source() {
	BasicDataSource source = new BasicDataSource();
	source.setUrl("jdbc:mysql//localhost:3306/databaseName");
    source.setDriverClassName("com.mysql.jdbc.Driver");
    source.setUsername("root");
    source.setPassword("1234");

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


#### application.yml 등록

```yaml
spring:
	datasource:
    	url: jdbc:mysql//localhost:3306/databaseName
        driver-class-name: com.mysql.jdbc.Driver
        username: root
        password: 1234
```


