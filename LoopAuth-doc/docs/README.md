---
home: true
heroText: LoopAuth
tagline: 精简、轻量、更细粒度的Java权限框架
actionText: 快速上手 →
actionLink: /doc/
features:
- title: 精简轻量
  details: 一次引入,简单配置,开箱即用
- title: 细粒度
  details: 在RBAC的基础上叠加ABAC的属性鉴权(后续版本发布)
- title: Redis
  details: 独立集成Redis,便于缓存区分
footer: MIT Licensed | Copyright © 2022-present | Author is Sober
---

### 抛弃繁琐的权限框架配置，甚至无需配置`yml`即可简单启动

```java
// 你可以这样
@GetMapping("/islogin")
public String isLogin(){
    // 验证是否登录
    LoopAuthFaceImpl.isLogin();
    return "已经登录";
}
// 也可以这样
// 登录才可访问
@LoopAutoCheckLogin
@GetMapping("/islogin")
public String isLogin(){
    return "已经登录";
}
```