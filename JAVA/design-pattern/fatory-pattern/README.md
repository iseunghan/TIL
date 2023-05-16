# 팩토리 패턴(팩토리 메소드, 추상)

# 1. 간단한 팩토리
먼저 간단한 팩토리를 만들어보고 이를 점진적으로 패턴을 적용하여 업그레이드 하는 식으로 진행해보겠습니다.

먼저 아래에 노트북을 생성하는 함수가 있습니다. 매개변수 type에 따라서 노트북을 생성하고 준비, 설정, 포장해서 최종적으로 노트북을 반환하게 됩니다.
```java
public NoteBook creaNoteBook(String type) {
    NoteBook noteBook;

    if(type.equals("Macbook")) {
        noteBook =  new MackBook();
    } else if (type.equals("GalaxyBook")) {
        noteBook =  new GalaxyBook();
    } else if (... ...) {
        ...
    }

    noteBook.prepare();
    noteBook.setting();
    noteBook.box();

    return noteBook;
}
```

## 1.1 변하는 것과 변하지 않는 것
먼저 변하는 것과 변하지 않는 것을 알아보겠습니다.

### 1.1.1 변하는 것
노트북을 생성하는 코드는 Type이 많아지면 그만큼 if-else문도 길어질 것입니다.
```java
if(type.equals("Macbook")) {
    noteBook =  new MackBook();
} else if (type.equals("LG Gram")) {
    noteBook =  new LGGram();
} else if (type.equals("GalaxyBook")) {
    noteBook =  new GalaxyBook();
} else if (... ...) {
    ...
}
```

### 1.1.2 변하지 않는 것
노트북 종류에 상관없이 준비하고, 설정하고, 포장하는 것은 변하지 않습니다.
```java
noteBook.prepare();
noteBook.setting();
noteBook.box();
```

## 1.2 둘을 분리해보자

### 1.2.1 NoteBook을 생산하는 간단한 팩토리
노트북을 생성하는 코드를 이젠 Factory라는 객체가 맡기로 했습니다.
```java
public class SimpleNoteBookFactory {
    public NoteBook createNoteBook(String type) {
        NoteBook noteBook;

        if(type.equals("Macbook")) {
            noteBook =  new MackBook();
        } else if (type.equals("LG Gram")) {
            noteBook =  new LGGram();
        } else if (type.equals("GalaxyBook")) {
            noteBook =  new GalaxyBook();
        } else if (... ...) {
            ...
        }
        return noteBook;
    }
}
```

### 1.2.2 노트북을 판매하는 스토어
Store에서는 Factory에서 생성된 노트북을 받아서 준비하고, 설정하고, 포장하는 일을 담당합니다.
```java
public class NoteBookStore {
    SimpleNoteBookFactory noteBookFactory;

    public NoteBookStore(SimpleNoteBookFactory noteBookFactory) {
        this.noteBookFactory = noteBookFactory;
    }

    public NoteBook orderNoteBook(String type) {
        NoteBook noteBook = noteBookFactory.createNoteBook(type);

        noteBook.prepare();
        noteBook.setting();
        noteBook.box();

        return noteBook;
    }
}
```

### 정리

간단한 팩토리(단순 팩토리)는 디자인 패턴이 아닙니다. 팩토리 메소드 또는 추상 팩토리 패턴을 도입하는 중간 단계라고 볼 수 있습니다.

> **장점**
> - 변하는 부분과 변하지 않는 부분을 분리
> - 단일 책임 원칙

> **단점**
> - 하나의 팩토리에서 여러 개의 클래스를 생성
> - 클래스가 늘어난다면 if-else문 길이도 함께 늘어남

# 2. 팩토리 메소드 패턴(Factory Method Pattern)
간단한 팩토리를 좀 더 고도화 해봅시다.

## 2.1 제조사 마다 스토어 생성

각 노트북 제조사마다 스토어를 만들기 위해 하나의 인터페이스를 제공해야 합니다. 그래야 새로운 제조사가 등장해도 유연한 구조를 가질 수 있습니다.

```java
public abstract class NoteBookStore {
	public NoteBook class orderNoteBook(String type) {
		NoteBook noteBook = createNoteBook(type);

        noteBook.prepare();
        noteBook.setting();
        noteBook.box();

		return noteBook;
	}

	public abstract NoteBook createNoteBook(String type);
}
```

