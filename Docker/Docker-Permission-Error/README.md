## 도커 권한 문제

일반 유저가 도커 명령어를 사용할 때마다 Permission Denied가 떠서 sudo를 사용해야하는 불편함이 있습니다. 이때 해결방법은 여러가지가 있습니다. 제가 알아본 2가지 방법에 대해서 알아보겠습니다.

### 현재 사용자를 그룹에 추가

현재 로그인 된 유저를 docker 그룹에 추가하는 것으로 sudo 권한 없이 실행할 수 있습니다.

```bash
sudo usermod -aG docker $USER
logout # 로그아웃 후 다시 로그인 후 적용
```

하지만 위 방법은 써드파티(jenkins 등) 유저에 대해서는 적용이 어려웠습니다.

### docker.sock

Docker CLI(도커 커맨드들)는 결국 docker.sock을 통해서 명령을 실행합니다. 그래서 docker.sock의 권한을 수정해주면 됩니다.

다만 이 방법은 보안에 취약할 수 있으니 실제 서비스에는 사용하지 마시길 바랍니다.

```bash
sudo chmod 666 /var/run/docker.sock
```

또는 group 소유자 변경

```bash
sudo chown root:docker /var/lib/docker.sock
```

### gid를 맞추는 방법
(공부중..)
    

### REFERENCES
- https://github.com/occidere/TIL/issues/116
- https://blog.dasomoli.org/tag/var-run-docker-sock/