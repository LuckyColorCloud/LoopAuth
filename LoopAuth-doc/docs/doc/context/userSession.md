---
title: 会话模型及操作
---

# 会话操作类UserSession

## 数据模型

```java
public class UserSession{
    /**
     * 用户id
     */
    private String loginId;
    /**
     * token列表
     */
    private Set<TokenModel> tokens;
    /**
     * 当前登录的tokenModel
     */
    private TokenModel tokenModelNow;
}
```

## 会话操作

- 仅在开启持久化`token-persistence: true`时有效
- 操作都是直接对持久层的修改或读取

### 获取指定`LoginId`的会话

> 并不建议用户直接通过此方法获取`UserSession`，  
> 可以通过`LoopAuthFaceImpl`的`getUserSession()`及`getUserSessionByLoginId("1")`  
> 来获取当前或是指定`LoginId`的`UserSession`

- 可以用来查看某用户的所有登录会话
- 这里被`gainUserSession()`执行过的`UserSession`，等同于从`LoopAuthFaceImpl`处获取的`UserSession`

```java
// 调用gainUserSession()前 必须保证setLoginId()
UserSession userSession = new UserSession();
userSession.setLoginId("X")
    .gainUserSession();
```

### 清空当前会话的所有登录

- 可以用来使某用户全部登录失效

```java
UserSession userSession = new UserSession();
userSession.setLoginId("X")
    .gainUserSession()
    .remove();
```

### 删除指定Token值的TokenModel

- 可以用来使某用户指定登录失效

```java
// removeToken参数可以是多个token的值
UserSession userSession = new UserSession();
userSession.setLoginId("X")
    .gainUserSession()
    .removeToken("token1","token2")
    .removeToken("token3");
```

### 加入一个TokenModel进入会话

- 可以用来生成非用户的会话

> 例如你的项目对外提供天气查询接口  
> 客户需要先获取token，然后带token才可请求接口
> 但是你需要自己定义token生成方法，并且`LoginId`必须区别于普通用户，比如可以填写`weather:1`

```java
TokenModel tokenModel = new TokenModel()
    .setLoginId("1")
    .setValue("token")
    .setFacility("sad")
    .setXXX()...;
UserSession userSession = new UserSession();
userSession.setLoginId("X")
    .gainUserSession()
    .setToken(tokenModel);
```

## TokenModel

- 此类仅作为一个实体使用
- 数据模型如下：

```java
public class TokenModel {
    /**
     * token值
     */
    private String value;
    /**
     * 用户id
     */
    private String loginId;
    /**
     * 设备类型
     */
    private String facility = "def";
    /**
     * 设备名称
     */
    private String facilityName = "def";
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * token有效期(单位毫秒)
     */
    private long timeOut;
}
```

