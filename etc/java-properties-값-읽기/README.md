# java 프로젝트에서 properties 읽어들이기

* application.properties

```properties
people.name="iseunghan"
people.age=26
people.city="Daejeon"
```

* Main.java

```java
public class Main {

    static String name;
    static String age;
    static String city;

    private static void read_properties() {
        // resource 위치
        String resource = "META-INF/application.properties";
        Properties properties = new Properties();

        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(resource);

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("file not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 미리 선언해둔 String 변수들
        name = properties.getProperty("people.name");
        age = properties.getProperty("people.age");
        city = properties.getProperty("people.city");
    }

    public static void main(String[] args) {
        read_properties();

        System.out.println("name = " + name);
        System.out.println("age = " + age);
        System.out.println("city = " + city);
    }
}
```

* `properties.getProperty()` 는 항상 String으로 리턴합니다.

age를 int로 선언했더니 발생한 오류

![1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcfSOZt%2FbtrroesqspV%2FLkxZebZHNLMrxB0dPpIEk1%2Fimg.png)

* 실행 결과

정상적으로 `properties`값을 가져오는 것을 확인할 수 있습니다.

![2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FWUCrb%2FbtrroW5YLLq%2FyMJGiv8Mrov5xn2RybZARk%2Fimg.png)