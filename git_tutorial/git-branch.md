# Git Branch란?

![](https://blog.kakaocdn.net/dn/rDq3C/btq91pErdDG/yqDVfyp0afYvstWTY0jkT1/img.png)

\


***

### Branch 란?

"나무가지" 또는 "분기"라는 뜻을 가지고 있습니다.&#x20;

**git** 에서는 **특정 커밋**을 가리키는 **포인터**라고 합니다.

\


***

### Branch는 왜 사용할까?

실무에서는 master 브랜치에서 쭉 개발을 하지 않고, 각 기능별로 개발 흐름을 쪼개서 병렬적으로 처리할 수 있도록 해줍니다.

![](https://blog.kakaocdn.net/dn/2XE67/btq91N58A0J/8zWJRA5YQsKHZ8QNWsznU1/img.png)

이런식으로 기능별로 각 브랜치를 생성해 개발 흐름을 쪼개서 다른 브랜치의 영향을 받지 않고 독립적으로 그 기능을 개발 할 수 있도록 해주는 개념입니다.

\


***

### Branch 실습해보기

**master** 브랜치에서 개발에 필요한 **develop** 브랜치로 쪼개고 또 그 안에서 **기능 A(Func-A)**와 **기능 B(Func-B)** 브랜치로 나눠보겠습니다.

\


**1. 현재 커밋 로그 확인**

![](https://blog.kakaocdn.net/dn/1feA5/btq93nTnVHB/pt8sim0f7YA7SsdFcla06k/img.png)

현재 **HEAD**가  **master **브랜치를 가리키고 있습니다.

이제 **master** 브랜치에서 **develop** 브랜치를 생성해주겠습니다.

\


**2. develop 브랜치 생성하기**

```
$ git branch develop
```

다시 **$ git log** 를 통해 보겠습니다.

![](https://blog.kakaocdn.net/dn/bltSYc/btq97OJcfom/sK6hTH8AynhmO39Hj0BS2k/img.png)

자 이제 **develop** 브랜치가 생성되었습니다.&#x20;

\


**3. 브랜치 안에서 또 브랜치 생성하기 (checkout 명령어)**

위 상황에서는 **HEAD**가 **master** 브랜치를 가리키고 있기 때문에 **develop** 브랜치를 가리키게 해줘야합니다.

이때 사용하는 명령어는 **$ git checkout** 명령어입니다.

```
$ git checkout develop
```

![](https://blog.kakaocdn.net/dn/KYhQQ/btq97ysU3EL/k5w5QvgnDEW5jkMpN5dpS1/img.png)

자 이제 **HEAD**가 **develop** 브랜치를 잘 가리키고 있습니다.

\


이제 기능 A와 기능 B를 따로 개발을 진행하기 위해, 각각 알맞게 브랜치를 생성해주겠습니다.

```
$ git branch Func-A

$ git branch Func-B
```

\


**4. 각 브랜치에서 기능을 개발해보기**

* 간단하게 예시로
  * 1번째로 Func-A에서는 ClassA를 생성하고
  * 2번째로 Func-B에서는 ClassB를 생성해서 각각 커밋을 해보도록 하겠습니다.

\


> Func-A 개발

1\. 먼저 **checkout** 명령어로 해당 브랜치로 이동!

```
$ git checkout Func-A
```

\


2\. 새로운 클래스 생성하고 **$** **git add** 후 **$ git commit** 수행

```
$ git add ClassA.java
$ git commit -m "Add ClassA in FuncA"
```

\


> 현재 상태의 $ git log를 통해서 한번 볼까요?

![](https://blog.kakaocdn.net/dn/lsxiN/btq97OP0rUn/OKQNyZ1A3NV8yReLM6YUw0/img.png)

이렇게 보니까 잘 이해가 잘 안되는 것 같습니다. 그림으로 한번 보겠습니다.

![](https://blog.kakaocdn.net/dn/bGGBRV/btq9Zr27129/GnKbzBa0Cf8dCMBELh7wtk/img.png)

**Func-A** 브랜치는 최신 커밋을 가리키고 있고, 아직 **develop** 브랜치와 **Func-B** 브랜치는 2번째 커밋을 가리키고 있습니다. (이 두 브랜치의 코드는 자신들이 가리키고 있는 **2번째 커밋의 상태**입니다.)

\


> Func-B 개발

이제 Func-B 기능을 개발해보도록 하겠습니다.

1\. 먼저 Func-B 브랜치로 이동을 해줍니다.

```
$ git checkout Func-B
```

\


2\. 새로운 클래스 생성하고 **$** **git add** 후 **$ git commit** 수행

```
$ git add ClassB.java

$ git commit -m "Add ClassB in Func-B"
```

\


> $ git log 를 통해 현재 커밋 로그를 살펴보겠습니다.

![](https://blog.kakaocdn.net/dn/bsqJtc/btq91oMhWRB/EzbYUjOvKZeiykxVqRwWYk/img.png)

음.. 방금 커밋한 내용은 잘 나왔는데 이전에 우리가 했던 **Func-A** 브랜치에서 커밋했던 내용은 안나옵니다. 왜그럴까요?

현재 **HEAD**가 가리키고 있는 커밋이 **Func-B**의 커밋이기 때문입니다.

그렇다면 모든 브랜치의 커밋내용을 확인 하고 싶을 때는 어떻게 하면 될까요?

![](https://t1.daumcdn.net/keditor/emoticon/niniz/large/008.gif)

바로 **$ git log --all** 입니다.

\


#### $ git log --all --graph

* **--all** : 모든 브랜치들의 커밋내역을 볼 수 있습니다.
* **--graph** : 브랜치들의 커밋 관계를 그래프 형식으로 볼 수 있습니다.
* 이외의** --oneline** 옵션도 있습니다.

![](https://blog.kakaocdn.net/dn/bJc36F/btq91UDNnWd/dadP9ZspgSt9AAQ99hUw5k/img.png)

자 이제 **모든 브랜치**들의 커밋 내역까지 다 볼 수 있습니다!

\


> 정리

기능별로 브랜치를 생성하여 흐름을 쪼개면 이렇게 해당 기능을 독립적으로 개발할 수 있고, 나중에 오류가 생겼을 때 해당 브랜치로 이동하여서 수정을 하면 되기 때문에 개발을 할 때 많은 이점을 줍니다.

\


***

**REFERENCE**

> Merge 이해하기

이제 **branch**를 배웠으면 꼭 알아야하는 개념이 있는데 바로 **Merge** 입니다. 브랜치를 쪼갰으면 나중에 가서 다시 합쳐야 하는데 그 작업을 **Merge**라고 합니다.

Merge에 대한 설명은 [Merge 이해하기](https://iseunghan.tistory.com/326) 포스팅을 참조하시기 바랍니다.

[\
](https://iseunghan.tistory.com/326)

\


감사합니다 :D
