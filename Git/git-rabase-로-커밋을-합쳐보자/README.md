## 다른 브랜치에서 커밋을 가져오는 방법은 크게 2가지가 있습니다.

- git merge
- git rebase

## 둘의 차이점은?
> Merge와 rebase의 큰 차이점은 하나로 합치냐 모두 가져오냐 입니다.  

![](./git1.png)
merge로 커밋을 합치면 모든 커밋들이 다 보이는 반면, rebase로 합친 커밋로그는 깔끔하게 하나만 남기도록 할 수 있습니다.

## git rebase를 이용해서 커밋을 합쳐보자

### 1. 커밋 리스트 확인

```bash
$ git log
commit a1d131c918d52ed96759d1f181e3cac96aa80031
Author: iseunghan <lsh13288@gmail.com>
Date:   Thu Aug 4 07:38:20 2022 +0000

    Third commit

commit a1d131c918d52ed96759d1f181e3cac96aa80031
Author: iseunghan <lsh13288@gmail.com>
Date:   Thu Aug 4 07:38:20 2022 +0000

    Second commit

commit a1d131c918d52ed96759d1f181e3cac96aa80031
Author: iseunghan <lsh13288@gmail.com>
Date:   Thu Aug 4 07:38:20 2022 +0000

    First commit
```

3개의 커밋이 있다고 가정합니다.

### 2. git rebase로 합치기

```bash
$ git rebase -i @~3
pick 1cc4c33 First Commit
pick 8973c50 Second Commit # pick -> s
pick a1d131c Third Commit  # pick -> s

# Rebase fcf15ab..91a8b37 onto fcf15ab (4 commands)
#
# Commands:
# p, pick <commit> = use commit
# r, reword <commit> = use commit, but edit the commit message
# e, edit <commit> = use commit, but stop for amending
# s, squash <commit> = use commit, but meld into previous commit
# f, fixup [-C | -c] <commit> = like "squash" but keep only the previous
#                    commit's log message, unless -C is used, in which case
#                    keep only this commit's message; -c is same as -C but
#                    opens the editor
# x, exec <command> = run command (the rest of the line) using shell
# b, break = stop here (continue rebase later with 'git rebase --continue')
# d, drop <commit> = remove commit
# l, label <label> = label current HEAD with a name
# t, reset <label> = reset HEAD to a label
# m, merge [-C <commit> | -c <commit>] <label> [# <oneline>]
# .       create a merge commit using the original merge commit's
# .       message (or the oneline, if no original merge commit was
# .       specified); use -c <commit> to reword the commit message
#
# These lines can be re-ordered; they are executed from top to bottom.
#
# If you remove a line here THAT COMMIT WILL BE LOST.
#
# However, if you remove everything, the rebase will be aborted.
```
`-i`는 interactive 대화형 모드입니다.  `@~3`(또는 `HEAD~3` 이렇게 사용해도 됨) 최근 3개의 커밋을 rebase하겠다는 뜻입니다.
최종적으로 남기고 싶은 커밋은 `pick`으로, 합치고 싶은 커밋은 squash의 약자인 `s`로 변경해줍니다.

### 3. 최종 변경된 모습

```bash
$ git rebase -i @~3
pick 1cc4c33 First Commit
s 8973c50 Second Commit # pick -> s
s a1d131c Third Commit  # pick -> s

# Rebase fcf15ab..91a8b37 onto fcf15ab (4 commands)
#
# Commands:
# p, pick <commit> = use commit
# r, reword <commit> = use commit, but edit the commit message
# e, edit <commit> = use commit, but stop for amending
# s, squash <commit> = use commit, but meld into previous commit
# f, fixup [-C | -c] <commit> = like "squash" but keep only the previous
#                    commit's log message, unless -C is used, in which case
#                    keep only this commit's message; -c is same as -C but
#                    opens the editor
# x, exec <command> = run command (the rest of the line) using shell
# b, break = stop here (continue rebase later with 'git rebase --continue')
# d, drop <commit> = remove commit
# l, label <label> = label current HEAD with a name
# t, reset <label> = reset HEAD to a label
# m, merge [-C <commit> | -c <commit>] <label> [# <oneline>]
# .       create a merge commit using the original merge commit's
# .       message (or the oneline, if no original merge commit was
# .       specified); use -c <commit> to reword the commit message
#
# These lines can be re-ordered; they are executed from top to bottom.
#
# If you remove a line here THAT COMMIT WILL BE LOST.
#
# However, if you remove everything, the rebase will be aborted.
```

이후 `:wq` 로 빠져나옵니다.

### 4. 커밋 정리

```bash
# This is a combination of 4 commits.
# This is the 1st commit message:

First commit
# This is the commit message #2:

Second commit
# This is the commit message #3:

Third commit    # 이 부분은 남기고 싶은 커밋이므로 남겨둡니다.

# Please enter the commit message for your changes. Lines starting
# with '#' will be ignored, and an empty message aborts the commit.
#
# Date:      Thu Aug 4 07:25:11 2022 +0000
#
# interactive rebase in progress; onto fcf15ab
# Last commands done (4 commands done):
#    squash a1d131c Update .gitlab-ci.yml file
#    squash 91a8b37 Update .gitlab-ci.yml file
# No commands remaining.
# You are currently rebasing branch 'deploy' on 'fcf15ab'.
#
# Changes to be committed:
#       new file:   .gitlab-ci.yml
#
```
`Third commit` 부분만 남겨두고 나머지 부분은 삭제해줍니다.

### 5. 최종적인 모습

```bash
Third commit

# Please enter the commit message for your changes. Lines starting
# with '#' will be ignored, and an empty message aborts the commit.
#
# Date:      Thu Aug 4 07:25:11 2022 +0000
#
# interactive rebase in progress; onto fcf15ab
# Last commands done (4 commands done):
#    squash a1d131c Update .gitlab-ci.yml file
#    squash 91a8b37 Update .gitlab-ci.yml file
# No commands remaining.
# You are currently rebasing branch 'deploy' on 'fcf15ab'.
#
# Changes to be committed:
#       new file:   .gitlab-ci.yml
#
```

### 6. 커밋 확인

```bash
$ git log
commit a1d131c918d52ed96759d1f181e3cac96aa80031
Author: iseunghan <lsh13288@gmail.com>
Date:   Thu Aug 4 07:38:20 2022 +0000

    Third commit

```
이렇게 깔끔하게 커밋을 합쳐보았습니다. 한가지 주의할 점은 이미 저장소에 푸시한 상태일때는 가급적 rebase를 사용하지 않는것이 좋습니다. 왜냐하면 다른 사람이 봤을 때는 동일한 커밋인데 내용은 rebase로 합쳐졌기 때문에 혼란이 올 수 있기 때문입니다. 그렇기에 로컬에서 작업을 하고 저장소에 푸시하기 전에 커밋을 깔끔하게 다듬는 역할로 사용하면 좋을 것 같습니다. 감사합니다.

좀 더 자세한 내용은 아래 FlyingSquirrel님의 medium에서 확인하실 수 있습니다.

## REFERENCES

[git rebase 하는 방법](https://flyingsquirrel.medium.com/git-rebase-%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95-ce6816fa859d)