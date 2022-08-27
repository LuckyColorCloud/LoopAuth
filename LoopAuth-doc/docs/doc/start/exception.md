---
title: 鉴权失败异常咋处理
---

# 鉴权失败异常处理

## 代码块异常处理

- 在使用代码鉴权的时候可以使用该方法

```java
try {
    LoopAuthFaceImpl.isLogin();
}catch (LoopAuthException e){
    return e.getMessage();
}
```

## 全局拦截处理

- 使用Springboot的`@RestControllerAdvice`拦截全局异常

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常拦截  注解鉴权或代码鉴权 报错后 可在这里处理返回信息
     * 这里的返回类型可以改成自己的
     */
    @ExceptionHandler
    public String handlerException(LoopAuthException e) {
        return e.getMessage();
    }

}
```

## 过滤器异常处理

```java
.setLoopAuthErrorFilter(e -> {
    if (e instanceof LoopAuthException){
        System.out.println(((LoopAuthException) e).getCode());
        System.out.println(e.getMessage());
    }
    // 此处请根据你自己的返回类型 返回异常code、msg，或处理异常等操作
    return XXX;
});
```