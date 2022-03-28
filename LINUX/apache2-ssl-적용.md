# [Ubuntu] Apache SSL 적용해보기
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

# server.key 생성
```

* CSR(Certificate Sinning Request) 인증요청서 생성

```bash
$ openssl req -new -days 365 -key server.key -out server.csr
Enter pass phrase for server.key: (이전에 입력한 개인키 암호)
..생략

Country Name (2 letter code) [AU]:KR
State or Province Name (full name) [Some-State]:Daejeon
Locality Name (eg, city) []:Seogu
Organization Name (eg, company) [Internet Widgits Pty Ltd]:myHome
Organizational Unit Name (eg, section) []:myHome
Common Name (e.g. server FQDN or YOUR name) []:shlee.myHome
Email Address []: shlee@myHome.com

Please enter the following 'extra' attributes
to be sent with your certificate request
A challenge password []: (엔터로 건너뛰기)
An optional company name []: (엔터로 건너뛰기)

# server.csr 생성
```

추가로 입력하는 부분은 적절히 입력하고 넘어가면 됩니다.

* 개인키 password 제거
	* 개인키의 패스워드가 있다면, 아파치 구동 시마다 입력해야하기 때문에 편의상 제거를 합니다.
	* 제거해도 SSL 에는 문제 없으니 걱정하지 않아도 됩니다.

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
Signature ok
subject=C = KR, ST = Daejeon, L = Seogu, O = myHome, OU = myHome, CN = shlee.myHome, emailAddress = shlee@myHome.com
Getting Private key

# server.crt 생성
```

* 인증서 확인

```bash
$ ls -l server*

$ openssl x509 -in server.crt -text
```


## Apache SSL 적용
* apache2 설치

```bash
$ sudo apt install apache2
```

* SSL 모듈 enable

```bash
$ sudo a2enmod ssl
# 서비스 재시작하라는 내용, 나중에 할 것이기 때문에 계속 진행
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

![](https://images.velog.io/images/iseunghan/post/3dc91cb4-c422-406f-9a26-b331519464f1/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202022-03-28%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%201.47.25.png)

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


## localhost 접속
* `https://localhost` 접속

![](https://images.velog.io/images/iseunghan/post/d574fe50-5902-42cc-827a-c017e1100dd5/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202022-03-28%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%201.52.55.png)

CA(인증기관)에서 인증을 받은 것이 아니라, 자가 서명 인증서 이기 때문에 브라우저가 경고를 띄우지만 보안 접속은 됩니다.

`Advaced...` -> `Accept the Risk and Continue` 를 클릭하면 아래와 같습니다.

![](https://images.velog.io/images/iseunghan/post/80e6fc07-86ff-42c4-a80d-e5a07c64896c/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202022-03-28%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%201.54.48.png)


* 인증서 확인
	* 주소창 옆 좌물쇠 모양을 클릭 -> `Connection not secure` -> `More Information`

![](https://images.velog.io/images/iseunghan/post/53352d7c-fe7e-4565-8648-e6ca816fd8a0/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202022-03-28%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%201.55.36.png)

우리가 발급한 자체 서명 인증서 정보를 볼 수 있습니다.


---

## REFERENCES
* [리눅스 자체서명 SSL 인증서 생성 - 제타위키](https://zetawiki.com/wiki/%EB%A6%AC%EB%88%85%EC%8A%A4_%EC%9E%90%EC%B2%B4%EC%84%9C%EB%AA%85_SSL_%EC%9D%B8%EC%A6%9D%EC%84%9C_%EC%83%9D%EC%84%B1)
* [HTTPS와 SSL 인증서 - 생활코딩](https://opentutorials.org/course/228/4894)
* [WEBDIR :: Ubuntu 우분투 Apache(아파치) SSL 적용](https://webdir.tistory.com/228)
* [세상의 모든 기록 :: Ubuntu 아파치(Apache) OpenSSL 적용](https://all-record.tistory.com/189)

감사합니다.
