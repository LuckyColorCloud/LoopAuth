---
title: 试试ABAC鉴权
---

# 试试ABAC鉴权

- 以下所有代码均为示例代码，可根据步骤编写并运行，理解后可自定义规则
- [示例项目](https://gitee.com/Chang_Zou/LoopAuth-abac-demo)

## 实现`AbacInterface`接口

- LoopAuthHttpMode为请求类型的枚举，包括GET、PUT、POST或ALL等等所有常见的请求类型

```java
public class AbacInterFaceImpl implements AbacInterface {

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
        if (route.equals("/test/abac") && loopAuthHttpMode.equals(LoopAuthHttpMode.GET)){
            set.add(new Policy()
                    // 规则名称
                    .setName("test")
                    // 规则中的属性名称 及 属性值 用于后续进行 规则匹配校验
                    .setProperty("loginId", "2")
            );
        }
        return set;
    }
}
```


### 自动注入

- 在`AbacInterface`的实现类上加上`@Component`注解即可

```java
@Component
public class AbacInterFaceImpl implements AbacInterface {
    ...
}
```

### 手动注入

- 保证项目启动时执行下面语句即可

```java
AbacStrategy.setAbacInterface(new PermissionInterfaceImpl());
```

## 初始化ABAC鉴权规则

- 需要保证项目启动时 执行以下代码
- 以下代码以匹配`loginId`为例
- 请根据自己需求更改

```java
AbacStrategy.abacPoAndSuMap = new AbacPolicyFunBuilder()
        // 自定义登录id校验的鉴权规则
        .setPolicyFun("loginId",
                // 创建规则校验及获取当前值的方式
                new AbacPoAndSu()
                        // 创建校验方式  value为当前值即setSupplierMap提供的值
                        // rule为规则的值即 Policy setProperty 的值
                        .setMaFunction((value, rule) -> {
                            // 当前用户id需要与规则匹配才可访问  否则 抛出异常
                            if (!value.equals(rule)){
                                throw new LoopAuthPermissionException(LoopAuthExceptionEnum.NO_PERMISSION);
                            }
                        })
                        // 获得value方式
                        .setSupplierMap(() -> "2")
        ).build();


```

## 注入拦截器

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

## 创建`Controller`测试一下

- 可以更改`setSupplierMap()`中的返回值、或请求类型理解

```java
    @GetMapping("/test/abac")
    public String abac1(){
        return "检测成功";
    }
```