이렇게 하게 되면, 각 제조사에서는 noteBook을 만드는 코드를 구현하기만 하면 됩니다. 그렇게 되면 각 제조사의 노트북 생성 코드는 각 제조사스토어에 **캡슐화**가 되게 됩니다.

> **팩토리 메소드 등장**  
> `public abstract NoteBook createNoteBook(String type);`


팩토리 메소드는 객체 생성을 처리하며, 팩토리 메소드를 이용하면 객체를 생성하는 작업을 서브 클래스에 캡슐화시킬 수 있습니다. 이렇게 하면 수퍼클래스에 있는 클라이언트 코드와 서브클래스에 있는 객체 생성 코드를 분리할 수 있습니다.

### 2.1.1 AppleStore 생성

```java
public class MacBookStore extends NoteBookStore {
	public NoteBook createNoteBook(String type) {
	    return new MackBook(...); // 매개변수 생략
	}
}
```

## 2.2 NoteBook 클래스 생성

```java
public abstract class NoteBook {

    String name;
    String cpu;
    String ram;
    String storage;
    String display;
    String os;
    
    public void prepare() {
        System.out.println("Preparing.. " + "name=" + name + ", cpu=" + cpu + ", ram=" + ram + ", storage=" + storage + ", display=" + display + ", os=" + os);
    }

    public void setting() {
        System.out.println("Setting NoteBook...");
    }

    public void box() {
        System.out.println("Boxing NoteBook...");
    }
}
```

### 2.2.1 NoteBook 서브 클래스 예시

MacBook이라면 아래 처럼 만들 수 있습니다.

```java
public class MacBook extends NoteBook {
    public MacBook() {
        this.name = "MacBook";
        this.cpu = "M2";
        this.ram = "16GB";
        this.storage = "SSD 500GB";
        this.display = "Letina Display";
        this.os = "macOS";
    }
}
```

## 2.3 실제 노트북을 주문해보자

```java
public class Main {
	public static void main(String[] agrs) {
		NoteBookStore store = new MacBookStore();
		NoteBook noteBook = store.orderNoteBook();
		System.out.println("My Order NoteBook is " + noteBook.getName());
	}
}
```

### 정리

