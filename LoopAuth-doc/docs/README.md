---
home: true
heroText: LoopAuth
tagline: 精简、轻量、更细粒度的Java权限框架
actionText: 快速上手 →
actionLink: /doc/
features:
- title: 精简轻量
  details: 一次引入,简单配置,开箱即用
- title: Redis
  details: 独立集成Redis,便于缓存区分
- title: 动态鉴权
  details: 可以自由进行动态权限变更
- title: 细粒度
  details: 可以针对当前请求的属性进行匹配拦截,如时间等
footer: MIT Licensed | Copyright © 2022-present | Author is Sober
---

### 抛弃繁琐的权限框架配置，甚至无需配置`yml`即可简单启动

```java
// 你可以这样
@GetMapping("/islogin")
public String isLogin(){
    // 验证是否登录
    LoopAuthSession.isLogin();
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

### 特别鸣谢

- 本文档由Netlify支持：[![netlify](https://www.netlify.com/v3/img/components/full-logo-light.svg)](https://www.netlify.com)