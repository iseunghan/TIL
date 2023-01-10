## 이미지 다운 및 설치

[Influxdb - Official Image](https://hub.docker.com/_/influxdb)


> Password는 꼭 6자리 이상!

##### Option 부여:
* -Volume 옵션 제외

```bash
$ docker run -d -p 8086:8086 \
	  --name influxdb \
      -e DOCKER_INFLUXDB_INIT_MODE=setup \
      -e DOCKER_INFLUXDB_INIT_USERNAME=username \
      -e DOCKER_INFLUXDB_INIT_PASSWORD=pass1234 \
      -e DOCKER_INFLUXDB_INIT_ORG=my-org \
      -e DOCKER_INFLUXDB_INIT_BUCKET=test-bucket \
      -e DOCKER_INFLUXDB_INIT_ADMIN_TOKEN=my-secret-token \
      influxdb
```

* -Volume 옵션 포함
```bash
$ docker run -d -p 8086:8086 \
	  --name influxdb \
      -v $PWD/data:/var/lib/influxdb2 \
      -v $PWD/config:/etc/influxdb2 \
      -e DOCKER_INFLUXDB_INIT_MODE=setup \
      -e DOCKER_INFLUXDB_INIT_USERNAME=username \
      -e DOCKER_INFLUXDB_INIT_PASSWORD=pass1234 \
      -e DOCKER_INFLUXDB_INIT_ORG=my-org \
      -e DOCKER_INFLUXDB_INIT_BUCKET=test-bucket \
      -e DOCKER_INFLUXDB_INIT_ADMIN_TOKEN=my-secret-token \
      influxdb
```

#### Volume 옵션에 대해서..
해당 컨테이너를 종료하고, 다시 실행해도 데이터는 남아있습니다.

하지만, 컨테이너를 **삭제**하고 다시 생성하면 데이터는 초기화가 되어 실행될 것입니다.

만약 컨테이너를 삭제하고 다시 생성해도 데이터가 남아있길 원한다면 → 컨테이너 외부(호스트) 저장소와 연결을 해줘야합니다.

이것이 바로 Volume 옵션입니다.

Volume에 대해서는 따로 포스팅하겠습니다.

[Install InfluxDB](https://docs.influxdata.com/influxdb/v2.2/install/?t=Docker#persist-data-outside-the-influxdb-container)


## influx 접속하기

> (외부에서 접속 시) 공식문서 참고: [Install and use the influx CLI](https://docs.influxdata.com/influxdb/v2.2/tools/influx-cli/?t=Linux)

##### 도커 컨테이너 내부로 진입
```bash
$ docker exec -it 컨테이너_이름 bash
```

##### influx CLI 설정 적용
```bash
influx config create --config-name local-config \
  --host-url http://localhost:8086 \
  --org my-org \
  --token my-secret-token \
  --active
  
Active	Name		URL			Org
*	local-config	http://localhost:8086	my-org

```

## REFERENCES
- [Influxdb - Official Image](https://hub.docker.com/_/influxdb)  
- [Install and use the influx CLI](https://docs.influxdata.com/influxdb/v2.2/tools/influx-cli/?t=Linux)  
- [Install InfluxDB](https://docs.influxdata.com/influxdb/v2.2/install/?t=Docker#persist-data-outside-the-influxdb-container)  
