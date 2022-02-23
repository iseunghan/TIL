# [node.js] Bcrypt를 이용해 패스워드 암호화하기

* bcrypt 설치

```bash
$ npm install bcrypt
```

---

## async 비동기 (추천)

```js
const bcrypt = require('bcrypt');
const saltRounds = 10;
const password = "pass1234"
```

#### 패스워드를 암호화하여 Hash 값 얻어내기

* 여기서 `saltRounds`는 `Key Stretching`을 의미합니다
    
```js
bcrypt.genSalt(saltRounds, function(err, salt) {
        console.log("Salt : ", salt);
        if (err != null) {
                // error 처리
        }
        bcrypt.hash(password, salt, function(err, hash) {
                console.log("Hash : ", hash);
                if (err != null) {
                        // error 처리
                }
        });
});
```

* 결과

![](https://images.velog.io/images/iseunghan/post/68145c36-6d02-4a3c-a1fc-237cbd11c19a/image.png)

<br>

#### 패스워드 검증하기
```js
bcrypt.compare(password, hash, function(err, result) {
	console.log("true"); 
  	
  	if (err != null) {
     	console.log("false");
    }
}
```

* 결과
   * 올바른 패스워드와 다른 패스워드를 넣었을 때

![](https://images.velog.io/images/iseunghan/post/a1255e12-417b-4d33-b8cb-d226c247631c/image.png)

---

## Promise 함수
#### 패스워드 암호화

* 일반 함수 표현
```js

bcrypt.hash(password, saltRounds)
        .then(function(hash) {
                console.log("hash : ", hash);
        });

```


* 화살표 함수 표현
```js
const token = (password, rounds) =>
        bcrypt.genSalt(rounds)
		  .then((salt) => {
                console.log("Salt : ", salt);
                return bcrypt.hash(password, salt);
        })
        .then((hash) => {
                console.log("Hash : ", hash);
        });

token(password, saltRounds);
```

<br>

#### 패스워드 검증

* 일반 함수 표현
```js
bcrypt.compare(password, hash)
	.then(function (result) {
  		if(result) {
          	console.log("true");
        } else {
          	console.log("false");
        }
	});
```
* 화살표 함수 표현
```js
var valid_Password = (password, hash) =>
  bcrypt.compare(password, hash)
	.then((result) =>{
  		if(result) {
          	console.log("true");
        } else {
          	console.log("false");
        }
	});
```

---

## Sync

#### 패스워드 암호화

* 직접 salt를 발급해 hash값 얻기
```js
const salt = bcrypt.genSaltSync(saltRounds);
const hash = bcrypt.hashSync(password, salt);
```

* 자동으로 salt를 발급 후 hash값 얻기
```js
const hash = bcrypt.hashSync(myPlaintextPassword, saltRounds);
```

#### 패스워드 검증
```js
bcrypt.compareSync(password, hash);
```

# REFERENCES

[eun_dev님 블로그](https://programmer-eun.tistory.com/125)

[bcrypt github](https://github.com/kelektiv/node.bcrypt.js#sync)

[ki_blank님 블로그 - 화살표 함수](https://velog.io/@ki_blank/JavaScript-%ED%99%94%EC%82%B4%ED%91%9C-%ED%95%A8%EC%88%98Arrow-function)

감사합니다.
