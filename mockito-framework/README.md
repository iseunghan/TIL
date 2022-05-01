# Mockito로 테스트를 작성해보자

> 프로젝트 환경: Spring-boot, Junit5, Mockito
>

## Mockito

> Mockito란? 테스트 프레임워크로 테스트할 때 모든 빈을 일일히 주입시키지 않고, Mock 객체(가짜 객체)를 주입시켜 행위를 테스트할 수 있고, 아직 작성되지 않은 코드들(의존성 객체) 또는 구현하기 어려운 의존성 객체들을 Mocking하여 테스트 할 수 있습니다.
>

## 의존성 (dependency)

> spring boot를 사용중이라면, ### spring-boot-starter-test 라이브러리에 이미 JUnit5와 Mockito가 포함되어있으므로 의존성을 추가해주지 않아도 됩니다.
>

[Spring.io - 41.Testing](https://docs.spring.io/spring-boot/docs/1.5.7.RELEASE/reference/html/boot-features-testing.html#boot-features-test-scope-dependencies)

## Mock 테스트 순서

1. mock 객체 생성 및 주입
2. Stubbing : Mock의 행동을 구체적으로 명세합니다. 사전에 정의된 결과를 반환합니다.
3. Verify : Mock의 행동을 검증합니다.

### 테스트용 BookService

```java
@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public Book findAll() {
		List<Book> bookList = bookRepository.findAll();

		if(bookList.size() == 0) {
			throw new BookEmptyException();
		}
		return bookList;
	}

	public Book findById(Long id) {
		Book book = bookRepository.findById(id);

		if(book == null) {
			throw new BookNotFoundException();
		}
		return book;
	}

	..
}

```

정말 간단한 모든 Book을 조회하고, 하나의 book을 가져오는 기능이 구현되어있습니다.

## Mockito를 사용하기 위한 설정

JUnit 5는 MockitoExtension을 추가해주면 됩니다.

```java
@Extendwith(MockitoExtension.class)
public class BookServiceTest {

}

```

## Mock 객체 생성

Mock 객체를 생성하고 필드에 참조하고 있는 객체들을 Mock으로 주입시켜줘야 합니다.

이때 사용하는 것이 바로 `@Mock`, `@InjectMocks`입니다.

```java
@Extendwith(MockitoExtension.class)
public class BookServiceTest {
	// 해당 객체를 Mock 처리한다. (mock(BookRepository.class)와 같은 표현입니다.)
	@Mock
	private BookRepository bookRepository;

	// 해당 어노테이션이 붙은 객체에 @Mock이 붙은 객체들을 주입시켜줍니다. (필드에 있는 객체들과 동일하다면)
	@InjectMocks
	private BookService bookService;

}

```

### `@Spy` 와 `@Mock` 의 차이?

`@Mock`객체는 가짜 객체를 생성하기 때문에 우리가 테스트하려는 기능에 대해서 Stub을 해줘야 합니다. 만약 Stub을 해주지 않으면 Mockito 기본전략인 `Answers.RETURNS_DEFAULT`에 의해 리턴타입에 맞는 기본 메소드가 실행됩니다.

[Mockito - mockito-core 4.5.1 javadoc](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#RETURNS_DEFAULTS)

반면에 `@Spy`객체를 사용하면 실제 객체를 주입해줍니다. 그렇기 때문에 우리가 따로 Stub을 해주지 않으면 기존에 있는 기능들을 사용하게 됩니다.

---

## Stubbing

BookService의 `findAll()` 메소드의 행동을 검증하기 위해 Stubbing할 것입니다.

findAll() 메소드 내부에 보면 `bookRepository.findAll()` 메소드를 호출합니다.

우리는 해당 메소드의 행동을 정의해줘야 합니다.

bookRepository의 어떤 메소드를 어떤 매개변수와 함께 호출 했고, 어떤 값을 리턴해주는지를 정의해주는 것입니다.

### findAll() stubbing

```java
List<Book> bookList = new ArrayList<>();
bookList.add(new Book(1L, "title"));
bookList.add(new Book(2L, "title2"));

// stubbing
when(bookRepository.findAll()).thenReturn(bookList);

```

### findById() stubbing

```java
Book book = new Book(1L, "title");

// stubbing
when(bookRepository.findById(anyLong()).thenReturn(book);

```

---

## Verify

### 횟수에 대해 검증

```java

// 호출되지 않음
verify(bookRepository, never()).findAll();
verify(bookRepository, times(0)).findAll();	// never()과 동일

// 1번 호출 됨
verify(bookRepository).findAll();		// times(1)과 동일

// 적어도 1번이상 호출 됨
verify(bookRepository, atLeastOnce()).findAll();
verify(bookRepository, atLeast(1)).findAll();	// 동일

// 최대 N번이하 호출 됨
verify(bookRepository, atMost(N)).findAll();

```

### 순서에 대해 검증

예를 들어, Book을 추가할 때 BookRepository에 해당 id로 이미 추가된 책이 있는지 `1.조회` 해보고 중복되지 않았으면 `2.추가`를 합니다.

해당 순서를 검증한다고 했을 때 검증 코드는 아래와 같습니다.

```java
// 편의상 메소드 이름은 조회, 추가로 설정
InOrder inOrder = inOrder(bookRepository);

inOrder.verify(bookRepository).조회(anyLong());
inOrder.verify(bookRepository).추가(any(Book.class));

```

### Exception 검증

bookService.findById()가 예외를 던지려면 어떻게 해야할까요?

bookRepository.findById()이 `null` 을 리턴하게 하면 됩니다.

그리고, `findById`가 예외를 잘 던지는지 확인하면 됩니다.

```java
when(bookRepository.findById(anyLong())).thenReturn(null);

assertThrows(NotFoundException.class, () -> bookService.findById(1L));

```

---

## REFERENCES
[nesoy.github.io](https://nesoy.github.io/articles/2018-09/Mockito)  
[cobbybb.tistory.com](https://cobbybb.tistory.com/16)  
[velog.io/@ausg](https://velog.io/@ausg/Mockito-Test-Framework-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B)