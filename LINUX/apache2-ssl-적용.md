# [Ubuntu] Apache(아파치) SSL 적용
> 환경 : `Ubuntu 20.04`  + `apache2`

1. OpenSSL 설치 및 인증서 발급
2. Apache SSL 적용

## OpenSSL 설치
```
$ sudo apt install openssl
```

## CA 인증서 생성
* `private key` 생성

```bash
$ openssl genrsa -des3 -out server.key 2048
Enter pass phrase for server.key : (개인키 암호 입력)
```

* CSR(Certificate Sinning Request) 인증요청서 생성

```bash
$ openssl req -new -days 365 -key server.key -out server.csr
Enter pass phrase for server.key: (이전에 입력한 개인키 암호)
```

추가로 입력하는 부분은 적절히 입력하고 넘어가면 됩니다.

* 개인키 password 제거
	* 개인키의 패스워드가 있다면, 아파치 구동 시마다 입력해야하기 때문에 편의상 제거를 합니다.
	* SSL 기능에는 문제 없으니 걱정하지 않으셔도 됩니다.

```bash
$ sudo cp server.key server.key.backup
$ sudo openssl rsa -in server.key.backup -out server.key
Enter pass phrase for server.key.backup: (개인키 암호)
```


## 자체서명 인증서 생성
* `server.key` + `server.csr` 를 이용하여 인증서 요청
	* 기본 포멧: `PEM (base64 encoding)`

```bash
$ sudo openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
```

* 인증서 확인

```bash
$ ls -l server*

$ openssl x509 -in server.crt -text
```


## Apache SSL 적용

* SSL 모듈 enable

```bash
$ sudo a2enmod ssl
```

* 편의를 위한 SSL 전용 디렉토리 생성

```bash
$ sudo mkdir /etc/apache2/ssl
```

* 생성한 인증서 복사

```bash
$ sudo cp server* /etc/apache2/ssl/
```

* 보안을 위해 소유권 및 권한 변경

```bash
$ sudo chown -R root:root /etc/apache2/ssl
$ sudo chmod 700 /etc/apahce2/ssl
$ sudo chmod 600 /etc/apache2/ssl/*.*
```

### VirtualHost 설정

* 디렉토리 변경

```bash
$ cd /etc/apache2/site-available
```

* `default-ssl.conf`를 사용 할 도메인 명으로 복사

```bash
$ sudo cp default-ssl.conf example-ssl.conf
```

* `example-ssl.conf` 수정

```bash
$ sudo vi example-ssl.conf

# 아래의 내용 주석 해제 및 수정
SSLEngine on
SSLCertificateFile /etc/apache2/ssl/server.crt
SSLCertificateKeyFile /etc/apache2/ssl/server.key
```

* `example-ssl.conf` enable

```bash
$ sudo a2ensite example-ssl.conf
```

* apache 재시작

```bash
$ sudo service apache2 restart
```



---

## REFERENCES
[리눅스 자체서명 SSL 인증서 생성 - 제타위키](https://zetawiki.com/wiki/%EB%A6%AC%EB%88%85%EC%8A%A4_%EC%9E%90%EC%B2%B4%EC%84%9C%EB%AA%85_SSL_%EC%9D%B8%EC%A6%9D%EC%84%9C_%EC%83%9D%EC%84%B1)
[HTTPS와 SSL 인증서 - 생활코딩](https://opentutorials.org/course/228/4894)
[WEBDIR :: Ubuntu 우분투 Apache(아파치) SSL 적용](https://webdir.tistory.com/228)
[세상의 모든 기록 :: Ubuntu 아파치(Apache) OpenSSL 적용](https://all-record.tistory.com/189)

감사합니다.
