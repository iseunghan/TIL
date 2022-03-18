## Linux의 systemd (System Daemon)을 관리하는 명령어 systemctl

-   기존 서비스 종료 & 비활성화(심볼릭 링크 삭제)

```
$ sudo systemctl stop (서비스 이름)
$ sudo systemctl disable (서비스 이름)
```

-   서비스 등록(심볼릭 링크 생성)
    -   (따로 service 파일을 관리하고 싶을 때)

```
# 'A-*'로 시작하는 서비스 모두 심볼릭 링크 생성
sudo ln -s /home/pi/modbus-rpi-master/A-*.service /etc/systemd/system/
```

-   ls -l 명령어로 심볼릭 링크를 확인할 수 있다.

```
$ ls -l /etc/systemd/system/A-*.service
```

-   service enable (서비스 활성화)

```
$ sudo systemctl enable A-*
```

-   service start (서비스 시작)

```
$ sudo systemctl start A-*
```

-   service status (상태 확인)

```
$ sudo systemctl status A-*
```

-   service restart (서비스 재시작)

```
$ sudo systemctl restart A-Test.service
```
