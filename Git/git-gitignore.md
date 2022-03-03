### .gitignore 적용하기

##### 프로젝트 최상단 root 아래에 `.gitignore` 파일을 생성한다.
```bash
$ vi .gitignore
```

##### 예시 (example)
```bash
# .o .a인 파일 무시
*.[oa]

# .txt인 파일 무시
*.txt

# .txt 파일 중 hello.txt는 무시하지 않음
!hello.txt

# 현재 디렉토리의 TODO 파일 무시 (하위 디렉토리 적용 X)
/TODO

# build/ 디렉토리에 있는 모든 파일은 무시
build/

# doc 디렉토리 아래의 .txt 파일 무시 (하위 디렉토리 X)
doc/*.txt

# doc 디렉토리 아래의 모든 .pdf 파일을 무시 (하위 디렉토리 포함)
doc/**/*.pdf

# 현재, 하위 디렉토리 모든 .DS_Store 파일 무시
**/.DS_Store

```

##### 기존 캐시 삭제 후 적용하기
```bash
# 프로젝트 root에서 실행
$ git rm -r --cached .
$ git add .
$ git commit -m "{msg}"
$ git push origin {branch}
```

### REFERENCE
https://git-scm.com/book/ko/v2/Git%EC%9D%98-%EA%B8%B0%EC%B4%88-%EC%88%98%EC%A0%95%ED%95%98%EA%B3%A0-%EC%A0%80%EC%9E%A5%EC%86%8C%EC%97%90-%EC%A0%80%EC%9E%A5%ED%95%98%EA%B8%B0

