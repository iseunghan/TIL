# 계수 정렬 (Counting Sort)

## 계수 정렬 (Counting Sort)

카운팅 정렬은 시간복잡도가 _**𝚶(𝑛)**_ 으로 속도가 빠른 알고리즘이다.  퀵 정렬(Quick Sort), 합병 정렬(Merge Sort) 의 평균 시간복잡도는 _**𝚶(nlogn)**_ 인데 카운팅 정렬은 시간복잡도가 _**𝚶(𝑛)**_ 으로 속도가 아주 우수한 알고리즘이다.

\


카운팅 정렬을 코드로 하나하나 뜯어서 보자.

\


먼저, 배열은 세가지를 선언 해준다.

```
        int[] array = new int[100]; 	//수열의 원소 : 100개
        int[] counting = new int[101];	//수의 범위 : 0~100
        int[] sorted = new int[100];	//정렬 될 배열
```

\


arr 배열에 각각 담겨 있는 값을 counting 배열의 인덱스에 해당하는 값을 증가 시켜 준다.

그냥 말그대로 해당 숫자일때, counting 원소값을 증가 시켜 주는 것이다. (아래 그림을 참고하자.)

![](https://blog.kakaocdn.net/dn/5XbiK/btqNtil8IEc/dEJo6v7TX0gfOJW81llN00/img.png)

```
for(int i=0; i<arr.length; i++) {
	counting[ arr[i] ]++;
}
```

\


counting의 각 원소의 값은 arr값이 정렬되서 sorted 배열에 들어갈 인덱스 값이 된다.

그림으로 설명하자면,

![](https://blog.kakaocdn.net/dn/b8E83E/btqNtht0EUm/0aNDbrV5h3CTkQbKjSlU40/img.png)

![](https://blog.kakaocdn.net/dn/daw3jj/btqNqekD0Tb/Xm0oTgug94LylstpLOxbj0/img.png)

counting 의 각 원소의 값을 counting\[i] = counting\[i] + counting\[i-1]; 로 나타낸다.

코드로 나타내면 아래와 같다.

```
for(int i=1; i<counting.length; i++) {
	counting[i] += counting[i-1];
}
```

\


\


앞에서 했던 과정을 거치면, arr값에 해당하는 counting의 원소값은 sorted의 인덱스가 된다.

![](https://blog.kakaocdn.net/dn/zvvXH/btqNqeE1cqP/tosSmsniF5XhkxT9oAXmY0/img.png)

***

![](https://blog.kakaocdn.net/dn/XkaKh/btqNqde5kHr/8IkcrgdPetr6byhTU8x860/img.png)

***

![](https://blog.kakaocdn.net/dn/cakQtC/btqNBf9w4Im/y7YoqudpsOvPrke8u8yLx1/img.png)

***

![](https://blog.kakaocdn.net/dn/lTWmz/btqNqeryOsI/aWJ1KuANsmnbTYNl7klnS0/img.png)

***

![](https://blog.kakaocdn.net/dn/d9y3H8/btqNq0TPzbG/IkgpIIt0tkbm2GhXhIlNIK/img.png)

arr\[i]의 값이 counting의 index값이고, counting의 값을 -1 시킨 값을 sorted의 index로 사용해 해당 index 값에 arr\[i] 값을 넣어주면 된다. 코드로 나타내면 아래와 같다.

```
for (int i = array.length - 1; i >= 0; i--) {
            int value = array[i];
            counting[value]--;
            result[counting[value]] = value;
        }
```

\


이제 출력을 해보면,

> 1 2 3 4 5

순서대로 정렬이 된 것을 볼 수가 있다.

\


\


만약 수의 범위를 알고 있고, 출력만 하면 된다면 위 방법 보다, 더 간단한 코드가 있다.

```
public class Counting_Sort {
    public static void main(String[] args) {
        int[] arr = new int[10]; // 크기를 10으로 정했다면, 수의 범위도 0~10까지 제한해야한다.

        for (int i = 0; i < arr.length; i++) {
            arr[(int)(Math.random() * 11)]++; // Math.random() * 11 로 범위를 제한 시켰다.
        }

        for (int i = 0; i < arr.length; i++) {
            while (arr[i] > 0) {
                System.out.print(i + " ");
                arr[i]--;
            }
        }
    }
}
```

\


입력 받은 (랜덤으로 주어지는) 수에 해당하는 인덱스의 값을 증가시키고, while문으로 통해 인덱스가 1이상인 인덱스를 출력시켜주는 간단한 매커니즘이다.

\


오늘 _**Counting Sort**_ 를 알아보았는데, 성능이 평소 자주 사용하던 퀵 정렬(Quick Sort) 보다 훨씬 뛰어나고, 코드도 그리 복잡하지 않아서 코딩테스트 문제 같은 시간초과가 나지 않게 하기 위해서 사용하면 좋을 것 같다고 느꼈다.
