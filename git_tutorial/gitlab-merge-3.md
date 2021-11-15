# GitLab Merge 정책 3가지

![](https://blog.kakaocdn.net/dn/bSMhEa/btrastZXf1o/K8RYsy0sWDzySBHo5e7lyK/img.png)

***

GitLab의 Merge 3가지 정책에 대해서 배워보겠습니다.

\


### Merge Request - Merge 정책 3가지

![](https://blog.kakaocdn.net/dn/cacRA6/btrazLSHnPw/zGVocuYzVQRNxdsy10e97K/img.png)

* Merge Commit
* Merge Commit with semi-linear history
* Fast-Forward merge

\


### &#x20;

***

### 현재 커밋 로그 (가정)

![](https://blog.kakaocdn.net/dn/qbyKg/btrazui1Z7E/WM9Ag0mHdF0mUyvLn7ueG1/img.png)

현재 커밋 로그가 위 사진과 같다고 생각해보고 3가지 정책을 실행해 보도록 하겠습니다.

\


### &#x20;

***

### 1. Merge Commit

우리가 흔히 알고 있는 Merge 입니다.

* 특징
  * commit history 모양에 상관없이 **항상 Merge가 허용**됩니다.
  * 항상** 새로운 commit을 생성**하면서 **Merge**가 이뤄집니다.
* 장점
  * 어디서 branch가 갈라졌고, 어디서 합쳐졌는지** 빠짐없이 기록**됩니다.
  * 전체 작업의 **정확한 history**를 볼 수 있습니다.
* 단점
  * 개발 규모가 커지면 history가 엄청나게 **복잡**해집니다.

![](https://blog.kakaocdn.net/dn/K4QBG/btraoO4gUGU/Tn9ppfkopjYIzgbuO7Vxfk/img.png)

**develop** 브랜치에서 **Func-A** 브랜치를 머지하게 되면, **새로운 커밋이 생성**되고 **Merge**가 이루어지게 됩니다.

\


### &#x20;

***

### 2. Merge commit with semi-linear history

이 정책은 커밋 히스토리가 마치 semi-linear(직선) 하다고 해서 붙여진 이름이라고 합니다.

* 특징
  * **Fast-forward Merge**가 **가능한 경우**에만 Merge를 **허용**합니다.
  * 하지만, 실제로는 **1번 정책**과 같은 **새로운 commit을 생성**하여 **Merge**가 이뤄집니다.
* 장점
  * semi-linear 한 형태의 **히스토리가 깔끔**하게 남기 때문에 한눈에 알아보기 편합니다.
* 단점
  * Merge가 허용되지 않을 때는, **Rebase** 작업을 해줘야합니다.

\


> Merge가 허용된 경우

1번 정책과 결과물 같음.

\


> Merge가 허용되지 않는 경우

![](https://blog.kakaocdn.net/dn/bdmS8m/btraoasXmGW/EkPgoxwib2lGVcV3DVTYL1/img.png)

현재 상황은 **Fast-Forward**가 불가능하기 때문에 **Merge**가 허용이 되지 않습니다.

이때 필요한 작업이 바로 **Rebase** 입니다.

![](https://t1.daumcdn.net/keditor/emoticon/niniz/large/023.gif)

\


#### Rebase 하기

Rebase는 **출발점을 재설정**하다 라는 의미를 가집니다.

![](https://blog.kakaocdn.net/dn/bEL7U0/btrav64cO8m/DHWm1idNKHm3GjbJrYQZGK/img.png)

**Func-B** 브랜치를 **develop** 브랜치 뒤로 **rebase** 작업을 진행한 결과입니다.

이렇게 되면 이제 **Fast-Forward Merge**가 가능해졌고, 아래와 같이 **Merge**가 실행될 것입니다.

![](https://blog.kakaocdn.net/dn/qwEMS/btraoCwMtEj/kwterypwWJof29TPQXRKnK/img.png)

\


\


***

### 3. Fast-Forward Merge

* 특징
  * **Fast-Forward**가 가능한 경우에만 **Merge를 허용**합니다.
  * 실제 **Merge**도 **Fast-Forward** 방식으로 진행됩니다.
* 장점
  * **완벽한 일직선의 history**로 정말 깔끔합니다.
* 단점
  * 어디서 **branch**가 갈라졌고 합쳐졌는지 알 수 없습니다.
  * **Merge**가 허용되지 않을 경우에 마찬가지로 **Rebase**를 해줘야 합니다.

![](https://blog.kakaocdn.net/dn/tf3rt/btrapFfvPsf/CyonRw6U5dHW3Q56RKfi40/img.png)

\
