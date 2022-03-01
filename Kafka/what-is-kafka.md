# Kafka란 무엇인가?
Kafka는 `메세지 - 큐`이다. 분산환경에 특화되어 있는 특징을 가지고 있다.

구성요소는 `Producer, Broker, Consumer, Topic..`이 있다.

## Producer
Producer는 Broker에게 메세지를 던지는 역할을 한다.

## Broker
Broker는 메세지들을 큐에 담아서 보관하는 역할을 한다.

## Consumer
Consumer는 Broker에게 Producer가 던진 메세지를 받는 역할을 한다.

## Topic
그렇다면 Broker와 Consumer는 여러 Producer들이 던지는 메세지들을 보관하고 사용할까?

바로 이 Topic이라는 개념덕분에 해결이 가능하다.

Producer가 메세지를 Topic과 함께 던져주면, Broker가 해당 Topic에 메세지를 보관하고 있다가 Topic이 필요한 Consumer가 오면 메세지를 주는 형식이다.


---
# REFERENCES
[Apache Kafka](https://kafka.apache.org/documentation/)
