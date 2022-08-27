---
title: 鉴权Or登录验证看看呢
---

# 鉴权Or登录验证

## 实现`PermissionInterface`接口

- 要实现角色/权限代码的鉴权，就需要获取当前登录账户的角色列表、权限代码列表
- 需要手动实现`PermissionInterface`接口并注入

```java
public class PermissionInterfaceImpl implements PermissionInterface {
    @Override
    public Set<String> getPermissionSet(String userId, String loginType) {
        // 这里只做演示 所以写死 根据业务查询数据库或者其他操作
        return new HashSet<String>() {
            {
                add("user-*");
            }
        };
    }

    @Override
    public Set<String> getRoleSet(String userId, String loginType) {
        // 这里只做演示 所以写死 根据业务查询数据库或者其他操作
        return new HashSet<String>() {
            {
                add("user");
            }
        };
    }
}
```

### 自动注入

- 在`PermissionInterface`的实现类上加上`@Component`注解即可

```java
@Component
public class PermissionInterfaceImpl implements PermissionInterface {
    ...
}
```

### 手动注入

- 保证项目启动时执行下面语句即可

```java
LoopAuthStrategy.setPermissionInterface(new PermissionInterfaceImpl());
```

## LoopAuthVerifyMode

- `LoopAuthVerifyMode`是一个枚举类，里面包含了`OR`、`AND`、`NON`
- `OR`代表或
- `AND`代表与
- `NON`代表非
- 所有需要填写`LoopAuthVerifyMode`的方法，不填写则默认`AND`

## 代码鉴权

- 所有需要登录的方法都会内部默认先调用一次`LoopAuthFaceImpl.isLogin();`，即`checkByRole`等方法使用时你无需手动调用`isLogin`

```java
// 判断是否登录
LoopAuthFaceImpl.isLogin();
// 判断用户是否拥有user角色
LoopAuthFaceImpl.checkByRole("user")
// 判断用户是否拥有user-**或者order-get中权限代码
LoopAuthFaceImpl.checkByPermission(LoopAuthVerifyMode.OR, "user-**","order-get")
```

## 注解鉴权

- 所有需要登录的方法都会默认执行`@LoopAutoCheckLogin`，即`@LoopAuthPermission`上无需使用`@LoopAutoCheckLogin`
- 注解可以加在类上来避免重复工作
- 注解鉴权需要依赖拦截器

### 注入拦截器

```java
@Component
public class LoopAuthMvcConfigure implements WebMvcConfigurer {
    /**
     * 注册LoopAuth 的拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器
        registry.addInterceptor(new LoopAuthAnnotationInterceptor()).addPathPatterns("/**");
    }
}
```

### 用注解拦截

```java
// 验证登录
@LoopAutoCheckLogin
// 判断用户是否拥有user-**或者order-get中权限代码
@LoopAuthPermission(value= {"user-**","order-get"},mode = LoopAuthVerifyMode.OR)
@GetMapping("/testPermission")
public String testPermission(){
    return "检测成功";
}

// 验证登录
@LoopAutoCheckLogin
// 判断用户是否拥有user角色
@LoopAuthRole(value="user")
@GetMapping("/testRole")
public String testRole(){
    return "检测成功";
}   
```

## 路由鉴权

- 同时我们也提供了`servlet`的过滤器路由鉴权方式
- 过滤器无法获取到业务方法或者类上的注解，因此需要自行在过滤器写代码鉴权
- `spring`的全局异常处理也获取不到过滤器的异常，因此需要自行在过滤器配置
- `isIntercept`参数代表是否需要拦截，`route`为当前路由
- `LoopAuthHttpMode`为请求类型的枚举，包括`GET`、`PUT`、`POST`或`ALL`等等所有常见的请求类型
- `addInclude`或`addExclude`不填写`LoopAuthHttpMode`则默认`ALL`

```java
/**
 * 注册 [LoopAuth 全局过滤器] 此优先级高于  注解  如这里报错就不在进入注解
 */
@Bean
public LoopAuthServletFilter getSaServletFilter() {
    return new LoopAuthServletFilter()
        .addInclude("/**")
        .addExclude("/test/login", LoopAuthHttpMode.GET)
        // 认证函数: 每次请求执行
        .setLoopAuthFilter((isIntercept,route) -> {
            if (isIntercept){
                // 拦截代码块
                LoopAuthFaceImpl.isLogin();
            }else {
                // 放行代码块
            }
        })
        // 异常处理函数：每次认证函数发生异常时执行此函数
        .setLoopAuthErrorFilter(e -> {
            e.printStackTrace();
            return e.getMessage();
        });
}

```


## 模糊匹配

在框架鉴权时，会进行模糊匹配

如：用户用于`user-**`的权限代码时，`user-add`、`user-del`等都可以通过验证