![Untitled](https://upload.wikimedia.org/wikipedia/commons/4/43/W3sDesign_Factory_Method_Design_Pattern_UML.jpg)  
[출처: https://en.wikipedia.org/wiki/Factory_method_pattern](https://en.wikipedia.org/wiki/Factory_method_pattern)

팩토리 메소드 패턴은 객체 생성을 위한 인터페이스를 제공하고, 클래스의 인스턴스를 만드는 일을 서브클래스에게 위임합니다.

> **장점**
> - 객체 생성을 위한 인터페이스 제공 (개방/폐쇄 원칙)
> - 서브 클래스에게 객체 생성을 위임 (단일 책임 원칙)
> - 생성하는 부분과 사용하는 부분을 분리하여 결합도를 낮춤

> **단점**
> - 패턴을 구현하기 위해 서브 클래스들이 많아져 코드가 복잡해질 수 있음


# 3. 추상 팩토리 패턴(Abstract Fatory Pattern)
각 제조사의 부품들을 생성하는 공장을 만들기 전에, 추상 팩토리를 하나 생성합니다. 각 공장에서 어떻게 부품을 생산하는지에 대한 행동들이 정해져있습니다. 이제 각 제조사 공장들은 AbstractFactory를 구현해서 각 제품에 맞는 부품들을 생산해내면 됩니다.
## 3.1 AbstractFactory

### factory/AbstractFactory

```java
public interface NoteBookSemiconductorFactory {
    Cpu createCpu();
    Ram createRam();
    Storage createStorage();
    Display createDisplay();
    Os createOs();
}
```

### factory/ConcreteFactory1
맥북은 AppleCpu, AppleRam, … MacBook에 맞는 부품들을 생산해냅니다.
```java
public class MacBookSemiconductorFactory implements NoteBookSemiconductorFactory {
    @Override
    public Cpu createCpu() {
        return new AppleCpu("M1");
    }

    @Override
    public Ram createRam() {
        return new AppleRam("8GB");
    }

    @Override
    public Storage createStorage() {
        return new AppleStorage("HDD 250GB");
    }

    @Override
    public Display createDisplay() {
        return new AppleDisplay("Letina Display");
    }

    @Override
    public Os createOs() {
        return new MacOS("macOS");
    }
}
```

### factory/ConcreteFactory2
마찬가지로 GalaxyBook 제품에 맞는 부품들을 생산해내도록 합니다.
```java
public class GalaxyBookSemiconductorFactory implements NoteBookSemiconductorFactory {
    @Override
    public Cpu createCpu() {
        return new SamsungCpu("i-7700k");
    }

    @Override
    public Ram createRam() {
        return new SamsungRam("16GB");
    }

    @Override
    public Storage createStorage() {
        return new SamsungStorage("HDD 250GB");
    }

    @Override
    public Display createDisplay() {
        return new SamsungDisplay("LCD Display");
    }

    @Override
    public Os createOs() {
        return new WindowsOS("windowsOS");
    }
}
```

## 3.2 AbstractProduct
각 공장에서 부품을 만들 때, 어쨌든 AppleCpu나 SamsungCpu나 결국에는 CPU의 종류일 것입니다. 그렇기 때문에 각 부품마다 인터페이스를 제공하여 각 제조사에서는 해당 인터페이스를 통해 확장이 가능한 구조로 설계할 수 있습니다.
### product/AbstractProduct

```java
public interface Cpu {
}
```

### product/concreteProduct1

```java
public class AppleCpu implements Cpu {
    private String name;

		// getter, setter, ...
}
```

### product/concreteProduct2

```java
public class SamsungCpu implements Cpu {
    private String name;

		// getter, setter, ...
}
```
… 나머지 Products들은 생략

## 3.3 NoteBook
기존 노트북에 부품들을 String에서 interface 타입으로 변경되었습니다. prepare()라는 메소드를 통해 서브 클래스에서 어떤 부품들을 써야하는지에 대해서 위임합니다. 설정하고 포장하는 일은 어느 노트북이나 동일하니 필요에 따라 오버라이딩할 수 있습니다.
### noteBook/AbstractNoteBook
```java
public abstract class NoteBook {

    String name;
    Cpu cpu;
    Ram ram;
    Storage storage;
    Display display;
    Os os;

    public abstract void prepare();

    public void setting() {
        System.out.println("Setting NoteBook...");
    }

    public void box() {
        System.out.println("Boxing NoteBook...");
    }
}
```

### notebook/MacBook
각 구상 클래스에는 부품공장을 가지고 있습니다. 이제 해당 노트북에는 어떤 부품을 써야하는지 일일이 알필요가 없어졌습니다.
```java
public class MacBook extends NoteBook {

    NoteBookSemiconductorFactory semiconductorFactory;

    public MacBook(NoteBookSemiconductorFactory semiconductorFactory) {
        this.semiconductorFactory = semiconductorFactory;
    }

    @Override
    public void prepare() {
        this.name = "MacBook";
        this.cpu = semiconductorFactory.createCpu();
        this.ram = semiconductorFactory.createRam();
        this.storage = semiconductorFactory.createStorage();
        this.display = semiconductorFactory.createDisplay();
        this.os = semiconductorFactory.createOs();
    }

    @Override
    public String toString() {
        return "MacBook{" +
                "name='" + name + '\'' +
                ", cpu=" + cpu +
                ", ram=" + ram +
                ", storage=" + storage +
                ", display=" + display +
                ", os=" + os +
                '}';
    }
}
```

### notebook/GalaxyBook

```java
public class GalaxyBook extends NoteBook {

    NoteBookSemiconductorFactory semiconductorFactory;

    public GalaxyBook(NoteBookSemiconductorFactory semiconductorFactory) {
        this.semiconductorFactory = semiconductorFactory;
    }

    @Override
    public void prepare() {
        this.name = "GalaxyBook";
        this.cpu = semiconductorFactory.createCpu();
        this.ram = semiconductorFactory.createRam();
        this.storage = semiconductorFactory.createStorage();
        this.display = semiconductorFactory.createDisplay();
        this.os = semiconductorFactory.createOs();
    }

    @Override
    public String toString() {
        return "GalaxyBook{" +
                "name='" + name + '\'' +
                ", cpu=" + cpu +
                ", ram=" + ram +
                ", storage=" + storage +
                ", display=" + display +
                ", os=" + os +
                '}';
    }
}
```

## 3.4 Client
클라이언트 코드 입니다. 추상 클래스로 createNoteBook()이라는 팩토리 메소드를 제공하고 있습니다. 해당 메소드는 서브 클래스에게 객체 생성 부분을 위임하여 처리하고 있습니다.
```java
public abstract class NoteBookStore {

    public NoteBook orderNoteBook() {
        NoteBook noteBook = createNoteBook();

        noteBook.prepare();
        noteBook.setting();
        noteBook.box();

        return noteBook;
    }

    protected abstract NoteBook createNoteBook();
}
```

### client/ConcreteClient1
스토어를 생성할 때 전달받은 부품공장을 가지고 노트북을 생성하게 됩니다. createNoteBook()에서는 어떤 부품을 가지고 생성했는지에 대해서는 알지 못합니다.
```java
public class MacBookStore extends NoteBookStore {

    NoteBookSemiconductorFactory factory;

    public MacBookStore(NoteBookSemiconductorFactory factory) {
        this.factory = factory;
    }

    @Override
    protected NoteBook createNoteBook() {
        return new MacBook(factory);
    }
}
```

### client/ConcreteClient2

```java
public class GalaxyBookStore extends NoteBookStore {

    NoteBookSemiconductorFactory factory;

    public GalaxyBookStore(NoteBookSemiconductorFactory factory) {
        this.factory = factory;
    }

    @Override
    protected NoteBook createNoteBook() {
        return new GalaxyBook(factory);
    }
}
```

## 3.5 Main
실제 노트북을 만들어보도록 하겠습니다.
```java
public class Main {
    public static void main(String[] args) {
        MacBookSemiconductorFactory macBookSemiconductorFactory = new MacBookSemiconductorFactory();
        GalaxyBookSemiconductorFactory galaxyBookSemiconductorFactory = new GalaxyBookSemiconductorFactory();

        MacBookStore macBookStore = new MacBookStore(macBookSemiconductorFactory);
        GalaxyBookStore galaxyBookStore = new GalaxyBookStore(galaxyBookSemiconductorFactory);

        NoteBook macBook = macBookStore.orderNoteBook();
        System.out.println(macBook);

        System.out.println("---------------------");

        NoteBook galaxyBook = galaxyBookStore.orderNoteBook();
        System.out.println(galaxyBook);
    }
}
```

실행결과

```prolog
Setting NoteBook...
Boxing NoteBook...
MacBook{name='MacBook', cpu=AppleCpu{name='M1'}, ram=AppleRam{name='8GB'}, storage=AppleStorage{name='HDD 250GB'}, display=AppleDisplay{name='Letina Display'}, os=MacOS{name='macOS'}}
---------------------
Setting NoteBook...
Boxing NoteBook...
GalaxyBook{name='GalaxyBook', cpu=SamsungCpu{name='i-7700k'}, ram=SamsungRam{name='16GB'}, storage=SamsungStorage{name='HDD 250GB'}, display=SamsungDisplay{name='LCD Display'}, os=WindowsOS{name='windowsOS'}}
```

### 정리

![[https://en.wikipedia.org/wiki/Abstract_factory_pattern](https://en.wikipedia.org/wiki/Abstract_factory_pattern)](https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Abstract_factory_UML.svg/634px-Abstract_factory_UML.svg.png)
[출처: https://en.wikipedia.org/wiki/Abstract_factory_pattern](https://en.wikipedia.org/wiki/Abstract_factory_pattern)

추상 팩토리 패턴은 인터페이스를 이용해 서로 연관된 구상 클래스를 지정하지 않고도 생성할 수 있습니다. 클라이언트와 팩토리에서 생산되는 제품을 분리시켜 어떤 제품이 생성되는지는 전혀 알 필요가 없습니다.

> **장점**
> - 구상 제품들과 클라이언트 코드 사이의 결합도를 낮춤
> - 팩토리에서 생성되는 제품들의 상호호환을 보장할 수 있음
> - 단일 책임 원칙
> - 개방/폐쇄 원칙

> **단점**
> - 패턴을 구현하기 위해 새로운 인터페이스들이 많이 생성되기 때문에 코드가 복잡해질 수 있음
> - 인터페이스가 변경되면 모든 팩토리에 대한 코드 수정이 필요하다.

# REFERENCES

[팩토리 메서드 패턴](https://refactoring.guru/ko/design-patterns/factory-method)

[추상 팩토리 패턴](https://refactoring.guru/ko/design-patterns/abstract-factory)

[💠 팩토리 메서드(Factory Method) 패턴 - 완벽 마스터하기](https://inpa.tistory.com/entry/GOF-💠-팩토리-메서드Factory-Method-패턴-제대로-배워보자)

[💠 추상 팩토리(Abstract Factory) 패턴 - 완벽 마스터하기](https://inpa.tistory.com/entry/GOF-💠-추상-팩토리Abstract-Factory-패턴-제대로-배워보자)