# LoopAuth

一款JavaWeb鉴权框架，同时支持RBAC (角色鉴权校验)、ABAC (规则流引擎鉴权)， 
并提供会话管理等功能。
对SpringBoot3以及SpringWebFlux都可支持。

<p align="center">
	<img src="https://img.shields.io/badge/LoopAuth-v2.1.0-brightgreen.svg">
	<img src="https://img.shields.io/github/license/mashape/apistatus.svg">
</p>

目前包含如下功能：
- 注解鉴权
- 代码鉴权
- 登录功能
- 有/无状态登录
- Redis登录业务存储分离
- ABAC权限

## 写在前面

项目发布初期，希望大家多多点点`Star`
- [Gitee](https://gitee.com/lucky-color/loop-auth)
- [GitHub](https://github.com/ChangZou/LoopAuth)
- [官方文档](https://loopauth.sobercoding.com)

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

## SpringBoot快速使用

- `${version}`请查看[版本历史](https://loopauth.sobercoding.com/doc/preamble/version.html)，请使用最新正式版，且版本与其余拓展最好保持一致
- SpringBoot3选择后者依赖即可

### 添加依赖

```xml
<!-- LoopAuth的Springboot插件 -->
<dependency>
  <groupId>com.sobercoding</groupId>
  <artifactId>LoopAuth-spring-boot-starter</artifactId>
  <version>${version}</version>
</dependency>
<!-- LoopAuth的Springboot3插件 -->
<dependency>
  <groupId>com.sobercoding</groupId>
  <artifactId>LoopAuth-spring-boot3-starter</artifactId>
  <version>${version}</version>
</dependency>
```

### 配置文件

> 快速体验可以无需配置`yml`文件，完成其他配置直接启动即可

- 登录规则及持久层的配置需要开启`token-persistence`配置项
- `access-modes`为从请求获取`token`的位置，同时登录成功或登录续期操作也会主动返回`token`到`HEADER`或`COOKIE`中

```yml
loop-auth:
  session:
    time-out: 5 # token有效时间(单位秒)  默认24小时
    token-name: token # token名称  默认LoopAuth
    mutualism: true # token共生 默认false 开启则 账号可以同时在线
    exclusion: true # 互斥登录， 默认false  开启则 多人操作相同设备登录 会互相挤掉线（只有在 mutualism=true 时此配置才有效）
    token-persistence: true # token持久化配置  默认false
    max-login-count: 3 # 同一账号最大登录数量 默认1  -1代表不限制
    renew: false # 自动续签 默认true 每次isLogin操作，会自动刷新token有效期
    access-modes: # token获取方式 默认[COOKIE,HEADER]顺序获取。即COOKIE中获取到鉴权成功，则不前往HEADER获取
      - HEADER
      - COOKIE
    secret-key: secret # 默认LoopAuth Token生成密钥
    token-persistence-prefix: tokenPrefix # 默认LoopAuthToken token持久层存储的前缀
    login-id-persistence-prefix: loginIdPrefix # 默认LoopAuthLoginId LoginId持久层存储的前缀
    cookie: # cookie配置
      remember: true # 是否长久有效 默认false 开启则cookie的有效时间为time-out,关闭则网页关闭后cookie丢失
      domain: localhost # 域 默认服务端域
      path: /test # 默认false '/' 路径
      http-only: true # 默认false 是否允许js操作
      secure: true # 默认false 是否只在https安全协议传输
      # 安全等级  Strict (完全禁止第三方Cookie,跨站点时,任何情况下都不会发送Cookie) 默认不限制
      # Lax 不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
      # None 不限制
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
    LoopAuthSession.login("1");
    return "登录成功";
  }

  @GetMapping("/islogin")
  public String isLogin(){
    // 验证是否登录
    LoopAuthSession.isLogin();
    return "已经登录";
  }


  @GetMapping("/out")
  public String loginOut(){
    // 验证是否登录
    LoopAuthSession.isLogin();
    // 注销登录
    LoopAuthSession.logout();
    return "注销成功";
  }

}
```

## ABAC鉴权

- 以下所有代码均为示例代码，可根据步骤编写并运行，理解后可自定义规则
- [示例项目](https://gitee.com/Chang_Zou/LoopAuth-abac-demo)


### 实现四个属性模型的构建

- 这里的属性模型需要自行根据业务实现值的获取

#### 主题属性

> 访问者属性，如用户性别、地域等

```java
@Component
public class UserModel {

    private final AccountService accountService;

    UserModel(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 用户id
     */
    public String getId() {
        return LoopAuthSession.getTokenModel().getLoginId();
    }

    /**
     * 登录状态
     */
    public boolean getIsLogin() {
        try {
            LoopAuthSession.isLogin();
        } catch (LoopAuthLoginException e){
            return false;
        }
        return true;
    }

    /**
     * 获取用户性别
     */
    public Short getSex() {
        return accountService.getById(getId()).getSex();
    }

}
```

#### 环境属性

> 环境属性，如当前时间、访问者IP等

```java
@Component
public class ContextualModel {

    /**
     * 时间
     */
    public Long getNowTime() {
        return System.currentTimeMillis();
    }
}
```

#### 操作属性

- LoopAuthHttpMode为请求类型的枚举，包括GET、PUT、POST或ALL等等所有常见的请求类型

> 操作属性，如删除、新增、查询等

```java
@Component
public class ActionModel {

    /**
     * 请求方式
     */
    public LoopAuthHttpMode getLoopAuthHttpMode() {
        Optional<ServletRequestAttributes> servletRequestAttributes = Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = servletRequestAttributes
                .orElseThrow(() -> new LoopAuthParamException(LoopAuthExceptionEnum.PARAM_IS_NULL, "ServletRequestAttributes Failed to get"))
                .getRequest();
        return LoopAuthHttpMode.valueOf(request.getMethod());
    }

}
```

#### 访问对象属性

> 访问对象属性，这里是和业务耦合的，如被访问的数据ID区间、被访问的数据类型等

```java
@Component
public class ResObjectModel {

    public String getId() {
        Optional<ServletRequestAttributes> servletRequestAttributes = Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = servletRequestAttributes
                .orElseThrow(() -> new LoopAuthParamException(LoopAuthExceptionEnum.PARAM_IS_NULL, "ServletRequestAttributes Failed to get"))
                .getRequest();

        return request.getParameter("id");
    }
}
```

### 实现`AbacInterface`接口

- LoopAuthHttpMode为请求类型的枚举，包括GET、PUT、POST或ALL等等所有常见的请求类型

```java
/**
 * @author Sober
 */
@Component
public class AbacInterFaceImpl
        extends AbacWrapper<ActionModel, ContextualModel, ResObjectModel, UserModel>
        implements AbacInterface<ActionModel, ContextualModel, ResObjectModel, UserModel> {

    /**
     * 注入
     */
    AbacInterFaceImpl(ActionModel actionModel,
                      ContextualModel contextualModel,
                      UserModel userModel,
                      ResObjectModel resObjectModel) {
        this.action = actionModel;

        this.contextual = contextualModel;

        this.subject = userModel;

        this.resObject = resObjectModel;

        // 访问者主题属性 通常为账户属性
        Subject<UserModel> subject = Subject
                // id规则初始化
                .init("loginId",
                        UserModel::getId,
                        (v, r) -> v.get().equals(r))
                // 性别规则初始化
                .structure("sex",
                        UserModel::getSex,
                        (v, r) -> {
                            Short sex = Short.valueOf(r);
                            return v.get().equals(sex);
                        });

        // 环境属性 通常为非账户的属性 如时间、ip等
        Contextual<ContextualModel> contextual = Contextual
                // 时间规则初始化
                .init("time",
                        ContextualModel::getNowTime,
                        (v, r) -> {
                            long time = v.get();
                            long roletime = Long.parseLong(r);
                            return roletime > time;
                        }
                );

        // 操作类型 通常问请求方式GET 或者操作code等
        Action<ActionModel> action = Action
                // 操作类型规则初始化
                .init("model",
                        ActionModel::getLoopAuthHttpMode,
                        (v, r) -> {
                            LoopAuthHttpMode vMode = v.get();
                            return Arrays.stream(r.split(","))
                                    .map(LoopAuthHttpMode::valueOf)
                                    .anyMatch(item -> item.equals(vMode));
                        });

        // 访问对象
        ResObject<ResObjectModel> resObject = ResObject
                .init("idByGetMode",
                        ResObjectModel::getId,
                        (v, r) -> r.equals(v.get())
                );

        // 载入规则
        policyWrapper = PolicyWrapper.<ActionModel, ContextualModel, ResObjectModel, UserModel>builder()
                .subject(subject)
                .action(action)
                .contextual(contextual)
                .resObject(resObject);
    }

    /**
     *  获取一个或多个路由/权限代码所属的 规则
     * @param route 路由
     * @param loopAuthHttpMode 请求方式
     * @return 去重后的集合
     */
    @Override
    public Set<Policy> getPolicySet(String route, LoopAuthHttpMode loopAuthHttpMode) {
        // 这里只做演示，自行编写的时候，请根据自己存储abac规则的方式查询获取
        Set<Policy> set = new HashSet<>();
        // 根据路由地址及请求方式查询 插入

        // 获取账号列表请求，需要验证当前用户的性别类型需要为1
        if ("/time".equals(route) && loopAuthHttpMode.equals(LoopAuthHttpMode.GET)){
            set.add(new Policy()
                            // 规则名称
                            .setName("abac时间测试")
                            // 只有男性可以访问此接口
//                            .setSubjectProperty("sex", "1")
                            // 时间必须在 1677764476507 之前
                            .setContextualProperty("time", "1677764476507")
            );
        }

        // 获取账号列表请求，需要验证当前用户的性别类型需要为1
//        if ("/account".equals(route)){
//            set.add(new Policy()
//                            // 规则名称
//                            .setName("resful")
//                            // id符合才可执行
//                            .setSubjectProperty("loginId", "1585296315037323265")
//                            // 拥有GET,DELETE操作类型的权限
//                            .setActionProperty("model", "GET,DELETE")
//                            // 只能访问id为1585296315037323265的目标
//                            .setResObjectProperty("idByGetMode", "1585296315037323265")
//            );
//        }
        return set;
    }

    /**
     * 规则获取
     */
    @Override
    public PolicyWrapper<ActionModel, ContextualModel, ResObjectModel, UserModel> getPolicyWrapper() {
        return policyWrapper;
    }

}
```


#### 自动注入

- 在`AbacInterface`的实现类上加上`@Component`注解即可

```java
@Component
public class AbacInterFaceImpl...
```

#### 手动注入

- 保证项目启动时执行下面语句即可

```java
AbacStrategy.setAbacInterface(new AbacInterFaceImpl());
```

### 注入拦截器

```java
@Component
public class LoopAuthMvcConfigure implements WebMvcConfigurer {
    /**
     * 注册LoopAuth 的拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // abac拦截器
        registry.addInterceptor(new InterceptorBuilder().Abac().builder()).addPathPatterns("/**");
    }
}
```

### 创建`Controller`测试一下

- 可以更改`setSupplierMap()`中的返回值、或请求类型理解

```java
    @GetMapping("/time")
    public String time(){
        return "检测成功";
    }
```

### 注解方式拦截

- 我们并不建议使用注解鉴权，ABAC的特点是鉴权粒度更细，但是ABAC的灵活修改也很重要。我们希望你可以在无需重启项目的前提下完成ABAC的权限变更
- 注解鉴权ABAC的方式与上面其实大同小异
- 唯一的区别就是无需实现`AbacInterface`接口
- 你只需要在接口上添加注解，如下

```java
    @CheckAbac(name = "abac时间测试", value = {
        @AbacProperty(name = "time", prop = PropertyEnum.CONTEXTUAL, value = "1677764476507")
    })
    @GetMapping("/time")
    public String time(){
        return "检测成功";
    }
```

- 然后注册注解拦截器

```java
@Component
public class LoopAuthMvcConfigure implements WebMvcConfigurer {
    /**
     * 注册LoopAuth 的拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器
        registry.addInterceptor(new InterceptorBuilder().annotation().builder()).addPathPatterns("/**");
    }
}
```