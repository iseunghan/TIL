# Git Merge 이해하기

![](https://blog.kakaocdn.net/dn/oQZCF/btq935ypfG5/83hZWnMD7YcLHjXkBAftb1/img.png)

***

### Merge

```
$ git merge {branch_id}
```

**: 현재 HEAD가 가리키고 있는 branch에 branch\_id에서 했던 작업들을 합쳐라는 뜻입니다.**

\


**Branch**로 개발 흐름을 쪼개서 개발을 했다면 언젠가는 이 쪼개진 흐름들을 다시 **합쳐야 할 순간**이 올 것입니다. 예를들면 서비스를 배포해야 한다거나 등등 ..

쪼개진 **Branch**들을 합치는 것을 바로 **Merge**라고 합니다.

\


***

### Merge 하기 전 확인

```
$ git merge {합치고 싶은 branch}
```

이전에 [Branch 이해하기](https://iseunghan.tistory.com/325)에서 사용했던 프로젝트의 커밋 히스토리를 살펴보겠습니다.

\


```
$ git log --all --graph
```

![](https://blog.kakaocdn.net/dn/cgkJGa/btq97ytgmzw/qZgvw0sleKVcW61x2hK2OK/img.png)

현재 **HEAD**가 **Func-B**를 가리키고 있고, **develop** 브랜치에서 **Func-A** 와 **Func-B** 이렇게 두 갈래로 흐름이 쪼개졌습니다.

\


이제 **develop** 브랜치로 **Merge** 시켜보도록 하겠습니다.

\


***

### Merge 시키기

> Merge를 하기 위해서 develop 브랜치로 이동하기

```
$ git checkout develop
```

자 이제 **HEAD**는 **develop** 브랜치를 가리키고 있습니다.

\


> 먼저 Func-A부터 Merge를 시켜보겠습니다.

```
$ git merge Func-A
```

![](https://blog.kakaocdn.net/dn/bAWE1y/btq91UKRwPZ/UKYWct4bSt6z5zOkpTnMl0/img.png)

**Merge**가 성공적으로 이뤄졌습니다.

\


> 커밋 로그를 살펴보겠습니다.

```
$ git log --all --graph
```

![](https://blog.kakaocdn.net/dn/mmnAt/btq91NyMRSQ/qKpd6wQlIrutalk4x8L1X0/img.png)

**develop** 브랜치가 **Func-A**가 있는 곳까지 그냥 이동하였습니다. 이 방식을 **Fast-Forward Merge** 라고 합니다.

자세한 내용은 **Merge 정책 이해하기**에서 다루겠습니다.

\


> 자 그럼 지금까지의 작업들을 그림으로 살펴보겠습니다.

![](https://blog.kakaocdn.net/dn/btqwrT/btq97ms9UPx/OfEkwdi0f2Hc5JkQJqPfQk/img.png)

**develop** 브랜치가** Func-A**가 있는 곳으로 쭉 당겨져서 이동을 하였습니다. (**Fast-Forward Merge**)

\


***

### COMPLICT (충돌)

만약 **Func-A** 브랜치와 **Func-B** 브랜치에서 같은 클래스를 수정하고 커밋했다고 가정해보겠습니다.

예를들면 아래와 같이 수정했다고 해보겠습니다.

> develop 브랜치에서 작업한 Test1 클래스

```
public class Test1 {
    // develop 추가
}
```

> Func-A 에서 Test1 클래스에 주석 추가

```
public class Test1 {
    // develop 추가
    // Func-A를 추가했습니다!
}
```

> Func-B 에서 Test1 클래스에 주석 추가

```
public class Test1 {
    // develop 추가
    // Func-B 추가했습니다!
}
```

브랜치를 이동하면 워킹디렉토리도 _**해당 커밋에 따라 상태가 변경 **_된다고 했었습니다!

\


이제 순서대로 **Func-A**와 **Func-B**를 **develop** 브랜치에 **Merge** 시켜보도록 하겠습니다.

> Func-A를 Merge!

```
$ git merge Func-A
```

> 실행 결과

![](https://blog.kakaocdn.net/dn/bFrd54/btq92xJe9oj/Sv07fYsFDh2kgg4uu9syKK/img.png)

**Fast-forward Merge**가 발생한것을 알 수 있습니다.

\


> Func-B를 Merge!

```
$ git merge Func-B
```

> 실행 결과 (COMPLICT 발생!)

![](https://blog.kakaocdn.net/dn/D52DN/btraawO7A9V/j385kezAxbBkkG35p4JGD0/img.png)

충돌이 발생하였습니다. 오류 메세지에 따르면 **Test1.java** 파일에 **Merge conflict**가 발생했다고 합니다. 이 충돌을 **수정**하고 그 다음에 **최종적으로 커밋**을 하라고 안내해줍니다.

\


> Test1.java 수정

```
public class Test1 {
    // develop 추가
<<<<<<< HEAD
    // Func-A를 추가했습니다!
=======
    // Func-B 추가했습니다!
>>>>>>> Func-B
}
```

파일을 열었더니 이런식으로 알수 없는 기호들이 섞여있습니다..

**git**은 **Merge**를 하려고 보니 같은 라인에 다른 주석들이 있으니 먼저 했던 **Func-A**의 주석을 써야할지 이후에 했던 **Func-B**의 주석을 써야할지 헷갈린 것입니다. 그래서 우리에게 해당 부분을 저렇게 표시하고 알려주는 것이죠. ~~뭔가 귀엽지 않나요?~~

![](https://t1.daumcdn.net/keditor/emoticon/friends1/large/014.gif)

\


***

#### COMPLICT 해결 방법!

해당 부분을 최종적으로 내가 남기고 싶은 부분들을 수정해준뒤 **Commit**을 해주면 됩니다!!

```
public class Test1 {
    // develop 추가
    // Func-A를 추가했습니다!
    // Func-B 추가했습니다!
}
```

\


자 이제** Commit**을 해볼까요?

```
$ git add .

$ git commit -m "Merge Func-B"
```

\


그러고 나서 커밋 로그를 살펴보면??

![](https://blog.kakaocdn.net/dn/pY6Ez/btq935FcEpV/MSKr5XoJKtMA1LJAajK7J0/img.png)

짜잔 이렇게 잘 Merge가 이뤄졌습니다!!

\


> 이것을 그림으로 살펴보면 아래와 같습니다.

![](https://blog.kakaocdn.net/dn/b6IwmB/btq96Enh6UI/rJPs4Sa19Zrqr1fZlCc2kK/img.png)

\


***

**REFERENCE**

Merge 정책에는 3가지 정책이 있습니다. 해당 내용은 [**Merge 정책 3가지 이해하기**](https://iseunghan.tistory.com/330) 포스팅을 참조하시기 바랍니다.

[\
](https://iseunghan.tistory.com/330)

\


감사합니다 :D
