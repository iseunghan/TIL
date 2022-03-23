# LazyInitializationException, LazyLoading

`org.hibernate.LazyInitializationException` 는 `Team-Member` 양방향 연관관계 매핑에서 Team은 조회를 성공했는데, Team안에 있는 `List<Member>`를 조회할 때, 영속성 컨텍스트가 종료되버려서 지연로딩을 할 수 없을 때 발생하는 에러이다.

지연로딩을 하기 위해서는 영속성 컨텍스트가 계속 유지되어야 한다.


## 예외 발생 코드

#### Team.java
```java
public class Team {
	private Long id;
    ..
    
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();
	
    ...
}
```

#### Member.java
```java
public class Member {
    ..
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;
    ..
}
```

둘 다 글로벌 패치는 LAZY 전략을 사용하고 있다.

#### TeamService.java
```java
@Service
public class TeamService {
	
    @Autowired
    private TeamRepository teamRepo;
    
    public List<Team> findAll() {
    	return extractMembers(teamRepo.findAll());
    }
    
    // LAZY Loading
    public List<Team> extractMembers(List<Team> teams) {
    	teams.forEach(t -> t.getMembers().get(0).getName());
        return teams;
    }
}

```
`findAll()` 메소드를 호출하면, Team의 Lazy로 가져온 members를 다 조회시킨다. (N+1발생)
이때, `extractMembers`가 호출되고 `members`에 접근하는 순간 `LazyInitializationException`가 발생한다.


```
org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: me.iseunghan.prac_jdbc.account.Account.articles, could not initialize proxy - no Session
```


### 해결 방법

#### 1) @Transactional
```java
@Service
public class TeamService {
	
    @Autowired
    private TeamRepository teamRepo;
   
   @Transactional(readOnly = true)	// ***
    public List<Team> findAll() {
    	return extractMembers(teamRepo.findAll());	// lazy
    }
    
    public List<Team> extractMembers(List<Team> teams) {
    	teams.forEach(t -> t.getMembers().get(0).getName());
        return teams;
    }
}

```

`findAll()`메소드에 `@Transactional`을 붙여 `extractMembers()`에도 트랜잭션이 유지되도록 설정해주는 것이다.

#### 2) Join Fetch
```java
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
	@Query("select t from Team t join fetch t.members")
    List<Team> findAllJoinFetch();
}
```

#### 3) EntityGraph
```java
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
	@EntityGraph(arrtibutePaths = "members")
    @Query("select t from Team t")
    List<Team> findAllEntityGraph();
}
```

---

### REFERENCES

* https://www.inflearn.com/questions/33949
* http://ojc.asia/bbs/board.php?bo_table=LecSpring&wr_id=500
* [tmdgh0221님 velog](https://velog.io/@tmdgh0221/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BC%80%EC%9D%B4%EC%8A%A4%EC%97%90%EC%84%9C%EC%9D%98-Transactional-%EC%9C%A0%EC%9D%98%EC%A0%90)
* [Lob-dev님 tistory](https://lob-dev.tistory.com/entry/Service%EC%97%90-Transaction-%EC%9D%84-%EC%A0%81%EC%9A%A9%ED%95%9C%EB%8B%A4%EB%A9%B4)

