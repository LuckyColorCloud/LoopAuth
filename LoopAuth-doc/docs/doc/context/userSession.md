---
title: 会话模型及操作
---

# 会话模型UserSession

## UserSession

### 数据模型

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

## TokenModel

### 数据模型

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

