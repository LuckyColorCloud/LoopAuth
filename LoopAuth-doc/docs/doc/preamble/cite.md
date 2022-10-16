---
title: 咋引入依赖?
---

- `${version}`请查看[版本历史](./version.md)

## Maven引入

```xml

<!-- LoopAuth的Springboot插件, 包含session、rbac、abac模块 -->
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
<!-- LoopAuth会话管理 -->
<dependency>
    <groupId>com.sobercoding</groupId>
    <artifactId>LoopAuth-session</artifactId>
    <version>${revision}</version>
</dependency>
<!-- LoopAuth-RBAC鉴权 -->
<dependency>
    <groupId>com.sobercoding</groupId>
    <artifactId>LoopAuth-rbac</artifactId>
    <version>${revision}</version>
</dependency>
<!-- LoopAuth-ABAC鉴权 -->
<dependency>
    <groupId>com.sobercoding</groupId>
    <artifactId>LoopAuth-abac</artifactId>
    <version>${revision}</version>
</dependency>

```

## Gradle引入

```xml
implementation 'com.sobercoding:LoopAuth-rbac:${rbac}'

implementation 'com.sobercoding:LoopAuth-abac:${abac}'

implementation 'com.sobercoding:LoopAuth-session:${session}'

implementation 'com.sobercoding:LoopAuth-redis:${version}'

implementation 'com.sobercoding:LoopAuth-servlet:${version}'

implementation 'com.sobercoding:LoopAuth-spring-boot-starter:${version}'
```