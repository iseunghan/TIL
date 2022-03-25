# scp로 파일 전송하기
> scp 명령어를 이용하여 다른 서버로 파일을 전송하거나, 다운 받을 수 있습니다.

* scp 명령어
	* 기본 포트 : `22 port`

```bash
# 다른 서버로 전송
$ scp [option] (보낼 파일) (아이디)@(서버_주소):(저장할_경로)

# 서버로 부터 다운
$ scp [option] (아이디)@(서버_주소):(파일_경로) (저장할_경로)
```


---

### 실습
* 전송하려는 곳 : `pi@123.123.123.123`

#### 파일 전송
* 현재 디렉토리의 `test.txt`을 `pi`  홈 디렉토리에 `new.txt`라는 이름으로 저장

```bash
$ scp /home/shlee/test.txt pi@123.123.123.123:/home/pi/new.txt
```

#### 디렉토리(내부까지) 전송
* 현재 디렉토리의 `test_dir` 디렉토리를 `pi` 홈 디렉토리에 저장

```bash
$ scp -r /home/shlee/test_dir pi@123.123.123.123:/home/pi
```

#### 기본 포트가 다를 경우 (22가 아닐 경우)
* ssh `1122 port`를 사용한다고 가정

```bash
$ scp -P 1122 /home/shlee/test.txt pi@123.123.123.123:/home/pi
```

#### 파일 다운로드
* `pi`의 `/home/pi/new.txt` 다운

```bash
$ scp pi@123.123.123.123:/home/pi/new.txt ./
```

---

### REFERENCES

[scp - 파일 또는 폴더를 업로드/다운로드 합니다.](https://dejavuqa.tistory.com/358)
[투덜이의 리얼 블로그 :: ssh로 파일전송 - scp](https://tourspace.tistory.com/220)

감사합니다.
