### for 문
* java와 동일하다
* for 문 내부 조건은 생략이 가능하다

```js
let num = 0;

for(let i = 0; i < 10; i++) {
  num += 1;
}

console.log(num);	// 10
```

* 변수 선언, 조건 생략
```js
let i = 0;
let num = 0;

for(; ; i++) {
  num += 1;
  
  if(num === 10) break;
}

console.log(num);	// 10
```

### for-Each 문

* array 객체만 사용 가능
* `callback`으로 세가지 매개변수를 받는다.
    * `currentValue` : 현재 값
    * `index` : 현재 인덱스
    * `array` : forEach()를 호출한 배열


```js
let arr = [
  {name : "name1", age : 20},
  {name : "name2", age : 30}
];

arr.forEach( function (value, index, arr) {
	console.log(index, value, arr[index]);
});
```

실행결과

![](https://images.velog.io/images/iseunghan/post/ee989d8c-7ea8-43a7-aeaf-43781175d8bd/image.png)


### for...of
* `array 객체, String, Map, Set..` 사용할 수 있다.

```js
let arr = [
  {name : "name1", age : 20},
  {name : "name2", age : 30}
];

for (i of arr) {
	console.log(i);
}


let str = "abc";

for (i of str) {
	console.log(i); 
}
```

실행결과

![](https://images.velog.io/images/iseunghan/post/6106d4f5-e997-44ae-bd9c-b72d14d54d18/image.png)


### for...in
* key 값이 변수로 할당됩니다.

```js
let arr = [
  {name : "name1", age : 20},
  {name : "name2", age : 30}
];

for (i in arr) {
  console.log(i, arr[i]);
}
```

실행결과
![](https://images.velog.io/images/iseunghan/post/55873932-de5b-435d-b092-295e07758fcc/image.png)
