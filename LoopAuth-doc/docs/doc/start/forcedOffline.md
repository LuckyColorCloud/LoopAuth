---
title: 咋强制会话离线
---

# 强制离线

- 强制离线可以通过全局工具类`LoopAuthSession`，或者`UserSession`通过操作
- 这里只介绍`LoopAuthSession`
- [UserSession](../context/userSession.md)相关操作看这里

## 指定loginId所有会话失效

```java
// 直接使用此方法传入loginId即可
LoopAuthSession.forcedOfflineByLoginId("loginId");
```

## 指定token失效

```java
// 直接使用此方法传入token即可
LoopAuthSession.forcedOfflineByToken("token");
// 当然你也可以指定多个token
LoopAuthSession.forcedOfflineByToken("token1","token2");
```