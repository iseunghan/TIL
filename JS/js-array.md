## array

#### init
```js
var arr = [0, 1, 2, 3];
```

#### push
* 마지막에 원소를 추가

```js
arr.push(4);

console.log(arr);
// [ 0, 1, 2, 3, 4 ]
```

#### pop()
* 마지막 원소를 제거

```js
arr.pop();	// 4

console.log(arr);
// [ 0, 1, 2, 3 ]
```

#### shift()
* 첫 번째 원소를 제거

```js
arr.shift();	// 0

console.log(arr);
// [ 1, 2, 3]
```

#### unshift()
* 첫 번째에 원소 추가

```js
arr.unshift(10);

console.log(arr);
// [ 10, 1, 2, 3 ]
```

#### splice(start, length)
* 인덱스가 `start` 지점부터 `length`만큼 제거

```js
arr.splice(1, 2);

console.log(arr);
// [ 10, 3 ]
```

#### slice()
* 배열 복제

```js
var copy = arr.slice();

console.log(copy);
// [ 10, 3 ]
```


