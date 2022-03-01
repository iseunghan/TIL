## Docker Compose로 Kafka 실행하기

`Kafka`를 띄우기 위해서는 `Zookeeper`를 함께 띄워줘야합니다. (Kafka 클러스터내의 브로커들을 Zookeeper가 관리해주기 때문입니다.)

`Kafka를` 띄우고, 또 `Zookeeper`를 띄워서 둘이 연결을 시켜줘야하는데,, (귀찮아..) 이럴 때 사용하는게 바로 `Docker Compose`!!

## Docker Compose 설치

* `docker-compose` 설치

```bash
$ curl -L "https://github.com/docker/compose/releases/download/1.11.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```

* `docker-compose` 실행 권한 부여

```bash
$ chmod +x /usr/local/bin/docker-compose
```

## kafka with Zookeeper 이미지

가장 유명한 [wurstmeister/kafka-docker](https://github.com/wurstmeister/kafka-docker)에서 이미지를 받도록 하겠습니다.

```bash
$ git clone https://github.com/wurstmeister/kafka-docker
```

## 사용법
![](https://images.velog.io/images/iseunghan/post/a3ea4ffd-7f13-408e-bb2b-259e18208b85/image.png)

클러스터 실행
* `docker-compose up -d`

브로커 추가
* `docker-compose scale kafka=3`

클러스터 종료
* `docker-compose stop`

### 기본적으로 `docker-compose.yml`을 적용
![](https://images.velog.io/images/iseunghan/post/12f1f088-fc58-478f-8756-bd28978215c1/image.png)

그냥 `docker-compose up`을 실행하게 되면 `docker-compose.yml`이 적용이 됩니다.
각 브로커는 재실행될 때마다 새 포트 번호와 브로커 ID를 부여받기 때문에, 특정 포트와 브로커 ID를 사용하고 싶으면 `docker-compose-single-broker.yml`을 적절하게 수정하여 사용하면 된다고 하는 것 같습니다.

저는 로컬 환경에서만 테스트를 하기 위한 목적이므로, `Kafka_HOST_NAME`을 127.0.0.1로 변경하겠습니다. (만약 멀티 브로커 환경일 때에는 외부에서 접근할 수 있는 IP를 넣어주셔야 합니다.)


```yaml
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"
  kafka:
    build: .
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: 127.0.0.1
      KAFKA_CREATE_TOPICS: "test:1:1"
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
```


### docker-compose up
방금 수정한 `docker-compose-single-broker.yml`을 적용하여 up을 해보도록 하겠습니다.

  
```bash
$ docker-compose -f docker-compose-single-broker.yml up
```

![](https://images.velog.io/images/iseunghan/post/066466b0-2433-4389-8f55-175d1869559f/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202022-01-24%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%2011.36.04.png)


자 이제 뭔가 막 실행이 되면서 `kafka + zookeeper`가 실행이 되고 있습니다.


정상적으로 실행되는 것도 확인할 수 있습니다.


![](https://images.velog.io/images/iseunghan/post/cbbc2a2b-5b37-43ab-82d3-362f08784bdc/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202022-01-24%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%2011.36.22.png)

## Kafka Producer, Consumer 연결 테스트

### GET KAFKA
먼저 kafka [공식 사이트](https://www.apache.org/dyn/closer.cgi?path=/kafka/3.1.0/kafka_2.13-3.1.0.tgz)에서 kafka를 다운받습니다.

### 압축 풀기
```bash
$ tar -xzf kafka_2.13-3.1.0.tgz
$ cd kafka_2.13-3.1.0
```

### Topic 생성
```bash
$ bin/kafka-topics.sh --create --topic hello-world --bootstrap-server localhost:9092
```

### Producer
```bash
$ bin/kafka-console-producer.sh --topic hello-world --bootstrap-server localhost:9092
```

### Consumer
```bash
$ bin/kafka-console-consumer.sh --topic hello-world --from-beginning --bootstrap-server localhost:9092
```

### 메세지 전송 테스트
* `producer`에서 메세지 전송
![](https://images.velog.io/images/iseunghan/post/e3f09dac-84b0-4de2-891f-a175eab69e34/image.png)

* `consumer`에서 메세지 수신

![](https://images.velog.io/images/iseunghan/post/3b8e2f51-e322-40ea-bad3-17176c9cf6ff/image.png)



# REFERENCES
[Docker Compose와 버전별 특징](https://meetup.toast.com/posts/277)

[Kafka quickstart](https://kafka.apache.org/quickstart)
