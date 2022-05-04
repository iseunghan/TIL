## System.getProperties로 파라미터 받기

기존에는 이런식으로 넘겨주면,
```bash
$ java -jar XXX.jar arg1 arg2 arg3

```

자바에서는 이렇게 받으면 된다.
```java
public class Main {
	public static void main(String[] args) {
		String arg1 = args[0];
		String arg2 = args[1];
		String arg3 = args[2];

	}
}
```

문제는 도커 이미지를 만들다가 ENV 변수로 실행시키게끔 하려는데 자꾸 ENV가 안담겨나가서 다른 방법을 찾아보다가 발견했다.

바로.. `System.getProperty("key")`

파라미터 넘겨줄 때는, -D옵션으로 `-D{key}={value}` 이런식으로 넘겨주면 된다.
```bash
$ java -Darg1=arg1 -Darg2=arg2 -Darg3=arg3 -jar XXX.jar

```

`<key, value>`로 넘어오기 때문에 key값으로 받아오면 된다.
```java
public class Main {
	public static void main(String[] args) {
		String arg1 = System.getProperty("arg1");
		String arg2 = System.getProperty("arg2");
		String arg3 = System.getProperty("arg3");

	}
}

```

## REFERENCES
[Proper usage of Java -D command-line parameters - Stack Overflow](https://stackoverflow.com/questions/5045608/proper-usage-of-java-d-command-line-parameters)