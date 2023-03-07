---
title: 看看版本
---

# 版本历史

## 2.1.4.Bate.1（测试版本）

- ABAC使用方式改版

## 2.1.1

- 修复内部不受控的异常
- 细化ABAC异常信息
- 增加ABAC注解鉴权能力
- 修改ABAC校验规则的返回值，验证成功true，非法false。原先无返回值，直接抛出异常

## 2.1.0

> 该版本为正式版，此后版本重心将放在 修复bug、优化代码 Or 新功能添加  
> 不会轻易改变现有的使用方式

- 修复HandlerMethod的静态资源访问时强转错误
- 拆分注解拦截方法到Session Rbac
- 修复javafx引用问题
- 修复当前请求路径获取异常问题
- 修复不使用持久化时空指针问题
- 适配WebFlux

## 2.0.1

- 修复Policy定义 未初始化的属性规则时空指针问题

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