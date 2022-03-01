# 삽입 정렬 (Insertion Sort)

### 삽입 정렬 (Insertion Sort)

삽입 정렬은 **2 번째 원소부터** 마지막 원소까지 순차적으로, 해당 원소부터 **1 번째 원소**까지 비교를 하며 삽입해 나가는 정렬입니다.

\


> 시간 복잡도 : O(N^2)\
> 공간 복잡도 : O(N)\
> **안정** 정렬

\


### 과정

GIF로 이해하는 삽입정렬

![](https://blog.kakaocdn.net/dn/bvMJmX/btrlGITg7LC/2BXKqARwwVsAaRa2JwtQj1/img.gif)



내가 그린 삽입정렬

![](https://blog.kakaocdn.net/dn/dqLAqN/btrlIl4ewby/udGPJrhnBa0TGvYmoW2qkk/img.png)

1. 2 번째 원소부터 시작하여 마지막 원소까지 순차적으로 정렬을 진행합니다.
2. 1번 과정에서 i 번째 원소부터 시작했다면, i 번째부터 1 번째 원소까지 비교를 하며 삽입해 나아가는 정렬입니다.
3. 앞에서 이미 정렬을 완료했기 때문에 비교하는 과정에서 앞 원소가 더 이상 크지 않다면 종료해도 됩니다. (아래에서 자세히 설명)

\


### Code

* 1번 과정을 코드로 나타내면 아래와 같습니다.

```
for(int i=1; i<arr.length; i++)
```

\


* 2번 과정을 코드로 나타내면 아래와 같습니다.

```
int temp;
int prev = i-1;
while(prev >= 0 && arr[prev] > arr[prev+1]) {
	// 앞 원소가 더 크다는 뜻입니다.
    temp = arr[prev+1];
    arr[prev+1] = arr[prev];
    arr[prev] = temp;
}
```

\


위 코드를 아래와 같이 좀 더 **간결하게** 바꿀 수 있습니다.

```
int temp = arr[i];
int prev = i-1;

while(prev >= 0 && arr[prev] > temp) {
    arr[prev+1] = arr[prev];
    prev--;
}
arr[prev+1] = temp;
```

\


* 3번 과정은 위 2번 과정의 while문 두 번째 조건입니다.

![](https://blog.kakaocdn.net/dn/PD3bo/btrlFbaBbgs/hlDGJKOGkEJ9M6sEZVhbP0/img.png)

4 번째 숫자인 **11**을 정렬하려고 하니, 이미 앞에서 정렬을 한 상태이기 때문에 굳이 맨 앞까지 원소를 비교할 필요가 없습니다.

\


### 전체 코드

```
public void insertion_sort(int[] arr) {
    int temp = 0, prev = 0;
    // 1부터 마지막 원소까지 순회
    for(int i=1; i<arr.length; i++) {
    	temp = arr[i];
        prev = i - 1;
        // 첫 번째 원소까지 비교하면서 삽입해 나갑니다.
        while(prev >= 0 && arr[prev] > temp) {
            arr[prev + 1] = arr[prev];
            prev--;
        }
        arr[prev + 1] = temp;	// 현재 prev + 1 위치에 temp값에 넣었던 arr[i]값을 넣어줍니다.
    }
}
```

\
