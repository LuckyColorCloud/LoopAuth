---
title: 全局工具类
---

# 全局工具类

> `LoopAuthSession`是LoopAuth框架Session的全局的操作类，用于登录/注销/权限判断等操作

## `LoopAuthSession` 会话工具类

| 方法                                                          | 返回        | 描述                     |
|:------------------------------------------------------------| :---------- |:-----------------------|
| login(String loginId)                                       | void        | 登录方法                   |
| login(String loginId, String facility)                      | void        | 带设备的登录方法               |
| login(String loginId, String facility, String facilityName) | void        | 带设备及设备名称的登录方法          |
| logout()                                                    | void        | 注销登录                   |
| forcedOfflineByToken(String... tokens)                      | void        | 注销指定token的登录           |
| logoutAll()                                                 | void        | 注销当前会话所属loginId所有登录    |
| forcedOfflineByLoginId(String loginId)                      | void        | 注销指定loginId所有登录        |
| getTokenModel()                                             | TokenModel  | 获得当前会话Token模型          |
| getTokenModelByTokenValue(String token)                     | TokenModel  | 获得指定token的模型           |
| getUserSession()                                            | UserSession | 获取当前用户所有会话             |
| getUserSessionByLoginId(String loginId)                     | UserSession | 获取指定用户所有会话             |
| isLogin()                                                   | void        | 是否登录                   |
| loginRenew()                                                | void        | 登录续期,刷新当前会话的TokenModel |

## `LoopAuthRbac` RBAC鉴权工具类

- 权限判断需要实现`PermissionInterface`接口

| 方法                                                                                | 返回        | 描述                |
|:----------------------------------------------------------------------------------| :---------- |:------------------|
| checkByRole(LoopAuthVerifyMode loopAuthVerifyMode, String... roles)               | void        | 鉴权角色              |
| checkByPermission(LoopAuthVerifyMode loopAuthVerifyMode, String... permissions)   | void        | 鉴权权限代码            |
| checkByRole(String... roles)                                                      | void        | 鉴权角色(LoopAuthVerifyMode默认AND)       |
| checkByPermission(String... permissions)                                          | void        | 鉴权权限代码(LoopAuthVerifyMode默认AND)     |

## 参数解释

| 参数名      | 描述     |
| ----------- |--------|
| loginId     | 登录Id   |
| facility    | 设备类型   |
| facilityName | 设备名称   |
| roles       | 角色列表   |
| permissions | 权限代码列表 |
| loopAuthVerifyMode | 鉴权匹配规则 |

## 相关内容
- [UserSession](../context/userSession.md)
- [TokenModel](../context/userSession.md#tokenmodel)
- [角色Or权限代码鉴权](../start/auth.md#tokenmodel#角色or权限代码鉴权)