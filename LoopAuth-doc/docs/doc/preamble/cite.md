---
title: 咋引入依赖?版本历史?
---

## Maven引入
- 直接通过`pom.xml`文件引入即可
- `${version}`版本请根据下文版本历史选择，推荐使用最新版

```xml

<!-- LoopAuth的Springboot插件 -->
<dependency>
    <groupId>com.sobercoding</groupId>
    <artifactId>LoopAuth-spring-boot-starter</artifactId>
    <version>${version}</version>
</dependency>
<!-- LoopAuth的Redis拓展 -->
<dependency>
    <groupId>com.sobercoding</groupId>
    <artifactId>LoopAuth-redis</artifactId>
    <version>${version}</version>
</dependency>
<!-- LoopAuth的Servlet拓展 -->
<dependency>
    <groupId>com.sobercoding</groupId>
    <artifactId>LoopAuth-servlet</artifactId>
    <version>${version}</version>
</dependency>
<!-- LoopAuth核心功能 -->
<dependency>
    <groupId>com.sobercoding</groupId>
    <artifactId>LoopAuth-core</artifactId>
    <version>${version}</version>
</dependency>

```

## 版本历史

- `1.0.1`

> - 修订`LoopAuth-redis`中`jedis`版本,解决与`Spring-redis`的版本冲突

- `1.0.0`

> 正式版发布
> - 注解鉴权
> - 代码鉴权
> - 登录功能
> - 有/无状态登录
> - Redis登录业务存储分离