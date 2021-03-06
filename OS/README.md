# OS (운영체제)

* [프로세스와 스레드](#프로세스와-스레드)

---

## 프로세스와 스레드

### 프로세스(Process)
> 일반적으로 실행중인 프로그램을 의미한다.

* 운영체제로 부터 시스템 자원(`주소 공간, 파일, 메모리 등`)을 할당받은 작업의 단위
* 프로세스는 각자의 독립된 메모리 영역을 할당받는다. (다른 프로세스와 공유 X)

프로세스는 실행 중인 프로그램으로 디스크로부터 메모리에 적재되어 CPU의 할당을 받을 수 있는 것을 말합니다. 운영체제로부터 주소 공간, 파일, 메모리 등을 할당 받으며 이것들을 총칭하여 프로세스라고 한다. 그렇기 때문에 다른 프로세스의 자원을 공유하지 않는다.

### 스레드(Thread)
> 스레드는 프로세스 내부의 작업 단위(또는 실행 흐름)를 의미한다.

* 한 프로세스 내의 모든 스레드는 해당 프로세스의 주소 공간이나 자원을 공유한다. 
* 스레드는 각자의 PC Register와 Stack을 갖는다.
	* 멀티 스레딩 환경에서 스레드의 `context switch`에 의해 다시 이전의 작업중이던 함수나, 명령어의 실행 정보등을 기억해야하기 때문이다. 
	* PC Register : 스레드의 명령어가 어디까지 수행되었는지 기억하기 위함.
	* Stack : 함수 호출 시 매개변수, 함수 내부의 선언된 변수등을 저장하기 위함. 
