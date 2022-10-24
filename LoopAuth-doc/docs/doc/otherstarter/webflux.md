---
title: WebFlux 使用
---

# WebFlux 使用

> 在`WebFlux`中使用`LoopAuth`的方式，与在传统`Spring`应用中的使用方式大部分都相同。  
> 本文主要讲解不同的部分，如未做解释的用法，则无需做出改变。

## 添加依赖

- `${version}`请查看[版本历史](../preamble/version.md)，请使用最新正式版，且版本与其余拓展最好保持一致

```xml
<!-- LoopAuth的Springboot插件 -->
<dependency>
    <groupId>com.sobercoding</groupId>
    <artifactId>LoopAuth-spring-boot-webflux-starter</artifactId>
    <version>${version}</version>
</dependency>
```

## 如何拦截注解或ABAC鉴权

> `WebFlux`应用不存在拦截器，所有拦截操作需要通过过滤器

### 注入过滤器

- 这里的过滤器使用方式与`SpringBoot`基本一致
- 区别在于认证函数多提供了一个`exchange`参数，即`ServerWebExchange`类

```java
    /**
     * 注册 [LoopAuth 全局过滤器]
     */
    @Bean
    public LoopAuthWebFluxFilter getLoopAuthServletFilter() {
        return new LoopAuthWebFluxFilter()
                .addInclude("/**")
                .addExclude("/test/login", LoopAuthHttpMode.GET)
                // 认证函数: 每次请求执行
                .setLoopAuthFilter((isIntercept,route,exchange) -> {
                    if (isIntercept){
                        // ...这里写你的拦截方式
                    }
                })
                .setLoopAuthErrorFilter(e -> Result.error(e.getMessage()));
    }
```

### 怎么实现注解或ABAC鉴权

- 实现注解或ABAC鉴权只需要在 拦截器`if (isIntercept)`中调用指定的方法即可
- ABAC鉴权如下：

```java
LoopAuthAbac.check(route, exchange.getRequest().getMethodValue());
```
- 注解鉴权如下：

```java
// 获取请求对应的HandlerMethod
HandlerMethod handlerMethod = requestMappingHandlerMapping
        .getHandler(exchange).cast(HandlerMethod.class).share().block();
// 登录注解鉴权
CheckSessionAnnotation.checkMethodAnnotation.accept(handlerMethod.getMethod());
// RBAC注解鉴权
CheckSessionAnnotation.checkMethodAnnotation.accept(handlerMethod.getMethod());
```

- `requestMappingHandlerMapping`对象通过需要声明获取

```java
@Resource
private RequestMappingHandlerMapping requestMappingHandlerMapping;
```
