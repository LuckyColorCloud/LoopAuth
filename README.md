# LoopAuth

## 写在前面

项目发布初期，希望大家多多点点`Star`
- [Gitee](https://gitee.com/lucky-color/loop-auth)
- [GitHub](https://github.com/ChangZou/LoopAuth)
- [官方文档](https://loopauth.sobercoding.com)

## 介绍

LoopAuth一款低侵入、精简、轻量、细粒度的Java Web权限管理框架

目前包含如下功能：
- 注解鉴权
- 代码鉴权
- 登录功能
- 有/无状态登录
- Redis登录业务存储分离

## 交流群
- QQ群：460304421
- 微信群：请扫下图二维码，备注`LoopAuth加群`

  <img src="LoopAuth-doc/docs/.vuepress/public/img/mywx.jpg" alt="img" />

## 抛弃繁琐的权限框架配置，甚至无需配置`yml`即可简单启动

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

## SpringBoot快速使用

### 添加依赖

```xml
<!-- LoopAuth的Springboot插件 -->
<dependency>
    <groupId>com.sobercoding</groupId>
    <artifactId>LoopAuth-spring-boot-starter</artifactId>
    <version>${version}</version>
</dependency>
```

### 配置文件

> 快速体验可以无需配置`yml`文件，完成其他配置直接启动即可

- 登录规则及持久层的配置需要开启`token-persistence`配置项
- `access-modes`为从请求获取`token`的位置，同时登录成功或登录续期操作也会主动返回`token`到`HEADER`或`COOKIE`中

```yml
loop-auth:
  time-out: 5 # token有效时间(单位秒)  默认24小时
  token-persistence: true # token持久化配置  默认false
  token-name: token # token名称 同时也作为 默认LoopAuth
  mutualism: true # token共生 默认false 开启则 账号可以同时在线
  exclusion: true # 互斥登录， 默认false  开启则 多人操作相同设备登录 会互相挤掉线（只有在 mutualism=true 时此配置才有效）
  max-login-count: 3 # 同一账号最大登录数量 默认1  -1代表不限制
  renew: false # 自动续签 默认true 每次isLogin操作，会自动刷新token有效期
  access-modes: # token获取方式 默认[COOKIE,HEADER]顺序获取。即COOKIE中获取到鉴权成功，则不前往HEADER获取
    - HEADER
    - COOKIE
  secret-key: secret # 默认LoopAuth Token生成密钥
  token-persistence-prefix: tokenPrefix # 默认LoopAuthToken token持久层存储的前缀
  login-id-persistence-prefix: loginIdPrefix # 默认LoopAuthLoginId LoginId持久层存储的前缀
  cookie-config: # cookie配置
    remember: true # 是否长久有效 默认false 开启则cookie的有效时间为time-out,关闭则网页关闭后cookie丢失
    domain: localhost # 域 默认服务端域
    path: /test # 默认'/' 路径
    http-only: true # 默认false 是否允许js操作
    secure: true # 默认false 是否只在https安全协议传输
    # 安全等级  Strict (完全禁止第三方Cookie,跨站点时,任何情况下都不会发送Cookie)
    # Lax 不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
    # None 不限制  默认参数
    same-site: Strict
```

### 简单使用
- 新建`Controller`类

```java
@RestController
public class DemoController {
    @GetMapping("/login")
    public String register(){
        // 登录方法
        LoopAuthFaceImpl.login("1");
        return "登录成功";
    }

    @GetMapping("/islogin")
    public String isLogin(){
        // 验证是否登录
        LoopAuthFaceImpl.isLogin();
        return "已经登录";
    }


    @GetMapping("/out")
    public String loginOut(){
        // 验证是否登录
        LoopAuthFaceImpl.isLogin();
        // 注销登录
        LoopAuthFaceImpl.logout();
        return "注销成功";
    }

}
```