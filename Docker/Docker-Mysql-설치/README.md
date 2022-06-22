## docker-compose 다운로드

-   아래에서 최신 버전 확인!
    -   [Releases · docker/compose](https://github.com/docker/compose/releases)

```
sudo curl -L https://github.com/docker/compose/releases/download/v2.4.1/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
```

-   권한 설정

```
sudo chmod +x /usr/local/bin/docker-compose
```

-   설치 확인

```
docker-compose --version
```

[Docker, Docker-Compose 설치하기 - Ubuntu 20.04 LTS

Docker 설치 실행환경: Ubuntu 20.04 LTS 업데이트 및 HTTP 패키지 설치 $ sudo apt update $ sudo apt-get install -y ca-certificates \\ curl \\ software-properties-common \\ apt-transport-https \\ gnupg \\ ls..

iseunghan.tistory.com](https://iseunghan.tistory.com/408)

## docker-compose 디렉토리 생성

### 최종적인 디렉토리 구조

```
docker-mysql
├── docker-compose.yml
├── .env
└── mysql
    ├── conf.d
    │   └── my.cnf
    ├── data
    └── initdb.d
        ├── create_table.sql
        └── load_data.sql

4 directories, 4 files
```

-   docker-compose.yml : 컨테이너 실행할 때, 더욱 쉽게 설정하기 위한 파일
-   conf.d : MySQL 설정 파일이 위치
-   data : MySQL 데이터가 저장되는 위치
-   initdb.d : Docker 컨테이너가 최초 실행 시 불러올 `*.sh` , `*.sql` 등 파일 이름의 사전순서대로 실행됨.

### 1\. 임의로 `docker-mysql` 이라는 작업 디렉토리 생성

```
mkdir docker-mysql && cd docker-mysql
```

### 2\. DB 계정 정보 생성

`.env` 생성

```
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_ROOT_PASSWORD={root_pw}
MYSQL_DATABASE={db명}
MYSQL_USER=user
MYSQL_PASSWORD={user_pw}
```

### 3\. 각 폴더 및 파일 생성

```
mkdir -p mysql/conf.d
mkdir -p mysql/data
mkdir -p mysql/initdb.d
```

`conf.d` 안에는 `my.cnf` 를 아래와 같이 작성합니다.

```
[client]
default-character-set = utf8mb4

[mysql]
default-character-set = utf8mb4

[mysqld]
character-set-client-handshake = FALSE
character-set-server           = utf8mb4
collation-server               = utf8mb4_unicode_ci
```

`init.d` 안에는 `create.sql` 과 `load_data.sql` 를 생성합니다.

-   create.sql (예시)
-   `CREATE TABLE user( username VARCHAR(25) PRIMARY KEY, password VARCHAR (50) not null );`
-   load\_data.sql (예시)
-   `INSERT INTO user(username, password) VALUES ('user', 'pass');`

### 4\. docker-compose.yml

```
version: "3"

services:
  db:
    container_name: docker-mysql
    image: mysql
    ports:
      - 3306:3306
    volumes:
      - ./mysql/conf.d:/etc/mysql/conf.d
      - ./mysql/data:/var/lib/mysql
      - ./mysql/initdb.d:/docker-entrypoint-initdb.d
    env_file: .env
    environment:
      TZ: Asia/Seoul
    restart: always

# 정확한 작성법은 아래 링크 참조
# https://docs.docker.com/compose/compose-file/
```

-   `image`: mysql latest 버전 사용 (특정 버전 사용하고 싶으면 → mysql:0.0.1 이런식으로 작성)
-   `ports`: 컨테이너 포트 3306 개방
-   `volumes`: Docker 컨테이너의 파일을 사용자 시스템의 파일에 연결
-   `TZ`: DB Time Zone 설정

## docker-compose로 실행!

### 실행

```
$ docker-compose -f docker-compose.yml up -d
```

-   `-f` : 별도의 docker-compose 파일을 사용하고 싶을 때
-   `-d` : 백그라운드로 실행

### 종료

```
$ docker-compose down
```

## REFERENCE

-   [https://int-i.github.io/sql/2020-12-31/mysql-docker-compose/](https://int-i.github.io/sql/2020-12-31/mysql-docker-compose/)