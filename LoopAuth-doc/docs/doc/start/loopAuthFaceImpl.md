---
title: LoopAuthFaceImpl干啥用
---

# 全局工具类`LoopAuthFaceImpl`

> `LoopAuthFaceImpl`是LoopAuth框架全局的操作类，用于登录/注销/权限判断等操作

## 方法介绍
- 所有需要登录的方法都需要提前验证`isLogin()`

### 会话操作

|                                     方法 | 返回        |                               描述 |
|:--------------------------------------| :---------- |:--------------------------------|
|login(String loginId) | void        |                             登录方法 |
| login(String loginId, String facility) | void        |                         带设备的登录方法 |
|                               logout() | void        |                             注销登录 |
|                        getTokenModel() | TokenModel  |                    获得当前会话Token模型 |
|                       getUserSession() | UserSession |                       获取当前用户所有会话 |
|                              isLogin() | void        |                     是否登录 |
|                           loginRenew() | void        |           登录续期,刷新当前会话的TokenModel |

### 权限判断

- 权限判断需要实现`PermissionInterface`接口

|                                                                                 方法 | 返回        |                               描述 |
|:----------------------------------------------------------------------------------| :---------- |:--------------------------------|
|                checkByRole(LoopAuthVerifyMode loopAuthVerifyMode, String... roles) | void        |                             登录方法 |
|    checkByPermission(LoopAuthVerifyMode loopAuthVerifyMode, String... permissions) | void        |                         带设备的登录方法 |

## 参数解释

| 参数名      | 描述         |
| ----------- | ------------ |
| loginId     | 登录Id       |
| facility    | 设备名称     |
| roles       | 角色列表     |
| permissions | 权限代码列表 |

## 相关内容
- [UserSession](../context/userSession.md#usersession)
- [TokenModel](../context/userSession.md#tokenmodel)
- [角色Or权限代码鉴权](../start/auth.md#tokenmodel#角色or权限代码鉴权)