# JAVA

## 목차

* [OOP : 객체 지향 프로그래밍](java.md#oop)
* [캡슐화, 상속, 다형성](java.md#java)
* [접근제어자](java.md#undefined)
* [자바 프로그램 실행과정](java.md#undefined-2)
* [Intepreter vs JIT Compiler](java.md#interpreter-vs-jit-compiler)
* [Runime Data Areas (메모리 구조)](java.md#runtime-data-areas)
* [Annotation](java.md#annotation)
* [제네릭](java.md#generic)
* [final](java.md#final)
* [오버라이딩 vs 오버로딩](java.md#vs)



## OOP : 객체 지향 프로그래밍

객체 지향 프로그래밍 이전의 프로그래밍은 컴퓨터가 사고하는 대로 프로그래밍을 했었다. 하지만 객체 지향 프로그래밍은 `인간 중심적 프로그래밍 패러다임` 이라고 할 수 있다. 즉 현실 세계를 프로그래밍으로 옮겨와 프로그래밍하는 것을 말한다.

> 현실 세계의 사물들을 객체라고 보고 그 객체로부터 개발하고자 하는 애플리케이션에 필요한 특징들을 뽑아와 프로그래밍하는 것이다. 이것을 추상화라고 한다.

OOP로 코드를 작성하면 이미 작성한 코드에 대한 `재사용성`이 높다. **자주 사용되는 로직**을 `라이브러리`로 만들어두면 **재사용**할 수 있으며 그 **신뢰성**을 확보 할 수 있다. 객체 단위로 코드가 나눠져 작성되기 때문에 디버깅이 쉽고 유지보수에 용이하다. 또한 데이터 모델링을 할 때 객체와 매핑하는 것이 수월하기 때문에 요구사항을 보다 명확하게 파악하여 프로그래밍을 할 수 있다.

#### 객체 지향적 설계 원칙 (SOLID)

**SRP(Single Responsibility Principle) : 단일 책임 원칙**

* 클래스는 단 하나의 책임을 가져야 하며, 클래스를 변경하는 이유는 단 하나의 이유이어야 한다.

**OCP(Open-Closed Principle) : 개방-폐쇄 원칙**

* 확장에는 열려있어야 하고 변경에는 닫혀 있어야 한다.

**LSP(Liskov Substitution Priciple) : 리스코프 치환 원칙**

* 상위 타입의 객체를 하위 타입의 객체로 치환해도 상위 타입을 사용하는 프로그램을 정상적으로 동작해야 한다.

**ISP(Interface Segregation Priciple) : 인터페이스 분리 원칙**

* 인터페이스는 그 인터페이스를 사용하는 클라이언트를 기준으로 분리해야 한다.

**DOP(Dependency Inversion Priciple) : 의존 역전 원칙)**

* 고수준 모듈은 저수준 모듈의 구현에 의존해서는 안된다.

## JAVA의 캡슐화, 상속, 다형성이란?

* **캡슐화**
  * 해당 객체에 필드, 메소드를 하나로 묶고 이를 외부로부터 감추는 것을 말합니다.
  * 접근제어자를 이용해 노출시킬 것인지 숨길 것인지 결정할 수 있습니다.
  * 캡슐화를 통해 정보를 은닉화하여 외부로부터 중요한 데이터를 보호할 수 있습니다.
* **상속**
  * 자식이 부모에게 물려받는 것을 말합니다.
  * 부모 클래스의 코드를 자식 클래스가 물려받아 빠르게 개발할 수 있습니다.
  * 상속을 이용하면, 코드의 재사용성이 뛰어나고, 유지보수가 편한 장점이 있습니다.(유지보수 : 부모클래스만 고치면 자식클래스들에게 모두 반영되는 장점, 오버라이딩이 안됐을 때를 가정)
  * 객체의 다형성을 구현할 수 있습니다.
* **다형성**
  * 하나의 객체가 여러가지 타입을 가질 수 있는 것을 의미합니다.
  * `Is - A` 관계이다.
  * `Animal a = new Dog(); // O` -> 개는 동물이다. (O)
  * `Dog d = new Animal(); // X` -> 동물은 개다. (X)
  * 예를 들어, 스피커로 예를들면, 저음에 특화된 스피커가 있을거고, 고음에 특화된 스피커가 있듯이 하나의 스피커가 여러가지 종류를 가질 수 있는 것이 다형성이라고 생각합니다.

## 접근제어자

자바에는 4개의 접근제어자가 있습니다.  `public > protected > default > private`

![](https://images.velog.io/images/iseunghan/post/eeac8102-456c-4911-858d-d119fc9b2a9d/image.png)

* public : 모든 접근 가능
* protected : 동일 패키지 또는 상속 관계
* default : 동일 패키지 내
* private : 해당 클래스만

## 자바 프로그램 실행과정 <a href="#undefined" id="undefined"></a>



![](https://media.vlpt.us/images/iseunghan/post/adf77297-bc3b-4b10-a087-0f482a7cf845/13576C2A-B132-4A99-B122-646F64A07BE5.png)

1\. 프로그램이 실행되면, JVM은 OS로부터 이 프로그램이 필요로 하는 `메모리를 할당`\
2\. `자바 컴파일러(javac)`가 `소스코드(.java)`를 읽어들여 `바이트코드(.class)`로 변환\
3\. `Class Loader`를 통해 `class` 파일들을 JVM에 로딩\
4\. 로딩된 `class` 파일들을 `Execution engine`을 통해 해석 (Interpreter vs JIT Compiler)\
5\. 해석된 바이트 코드는 `Runtime Data Areas`에 배치되어 실직적인 수행이 이뤄진다.\
6\. 이러한 실행과정 속에서 JVM은 필요에 따라 Thread Syncronization과 GC같은 관리 작업을 수행한다.



## Interpreter vs JIT Compiler <a href="#interpreter-vs-jit-compiler" id="interpreter-vs-jit-compiler"></a>

`바이트코드(.class)`를 해석하는 방식은 두가지가 있다.

* 바이트코드를 한 라인씩 읽고 해석하는 `Interpreter`(속도가 느림) 방식과, 단점을 보완해 등장하는 것이 `JIT(Just In Time) Compiler` 방식이 있다.
* `JIT Compiler`는 이미 해석된 바이트코드 부분은 **저장**해두었다가 반복되는 부분이 실행될 때, 해당 부분은 해석과정에서 빼버리는 것이다. 그렇기 때문에 반복수행되지 않는다면 `Intepreter`방식이 효율적이다. (속도, 비용 측면)
* 하지만 JIT는 바이트코드를 `JIT Compiler`를 통해 어셈블러 같은 `Native Code`로 변경되어 수행되는데 어셈블러로 변환하면 속도가 빨라지지만 비용이 발생하게 된다.
* 그래서 JVM은 모든 코드를 `JIT Compiler` 방식으로 실행하지 않고, `Interpreter` 방식을 사용하다가 일정한 기준이 넘어가면 `JIT Compiler` 방식으로 실행한다.

## Runtime Data Areas (메모리 구조) <a href="#runtime-data-areas" id="runtime-data-areas"></a>

![](https://media.vlpt.us/images/iseunghan/post/b88b6610-ccf4-405f-87d9-8e23efee90bf/BFBE1F4C-8CC7-4583-A440-B62E8412B181.png)

**Method Area (= Class Area = Static Area) - 모든 Thread가 공유**

> 클래스 정보를 처음 메모리 공간에 올릴 때 초기화 되는 대상을 위한 메모리 공간\
> 클래스, 변수, Method, static 변수, 상수 정보 등이 저장되는 영역이다.

* Filed information: 멤버 변수의 이름, 데이터 타입, 접근 제어자에 대한 정보
* Method information: 메소드의 이름, 리턴 타입, 매개변수, 접근 제어자에 대한 정보
* Type information: Type의 속성이 Class인지 interface인지의 여부 저장
* static 변수: 모든 객체가 공유할 수 있고, 객체 생성 없이 접근 가능
* 상수 풀: 문자 상수, 타입, 필드, 객체 이름으로 참조하는 것도 저장

**Heap Area**

> 객체를 저장하는 가상 메모리 공간 (모든 Thread 공유)

* `new 연산자`로 생성된 객체와 배열을 저장한다. (`Class Area`영역에 올라온 클래스들만 객체로 생성 가능)
* Heap 영역은 `Young Generation`, `Old Generation`, `Permanent Generation` 으로 구성됨.

**Stack Area**

> 메소드 내에서 사용되는 값들(`매개변수, 지역변수, 리턴값 등`)이 저장되는 구역으로 메소드 호출 시마다 각각의 메소드마다 `스택프레임(각 메소드를 위한 공간)`이 생성된다

* `LIFO(Last In First Out)`을 준수하며, 메소드를 빠져나가면 해당 스택프레임은 `LIFO`를 지키며 소멸이 된다.

**PC Register**

> Thread가 생성 될 때마다 생성되는 공간

* 현재 Thread가 실행되는 부분의 주소와 명령과 저장

**Native Method Stack**

> 자바의 언어로 작성된 네이티브 코드를 위한 메모리 영역

## Annotation <a href="#annotation" id="annotation"></a>

어노테이션이란 원래 주석이라는 의미이지만, 어떤 클래스나 메소드에 주석처럼 달아서 해당 클래스나 메소드에 특별한 의미를 부여하거나, 기능을 주입할 수 있습니다.\
`대표적인 예) @Override`

***

## 제네릭(Generic) <a href="#generic" id="generic"></a>

제네릭은 안정성을 위해 사용합니다. 예를들어 어떤 컬렉션에 제가 원하는 타입의 객체만 넣게끔 할 수도 있습니다. 그렇게 하면 장점이 내부적으로 해당 타입이 맞는지 확인하지 않아도 되고, 나중에 컬렉션에서 값을 빼올 때 불필요한 형변환을 거치지 않아도 되는 장점이 있습니다.

***

## final <a href="#final" id="final"></a>

final을 붙이게 되면 변경이 불가능!

* Class는 상속 불가!
* 메소드는 오버라이딩 불가!
* 변수는 다른 값 대입 불가!

***

## 오버라이딩 vs 오버로딩 <a href="#vs" id="vs"></a>

* 오버라이딩 : `같은 메소드 시그니처`를 갖는 메소드를 **덮어 씌우는 것(재정의)** 을 의미합니다.
* 오버로딩 : `다른 메소드 시그니처`를 갖는 메소드를 **선언(생성)** 하는 것을 의미합니다.
