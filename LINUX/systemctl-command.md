# systemctl disable, enable, start
* 기존 서비스 종료 & 비활성화(심볼릭 링크 삭제)
```bash
$ sudo systemctl stop (서비스 이름)
$ sudo systemctl disable (서비스 이름)
```

* 서비스 등록(심볼릭 링크 생성)
	* (따로 service 파일을 관리하고 싶을 때)
```bash
# 'A-*'로 시작하는 서비스 모두 심볼릭 링크 생성
sudo ln -s /home/pi/modbus-rpi-master/A-*.service /etc/systemd/system/
```

* ls -l 명령어로 심볼릭 링크를 확인할 수 있다.
```sh
$ ls -l /etc/systemd/system/A-*.service

```

* service enable (서비스 활성화)
```sh
$ sudo systemctl enable A-*
```

* service start (서비스 시작)
```sh
$ sudo systemctl start A-*
```

* service status (상태 확인)
```sh
$ sudo systemctl status A-*
```
