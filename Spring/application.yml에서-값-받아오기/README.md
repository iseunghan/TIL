# Spring boot에서 application.yml 값을 받아오는 법.

## application.yml
```yaml
custom:
  properties:
  	people:
	    name: iseunghan
	    age: 26
	    city: daejeon

```

## 프로젝트 내부에서 application.yml값 받아오기

* `생성자`
```java
public Test(@Value("${custom.properties.people.name}" String p_name,
	    @Value("${custom.properties.people.age}" String p_age,
            @Value("${custom.properties.people.city}" String p_city,) {
    	name = p_name;
        age = p_age;
        city = p_city;
    }
}
```

* `필드`
```java
@Value("${custom.properties.people.name}")
private String name;
```

## REFERENCE
https://www.baeldung.com/spring-value-annotation
