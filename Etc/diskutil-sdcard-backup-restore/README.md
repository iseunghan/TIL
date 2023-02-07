## Disk list 확인하기

-   먼저 `Terminal`을 열어 아래 명령어로 디스크 이름을 확인합니다.

```
$ diskutil list

# 아래는 출력 값
... 생략

/dev/disk4 (external, physical):
   #:                       TYPE NAME                    SIZE       IDENTIFIER
   0:     FDisk_partition_scheme                        *31.9 GB    disk4
   1:             Windows_FAT_32 boot                    268.4 MB   disk4s1
   2:                      Linux                         31.6 GB    disk4s2
```

-   (예시 이미지)

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbQD2TI%2FbtrAUyBjSkc%2FtpsW2KkJsLV6WZar8iHw5k%2Fimg.png)

사용하던 raspberry pi의 sd 카드가 `/dev/disk4`라고 표시됩니다.

## SD 카드 백업 이미지 생성

-   먼저 디스크를 언마운트 시킵니다.

```
$ diskutil unmountDisk /dev/disk4
Unmount of all volumes on disk4 was successful
```

-   아래 명령어를 이용해 SD 카드를 img 파일로 생성합니다.
    -   `if=` 뒤에는 `diskutil list`로 확인했던 SD카드의 이름을 적어주고
    -   `of=` 뒤에는 생성할 이미지의 경로와 이름을 적어주면 됩니다.

```
$ sudo dd if=/dev/disk4 of=/Users/shlee/pi_sd_backup.img

# 저는 of=~/pi_sd_backup.img는 에러로 인해 절대경로를 사용했습니다.
```

명령어를 실행하고 아래 완료가 될때까지 기다리면 됩니다.

-   완료 결과

```
$ sudo dd if=/dev/disk4 of=/Users/shlee/pi_sd_backup.img
62333952+0 records in
62333952+0 records out
31914983424 bytes transferred in 1467.675304 secs (21745262 bytes/sec)
```

## SD 백업 이미지 복원

백업할 때와 거의 동일합니다. 마지막 명령어의 위치만 바꿔주면 됩니다.

-   복원할 디스크를 언마운트 시킵니다.

```
$ diskutil unmountDisk /dev/disk4
Unmount of all volumes on disk4 was successful
```

-   파일 시스템 : `ExFAT`
-   포맷 후 디스크 이름 : `boot`
-   포맷 할 디스크 : `/dev/disk4`

```
$ sudo diskutil eraseDisk ExFAT boot /dev/disk4
```

-   복원할 이미지와 디스크 이름을 명령어로 실행합니다.
    -   백업할 때와 다르게 `if=`, `of=` 변수를 서로 바꿔서 지정해주면 됩니다.

```
$ sudo dd if=/Users/shlee/pi_sd_backup.img of=/dev/disk4
```

## REFERENCE

[Easily Format a SD Card in OSX to FAT32 - Michael Crump](https://www.michaelcrump.net/the-magical-command-to-get-sdcard-formatted-for-fat32/)

-   Terminal이 아닌, 디스크 유틸리티(Disk Utility)로 SD 카드 포맷하기  
    [455\. 라즈베리파이 4 - 맥북(Mac OS)에서 라즈비안(Raspbian) 설치 : 네이버 블로그](https://m.blog.naver.com/pcmola/221867745562)
