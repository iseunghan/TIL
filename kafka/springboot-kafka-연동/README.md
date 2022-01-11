## dependency 추가
[org.springframework.kafka : spring-kafka](https://mvnrepository.com/artifact/org.springframework.kafka/spring-kafka)

```xml
<dependency>
  	<groupId>org.springframework.kafka</groupId>
	<artifactId>spring-kafka</artifactId>
</dependency>
```

## application.yml
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    template:
    	default-topic: test-topic
    consumer:
      group-id: test-group-1
      enable-auto-commit: true
      auto-offset-reset: latest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      max-poll-records: 1000
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```
* `spring.kafka.bootstrap-servers`:
kafka 클러스터와 연결하기 위한 `host:port` 입니다. 만약 consumer, producer의 특정한 `bootstrap-servers`가 정의되어 있지 않다면 이 설정이 자동 적용됩니다.
* `spring.kafka.template.default-topic`: default Topic을 설정합니다.

* `spring.kafka.consumer`
	
    * `group-id`: consumer가 속하는 group id
    * `enable-auto-commit`: consumer의 offset을 자동으로 커밋할건지 여부
    * `bootstrap-server`: consumer가 연결할 server의 host:port 정보
    * `auto-offset-reset`: kafka에 초기 오프셋이 없거나 현재 오프셋이 서버에 더 이상 없는 경우 리셋
    	* latest: 최근으로 오프셋 리셋
       * ealist: 맨 처음으로 오프셋 리셋
       * none: 아무것도 안함
    * `key-deserializer`/`value-deserializer`: 데이터를 주고 받을 때 필요한 `key/value` 역직렬화, **JSON**으로 주고 받고 싶다면 `JsonSerializer` 사용


## CustomProducer
```java
package me.iseunghan.kafkadocker.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.DestinationTopic;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Service
public class CustomProducer {

    private static final Logger logger = LoggerFactory.getLogger(CustomProducer.class);

    private static String TOPIC;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public CustomProducer(@Value("${spring.kafka.template.default-topic}") String topic) {
        TOPIC = topic;
    }

    public void send(String message) {
        kafkaTemplate.send(TOPIC, message);
        logger.info("Send Message : {}", message);
    }
}

```


## CustomConsumer
```java
package me.iseunghan.kafkadocker.service;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CustomConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CustomConsumer.class);

    @KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Payload String payload) {
        logger.info("CONSUME TOPIC : {}", topic);
        logger.info("CONSUME PAYLOAD : {}", payload);
        System.out.println("CUNSOMUER : " + topic + " - " + payload + " // msg consume!");
    }
}

```


	