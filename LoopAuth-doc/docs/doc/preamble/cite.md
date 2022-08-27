---
title: 咋引入依赖?
---

## Maven引入
- 直接通过`pom.xml`文件引入即可
- `${version}`请查看[版本历史](./version.md)

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