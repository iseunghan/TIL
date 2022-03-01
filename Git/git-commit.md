# Git Commit 이해하기

## Staging Area

Commit을 할 때, 총 3가지 영역을 바탕으로 작동합니다.

* Working Directory : 내가 작업하고 있는 프로젝트의 디렉토리
* Staging Area : 커밋을 하기 위해 $ git add 명령어로 추가한 파일들이 모여있는 공간
* Repository : 커밋들이 모여있는 저장소

![](https://blog.kakaocdn.net/dn/mtzJ7/btq9PUXFlUj/NAqKtGV9XIWHwhA1fq7BY1/img.png)

열심히 코드를 작성하다가 커밋을 해야하는 순간이 오면 **git add .**를 통해 커밋할 파일들을 추가합니다.

이 파일은 바로 Repository에 올라가지 않고, **Staging Area**에 올라가게 됩니다.

**Staging Area**에 추가한 파일들을 **Commit**을 한다면 최종적으로 **저장소**(Repository)로  저장되게 됩니다.

## File Status LifeCycle

![](https://blog.kakaocdn.net/dn/bYqijC/btq9MF1MxCE/05HbpzPuo2hk9Kn0dDt3mK/img.png)

**File** 관점에서는 다시 **4가지 단계**로 나뉜다.

* Untracked : Working Directory에 있는 파일이지만 **Git으로 버전관리**를 하지 않는 상태
* Unmodified : 신규로 파일이 추가되었을 때, new file 상태와 같다. ( $ git add 상태 )
* Modified : 파일이 추가된 이후 해당 파일이 수정되었을 때의 상태
* Staged : **Staging Area**에 반영된 상태

## 왜 바로 커밋 하지않고 Staging Area를 거쳐야 하나요?

아래 포스팅을 참조하시면 됩니다.[\
Git의 Staging Area는 어떤 점이 유용한가Git에는 Staging Area라는 공간이 있다. 어떤 변경사항이 저장소에 커밋되기 전에, 반드시 거쳐야만 하는 중간단계이다. 다른 버전관리도구에는 이에 정확히 대응하는 것은 없다. 저장소가 추적하는blog.npcode.com](https://blog.npcode.com/2012/10/23/git%EC%9D%98-staging-area%EB%8A%94-%EC%96%B4%EB%96%A4-%EC%A0%90%EC%9D%B4-%EC%9C%A0%EC%9A%A9%ED%95%9C%EA%B0%80/)

### REFERENCES

[\[Git\] 형상관리와 Git (3편) - Git 스테이징 단계 이해Git 스테이징단계 이해 Git은 다른 형상 관리시스템과 다르게 소스 코드를 직접 추가하거나 변경하지 않더라도 이를 인지하지 못하며 Git add 명령을 통해서만 인식할 수 있다. => Git의 형상 관리가cornswrold.tistory.com](https://cornswrold.tistory.com/71#google\_vignette)



[Git의 Staging Area는 어떤 점이 유용한가Git에는 Staging Area라는 공간이 있다. 어떤 변경사항이 저장소에 커밋되기 전에, 반드시 거쳐야만 하는 중간단계이다. 다른 버전관리도구에는 이에 정확히 대응하는 것은 없다. 저장소가 추적하는blog.npcode.com](https://blog.npcode.com/2012/10/23/git%EC%9D%98-staging-area%EB%8A%94-%EC%96%B4%EB%96%A4-%EC%A0%90%EC%9D%B4-%EC%9C%A0%EC%9A%A9%ED%95%9C%EA%B0%80/)

[\
\[Git\] Git 세 가지 영역 및 상태 Committed, Modified, Staged 설명Git 에서 세 가지 영역 Git 프로젝트는 Git 디렉터리, 워킹 트리, Staging Area 라는 세 가지 영역을 갖게 됩니다. Git 프로젝트에서 파일들은 아래 세 가지 영역별로 다양한 상태를 가지게 됩니다. Gitdololak.tistory.com](https://dololak.tistory.com/303)\
\
