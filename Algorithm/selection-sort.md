# 선택 정렬 (Selection So

### 선택 정렬 (Selection Sort)

정렬하여 원소를 배치할 자리를 **미리 정해놓고**, 해당 자리에 넣을 원소를 **선택**하는 알고리즘입니다.

개인적으로 삽입 정렬과 헷갈린 정렬,, 선택 정렬은 넣을 자리를 미리 지정하고, 삽입 정렬은 1 번째부터 n번째 원소를 앞의 원소와 비교하며 삽입해 나가는 차이가 있다. (헷갈림..)

> 시간 복잡도 : O(N^2)\
> 공간 복잡도 : O(N)\
> **불안정** 정렬

\


### 과정

![](https://blog.kakaocdn.net/dn/dAvRsj/btrlGbHaeDf/GwlhVSgPp3xq5rV7r6W561/img.gif)

\


![](https://blog.kakaocdn.net/dn/lbi35/btrlD2LdLjp/J3pHqMDTl1JNUUfKQaRym0/img.png)

1. 먼저 1 번째 자리에 올 원소를 선택할 것입니다. 첫 번째 원소부터 마지막 원소까지 탐색하여 가장 작은 수와 1 번째 수와 위치를 교환합니다.
2. 이제 2 번째 자리에 올 원소를 선택할 것입니다. 두 번째 원소부터 마지막 원소까지 탐색하여 그 중 제일 작은 수와 위치를 교환합니다.
3. &#x20;위와 동일
4. 위와 동일
5. 정렬이 완료됐습니다.

\


### Code

* 먼저 정할 위치의 범위는 **첫 번째** 위치부터 **마지막 - 1** 위치까지 입니다.
* 그 다음 해당 위치에 넣을 원소는 **i + 1**번째부터 **마지막** 원소까지 입니다.

```
int temp, min_idx;

for(int i=0; i<arr.length - 1; i++) {
    min_idx = i;
    
    for(int j=i + 1; j<arr.length; j++) {
        if( arr[min_idx] > arr[j])	min_idx = j;
    }
    temp = arr[i];
    arr[i] = arr[min_idx];
    arr[min_idx] = temp;
}
```

* 가장 작은 원소의 위치를 기억하기 위해 **min\_idx** 라는 변수를 이용합니다.
* 각 범위에 가장 작은 원소를 찾았으면, 위치를 교환해주면 됩니다.
