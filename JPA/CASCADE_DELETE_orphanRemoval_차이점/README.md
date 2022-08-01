# CascadeType.DELETE, orphanRemoval 차이점

## 목표

`CASCADE.DELETE` 와 `orphanRemoval`의 차이점에 대해서 알아보겠습니다.

## 테스트용 엔티티

```java
public class Parent {

	@OneToMany(mappedBy = "parent")
	private List<Child> children = new ArrayList<>();
}

public class Child {
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Parent parent;
}
```

## CASCADE.DELETE

```java
@OneToMany(mappedBy = "parent", cascade = CascadeType.DELETE)
private List<Child> children = new ArrayList<>();
```

- 상황 1. children.remove(child)
    - 이때, child는 `DETACH 상태`가 된다. 즉, 고아가 된다. (부모가 없이 여전히 `DB에 남아있다`.)
- 상황 2. parentRepository.delete(parent)
    - 이때, parent에 속한 모든 child는 함께 삭제된다.

## orphanRemoval = true

```java
@OneToMany(mappedBy = "parent", orphanRemoval = true)
private List<Child> children = new ArrayList<>();
```

- 상황 1. children.remove(child)
    - 이때 리스트에서 제거된 child를 `고아라고 인식하고 DB에서 제거`한다.
- 상황 2. parentRepository.delete(parent)
    - 이때, parent에 속한 모든 child는 함께 삭제된다.

## 차이점

- 공통
    - 부모를 삭제하면 자식도 함께 삭제
- CASCADE.DELETE
    - 자식을 리스트에서 제거하면 DB에서 제거 안시킴.
- orphanRemoval
    - 자식을 리스트에서 제거하면 DB에서도 제거함.
