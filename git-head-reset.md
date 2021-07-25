# ✏️ Git HEAD, reset 3가지 옵션

## HEAD 란?

현재 내가 위치해있는 커밋을 가리키는 **식별자**입니다.

보통 커밋을 가리킬 때에는 **HEAD**가 간접적으로  **브랜치**를 통해서 가리키게 되는데 아래의 형태가 바로 그 모습입니다.

![](https://blog.kakaocdn.net/dn/yJt30/btq9R4F69V8/rjgrPmzbWLsQnHcuDpFFmK/img.png)

**HEAD**가 **master** 브랜치를 통해 간접적으로 세번째 커밋을 가리키고 있습니다.

### $ git reset --{option} {commit\_id}

> HEAD가 가리키는 커밋에 따라 working directory의 형태도 바뀌게 됩니다.

한번 첫번째 커밋으로 이동해 보겠습니다. \(옵션은 아래에서 설명하겠습니다.\)

```text
$ git reset --hard {commit_id}
```

![](https://blog.kakaocdn.net/dn/Nj9yC/btq9OxbqvE4/tE6r6itrUZZM68Jl39U7Z1/img.png)

첫번째 커밋으로 이동했더니 두번째 커밋과 세번째 커밋이 없어졌습니다.

자 이제 다시 최신 커밋인 세번째 커밋으로 이동합시다.

어라, **$ git log**로 세번째 커밋 아이디를 보려했더니 reset 옵션으로 인해 커밋이 보이질 않습니다.

![](https://t1.daumcdn.net/keditor/emoticon/friends1/large/017.gif)

그럴 때 사용하는 명령어가 바로 **$ git reflog** 입니다.

### $ git reflog

: **HEAD**가 가리켰던 Commit 기록을 모두 보여주는 명령어 입니다.

![](https://blog.kakaocdn.net/dn/b5Td1W/btq9WquQnuQ/XkGuY4EvXoOVwGOElXn631/img.png)

커밋 ID 옆에 보면 **HEAD@{N}** 라고 있는데 이 **N**이라는 숫자가 작을수록 최근에 HEAD가 가리켰던 Commit을 의미합니다.

제일 마지막 줄에서 세번째를 보면 " **commit: third commit** **"** 이것이 바로 세번째 커밋임을 알 수 있습니다. \(Commit id : **c45b**\)

```text
$ git reset --hard c45b
```

자 이제 세번째 커밋으로 이동할 수 있게 되었습니다!

### git reset 3가지 옵션

* **--soft**
* **--mixed**
* **--hard**

만약 우리가 third commit에서 -&gt; first commit으로 이동한다고 가정해보면 3가지 옵션의 **차이점**은 아래의 그림과 같습니다.

![](https://blog.kakaocdn.net/dn/q3OBY/btq9ReWxGX2/GOBqAWT4YF3CQDchw6MmT0/img.png)

* --soft : HEAD는 **첫번째 커밋**을 가리킵니다. 하지만, Staging Area와 Working Directory는 **기존의 커밋의 상태**로 유지됩니다.
* --mixed: soft 옵션에서 **Staging Area**도 함께 **첫번째 커밋**의 **상태**로 변경됩니다.
* **--hard**: mixed옵션에서 **Working Directory**가 **첫번째 커밋**의 상태로 변경됩니다. \(**위험한 옵션**\)

### git reset은 도대체 언제 사용할까??

![](https://blog.kakaocdn.net/dn/bNUhtt/btq9VkPc3Hr/O1hQC6B9zYQIjzprD8y54k/img.png)

만약 내가 커밋을 했긴 했는데 **commit 2** 와 **commit 3**이 마음에 들지 않는다고 가정해봅시다.

그렇다면 **commit 1**로 **$ git reset {--soft \| --mixed} {commit1\_id}** 명령어를 이용해 이동한 후,

**--soft, --mixed** 옵션은 **working directory**는 그대로이기 때문에 다시 코드를 예쁘게 🌈 수정한 후 

1. **$ git add .** 
2. **$ git commit -m "commit 4"** 순으로 실행해주면?! 

커밋 히스토리가 아래와 같은 형태가 된다!

![](https://blog.kakaocdn.net/dn/S8jzd/btq9NQI4yNH/cDCjwFTxIa3GYYtwSiVrN1/img.png)

