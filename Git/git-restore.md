### git restore

깃 버전 2.23부터는 수정된(`modified`) 파일을 되돌리는 명령어가 변경되었습니다.

```bash
# before
$ git checkout -- <file>

# after
$ git restore <file>
```

스테이징에 올라간 (`git add 로 추가한`) 파일들은 아래 명령어로 되돌릴 수 있습니다.

```bash
# before
$ git reset HEAD <file>

# after
$ git restore --staged <file>
```
