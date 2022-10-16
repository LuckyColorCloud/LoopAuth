---
title: 看看版本
---

# 版本历史

## 2.0.0

- 结构重构
- 拆分core，使RBAC、Session、ABAC三个模块独立
- 具体使用方法的变更，请参考文档
- 1.X版本弃用


## 1.0.5

- 修复过滤器路由匹配`ALL`问题
- 修复续期时默认登录规则判断规则的逻辑
- `LoopAuthFaceImpl`增加获取指定的`UserSession`和`TokenModel`方法
- `LoopAuthFaceImpl`增加指定用户或Token的强制离线方法
- 更新`UserSession`文档说明

## 1.0.2

- 优化了isLogin()触发条件，内部需要登陆的方法会自行调用（包括代码/注解鉴权）
- login()登录方法增加facilityName(终端设备名称)
- 添加了过滤器路由鉴权的方式

## 1.0.1

- 修订`LoopAuth-redis`中`jedis`版本,解决与`Spring-redis`的版本冲突

## 1.0.0

- 正式版发布
- 注解鉴权
- 代码鉴权
- 登录功能
- 有/无状态登录
- Redis登录业务存储分离