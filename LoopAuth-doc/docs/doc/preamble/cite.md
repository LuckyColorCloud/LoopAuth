---
title: 咋引入依赖?
---

- `${version}`请查看[版本历史](./version.md)

## Maven引入

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

## Gradle引入

```xml
implementation 'com.sobercoding:LoopAuth-core:${version}'

implementation 'com.sobercoding:LoopAuth-redis:${version}'

implementation 'com.sobercoding:LoopAuth-servlet:${version}'

implementation 'com.sobercoding:LoopAuth-spring-boot-starter:${version}'
```