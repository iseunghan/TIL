# # HomeBrew 란?
Mac OS 용 패키지 관리자라고 생각하면 됩니다. Apple 또는 Linux에서 제공하지 않는 유용한 패키지 관리자를 설치합니다.

HomeBrew를 통해 이런 것들을 설치할 수 있다. [homebrew-core — Homebrew Formulae](https://formulae.brew.sh/formula/)

HomeBrew를 이용하면 그냥 커맨드 하나로 설치가 가능하니 간편합니다!

## HomeBrew install

[macOS 용 패키지 관리자 — Homebrew](https://brew.sh/index_ko)  
`Terminal`을 열고 아래 커맨드를 붙여넣기 하면 끝!

```
$ /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

(혹시 HomeBrew 설치하다가 오류 나시는 분들은 AppStore에서 Xcode를 설치해보시길 바랍니다.)

## 간단하게 설치해보기

> 다운받고 싶은 패키지가 있는지 먼저 조회 (search)!  

```
$ brew search mysql
```

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FzgTMW%2FbtrqPhXwLcy%2FBKAqsVBjkoZTWoYFTDcyRk%2Fimg.png)

> 다운 받고 싶은 패키지를 골라서 설치해주면 됩니다.  

```
$ brew install mysql    # 저는 그냥 최신 버전을 다운 받았습니다.
```

## 설치된 위치

HomeBrew를 통해 설치한 패키지들은 `/opt/homebrew/` 아래에 설치되고, `/usr/local` 위치에 심볼릭 링크를 연결합니다.

# REFERENCE
[macOS 용 패키지 관리자 — Homebrew](https://brew.sh/index_ko)
https://whitepaek.tistory.com/3