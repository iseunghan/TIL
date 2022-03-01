# 퀵 정렬 (Quick Sort)

## Quick Sort

Quick Sort는 real-world 데이터에서 빠르다고 알려져 가장 많이 쓰는 정렬 알고리즘입니다.

pivot이라는 것을 지정하여 pivot 왼쪽으로는 pivot보다 작은 값들을, 오른쪽에는 pivot보다 큰 값들을 재배치하며, 계속하여 분할하여 정렬하는 알고리즘입니다.

Quick Sort는 분할 정복 방법을 통해 정렬합니다.

Merge Sort와 달리 Quick Sort는 비균등하게 분할합니다.

\


> **불안정 정렬**에 속합니다.\
> 시간 복잡도 : O(N^2)\
> 공간 복잡도 : O(N)

\


### 과정

GIF로 이해하는 Quick Sort

![](https://blog.kakaocdn.net/dn/nynFi/btrlCRCeqiw/Cx1UuWauykwFulIrEbqkuk/img.gif)

\


**1. 먼저 배열의 원소들 중 임의로 pivot을 지정한다. (보통 가장 앞 원소를 선택한다.)**

![](https://blog.kakaocdn.net/dn/dQwZ6O/btrlzOl3ZPN/UlPE6EK60ksjprTuzo4I2k/img.png)

* 먼저 **마지막 위치**부터 인덱스를 감소시키면서 피봇보다 **작은 수**를 찾는다. (왜 먼저 작은 수 부터 찾는지 아래에서 설명하겠습니다.)
* **시작 위치 + 1** 부터 인덱스를 증가시키면서 피봇보다 **큰 수**를 찾는다.
* 언제 까지 반복하는가? 발견한 큰 수의 인덱스와 발견한 작은 수의 인덱스가 서로 엇갈렸을 때까지!
  * **"엇갈렸다."** 이게 무슨 말인가요?
  * 아래와 같이 i, j가 엇갈린 경우는 가장 작은 수와 피봇의 위치를 바꿔주면 됩니다. 피봇은 더 이상 정렬할 필요가 없습니다.

```
 10  9  8  7  13
(p)          |(i)
          (j)|
```

\


일단 이 과정들을 무작정 따라하면서 이해해보겠습니다.

\


**1-1. 작은 수를 찾는 과정**

![](https://blog.kakaocdn.net/dn/vnMzj/btrlEIyxvBr/y5W52llY0gFRZC5aevnIB0/img.png)

마지막 원소부터 피봇까지 살피면서 피봇보다 같거나 작은 수를 찾습니다.

(이때 피봇과 같은 수를 찾는 이유는 바로, 조건문 하나를 줄이기 위함입니다.)

코드로 표현하면 이렇습니다.

```
while( pivot < arr[j])	j--;

// 만약 pivot과 같은 것을 찾지 않는다면 아래처럼 됩니다.
while( pivot <= arr[j] && j > pivot_idx )	j--;
```

\


**1-2. 큰 수를 찾는 과정**

![](https://blog.kakaocdn.net/dn/dOKJnn/btrlEt1dXKd/mooJItc7D7qQ3PDKFHsiGk/img.png)

피봇부터 마지막까지 살피면서 피봇보다 큰 수를 찾습니다. 이때는 인덱스가 넘어가지 않도록 주의해줘야겠죠?

```
while( pivot >= arr[i] && i < end)	i++;
```

\


하지만 위 조건은 틀렸습니다. 여기서 주의해야 할 점은 인덱스 i와 j가 서로 엇갈린 상황을 체크해야 하는데 위 조건을 그것을 찾아내지 못합니다. 그러므로 아래와 같이 i와 j가 엇갈렸는지 체크해줘야합니다.

```
while( i < j && pivot >= arr[i] ) 	i++;
```

해당 조건을 주기 위해, 미리 작은 수를 찾아서 j의 값을 찾은 것입니다.

\


2\. 둘의 상태를 살펴보자

![](https://blog.kakaocdn.net/dn/bcfbXv/btrlFKbCKjB/RCb9QXEyFVSWTZ1HAhGAm0/img.png)

지금은 i와 j가 엇갈리기 직전의 상황입니다. 이런 상황에서는 피봇보다 큰 수는 없기 때문에 i와 j의 위치를 서로 바꿔주면 i 위치에 가장 작은 값이 들어가게 될 것이고, 그 i 위치에  해당하는 값과 피봇과 바꿔줍니다.

![](https://blog.kakaocdn.net/dn/ebfzqQ/btrlD3Xbg4c/9HCxqM2TxOTsMpLuUVOpZk/img.png)

\


해당 교환하는 코드를 작성해보면 아래와 같이 됩니다.

```
while( i < j ) {
    while( pivot < arr[j] ) j--;
    while( i < j && pivot >= arr[i] ) i++;
    
    swap(i, j);	// 위치 교환
}

// i와 j가 엇갈렸을 때
swap(pivot, i);
```

결과

![](https://blog.kakaocdn.net/dn/M7ec7/btrlC5Id5sx/szKCPBMmOyRzkVL2Y8H8V0/img.png)

**피봇**을 기준으로 왼쪽에는 피봇보다 작은 수, 오른쪽은 큰 수가 오게 됩니다. (현재는 큰 수가 없음.)

\


이제 피봇 기준으로 절반을 나눠서 다시 퀵 정렬을 해주면 됩니다!

코드로 표현하면 아래와 같이 됩니다!

```
quick_sort( start_idx, i - 1 );	// 피봇보다 작은 수들에 대해서
quick_sort( i + 1, end_idx );	// 피봇보다 큰 수들에 대해서
```

\


### 전체 코드

```
void quick_sort(int start_idx, int end_idx) {
	int pivot = arr[start_idx];
	int i = start_idx;
    int j = end_idx;
    int temp;
    
    while( i < j ) {
    	while( pivot < arr[j] ) j--;
        while( i < j && pivot >= arr[i] ) i++;
        
        temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    arr[start_idx] = arr[i];
    arr[i] = pivot;
    
    quick_sort(start_idx, i - 1);
    quick_sort(i + 1, end_idx);
}
```
