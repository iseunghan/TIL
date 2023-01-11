> 기존 influxDB를 이관해야하는 일이 생겼습니다. 차근차근 방법을 알아보도록 하겠습니다.

# 데이터 백업 [[🔗](https://docs.influxdata.com/influxdb/v2.6/migrate-data/migrate-oss/)]
먼저 기존 influxDB에 쌓인 데이터를 백업하도록 하겠습니다.

```bash
influxd inspect export-lp \
  --bucket-id 12ab34cd56ef \
  --engine-path ~/.influxdbv2/engine \
  --output-path path/to/export.gz
  --start 2022-01-01T00:00:00Z \
  --end 2022-01-31T23:59:59Z \
  --compress
```
각 옵션들의 의미는 다음과 같습니다.
- bucket id: 옮길 버킷의 ID
- engine path: 보통 `~/.influxdbv2/engine`에 위치 ([참조](https://docs.influxdata.com/influxdb/v2.6/reference/internals/file-system-layout/?t=Linux#file-system-layout))
- output path: 데이터를 어느 위치에 export 할지
- start: 데이터 시작 시간
- end: 데이터 종료 시간
- compress: 데이터를 Gzip으로 압축

# 데이터 복원 [[🔗](https://docs.influxdata.com/influxdb/cloud/write-data/developer-tools/line-protocol/#write-line-protocol-from-a-file)]
위에서 백업한 데이터 파일을 새로 세팅한 influxDB에 복원시키겠습니다.
```bash
influx write \
-b bucket_name \
-f data_filename \
--org-id organization_id \
--format lp \
-t influxdb_token
```
각 옵션들의 의미는 다음과 같습니다.
- -b: 데이터를 복원시키고자 하는 버킷명
- -f: 복원할 데이터 파일명
- —org-id: organization id
- —format: 위에서 line Protocol 형식으로 데이터를 백업했기 때문에 lp 형식 지정
- -t: 발급받은 influxdb token

데이터가 잘 복원되었는지 `influxDB UI`를 통해 확인해보면 잘 복원이 되었음을 알 수 있습니다.


# + 데이터 저장 위치 변경하기
데이터 이관뿐만 아니라, 추가적으로 influxDB 데이터를 마운트 시킨 디스크에 쌓이도록 설정하고 싶었습니다. 해당 작업도 차근차근 해보도록 하겠습니다.

기존 경로는 `/var/lib/influxdb/data` 에 저장이 되어있을 것입니다.

### influxDB 중지

```bash
systemctl stop influxdb
```

### 변경할 디렉토리 생성

새 디렉토리를 생성하고 influxdb가 접근할 수 있도록 권한을 부여해줍니다.

```bash
mkdir -p /new_dir/influxdb
sudo chown -R influxdb:influxdb /new_dir/influxdb
```

### config.toml 수정

influxDB 설정파일에서 데이터가 저장되는 위치를 변경시키도록 하겠습니다.

```bash
vi /etc/influxdb/config.toml

bolt-path = "/data_dir/influxdb/influxd.bolt"
engine-path = "/data_dir/influxdb/engine"
```

- `bolt-path`: BoltDB의 저장 위치입니다. BoltDB는 key, value 저장소로, organization, user 정보, UI data, REST resources 외 다양한 key-value를 저장하고 있습니다.
- `sqlite-path`: SQLite 데이터베이스 파일이 저장되는 공간입니다. influxdb의 메타데이터 또는 주석 정보들을 저장합니다. (기본적으로 bolt-path와 동일한 경로를 사용합니다.)
- `engine-path`: influxDB가 모든 TSM(Time-Structure Merge Tree) 데이터를 디스크에 저장하는 영구 스토리지 엔진 파일의 경로입니다.

이 외 추가적으로 설정하고 싶으시면 아래를 참조해주시길 바랍니다.  
[InfluxDB configuration options](https://docs.influxdata.com/influxdb/v2.6/reference/config-options/?t=TOML)

### influxDB 재시작

```bash
systemctl start influxdb
```


감사합니다.