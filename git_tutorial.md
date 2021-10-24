# ✏️ Git 이해 및 기본 사용법

## Git 이란?

소스코드의 `버전관리`와 다른 개발자와 `협업`하기 위해 사용하는 프로그램입니다.



## Git vs GitHub ?

서로가 같다고 생각하는 경우가 많은데 GitHub는 **Git 기반**의 `저장소 서비스`를 말합니다.



## Git 시작하기

1. 먼저 `Terminal`을 실행하여 버전관리하고 싶은 프로젝트의 루트 디렉토리로 이동합니다.



IntelliJ의 내장 **Terminal**을 열어줍니다.

![](https://blog.kakaocdn.net/dn/cQETkP/btq9OIiueHZ/RW3kQkYgOOmZqZskkGrgc1/img.png)

###

### $ git init

```
$ git init
```

![](https://blog.kakaocdn.net/dn/bZl2vC/btq9Rc4pokU/6MEYH6C9vgASKlSwZyADD1/img.png)

명령어 아래에 보면 우리의 프로젝트 디렉토리 아래에 **.git**이라는 디렉토리가 생성되었는데 이 디렉토리에서 버전관리를 위하여 필요한 것들을 알아서 관리를 해줌으로써 우리가 Git을 이용하여 **버전관리**를 할 수 있게 되었습니다.

###

### $ git config user.\*\*

유저의 정보들을 설정해줍니다.

```
$ git config user.name "username"
```

```
$ git config user.email "user@email.com"
```

![](https://blog.kakaocdn.net/dn/c0bQrR/btq9MEg2RUv/7II3P7SdjD5FIpw8JNOxW1/img.png)

###

### $ git add {file\_name}

이제 아무 클래스나 생성하고 커밋을 해보도록 하겠습니다.

저장할 때는 2가지 방법이 있는데,&#x20;

1. 특정 파일을 추가하는 방법 : **$ git add {file\_name}**

```
$ git add People.java
```

&#x20;2\. 변경된 모든 파일을 추가하는 방법 : **$ git add .**

```
$ git add .
```

&#x20;보통 저장할 파일들이 많고 귀찮기 때문에 2번째 방법을 더 많이 씁니다.

![](https://blog.kakaocdn.net/dn/bof58w/btq9JNkZurJ/MakxE55J1ZVl0PeLkyMDz0/img.png)

### $ git commit -m "message"

커밋할 파일들을 추가했다면 이제 커밋을 하면 됩니다.

```
$ git commit -m "first commit"
```

\-m 옵션은 커밋 메세지를 주기위한 옵션입니다.

![](https://blog.kakaocdn.net/dn/bttMIA/btq9JNSNcCu/zDrGDn8eWlsl9quRrZTPrK/img.png)

커밋이 아주 잘 되었습니다.

###

### $ git log

이때까지 한 커밋들의 로그를 살펴볼 수 있는 명령어 입니다.

```
$ git log
```

![](https://blog.kakaocdn.net/dn/ZbjKr/btq9NQuAFWx/aLnD6TQy7mk9RJOvZqxBJ1/img.png)

Author에는 이전에 우리가 설정해줬던 user.name 과 user.email이 적혀있다.

여기서 주의깊게 봐야하는 것은 commit 옆에 기다란 숫자와 영문으로 섞인 **commit id**입니다.

**앞 4자리**만 있어도 식별할 수 있기 때문에 다 외울 필요는 없습니다.

###

### $ git diff {commit\_id} {commit\_id}

해당 커밋의 차이점을 보여주는 명령어 입니다. 이전에 git log 명령어로 봤던 commit id의 앞 4자리를 적어주면 됩니다.

```
$ git diff 8329 2777
```

![](https://blog.kakaocdn.net/dn/bkE7OJ/btq9O6XZkgN/tMO2kxa18NiC7Vg6lr0ks1/img.png)

diff 명령어를 사용하게 되면, 어느 부분이 추가되고 삭제 되었는지 알 수 있습니다.

###

### GitHub Repository 생성

![](https://blog.kakaocdn.net/dn/rSgCB/btq9NtUirk8/kkKrMelmhESfxzTFkiz661/img.png)

저장소를 생성하면, Git으로 저장소를 컨트롤할 수 있는 명령어들을 친절하게 보여줍니다.

###

### $ git remote add origin {Repository}

```
$ git remote add origin https://github.com/iseunghan/Tutorial_git.git
```

URL이 가리키는 외부 서버의 프로젝트를 원격 저장소로 지정하는데 이름은 **origin**으로 하겠다는 의미입니다.

###

### $ git push -u origin master

이제 커밋한 내용들을 원격 저장소로 푸시(영구 저장)을 하도록 합시다.

```
$ git push -u origin master
```

* master : 내 컴퓨터의 master 브랜치가 **원격 저장소**의 **master** 브랜치를 바라보게 하라는 의미
* \-u : --set-upstream 의 옵션과 동일하다. (이후에는 $ git push 하면 자동으로 master 브랜치로 푸시하게 된다!)
