---
title: 试试Redis做持久化
---

# 使用Redis

## 引入依赖

- `${version}`请查看[版本历史](../preamble/cite.md#版本历史)，请使用最新正式版，且版本与其余拓展最好保持一致
- `jedis`包版本建议与本文一致,此版本也可同时兼容`Spring-redis`

```xml
<!-- LoopAuth的Redis拓展 -->
<dependency>
    <groupId>com.sobercoding</groupId>
    <artifactId>LoopAuth-redis</artifactId>
    <version>${version}</version>
</dependency>
<!-- LoopAuth-redis依赖的Redis连接器 -->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>3.6.3</version>
</dependency>
```

## 注入

### 自动注入

```java
@Bean
public LoopAuthDao getLoopAuthDao() {
    return new JedisDaoImpl();
}
```

### 手动注入

- 保证项目启动时执行下面语句即可

```java
LoopAuthStrategy.setLoopAuthDao(new JedisDaoImpl());
```

## 配置

- 请根据自己的Redis客户端进行配置，没有密码可以不使用`password`配置项
- `need-pool`配置项如果开启，则需要配置`max-total`、`max-idle`、`min-idle`

```yml
loop-auth:
  redis-config: # redis配置 需要加载LoopAuth-redis包才生效
    host: xxxxxxxx # 服务器地址 默认127.0.0.1
    password: xxxxxxx # 密码  默认空
    port: 8910 # 端口号， 默认是6379
    database-no: 1 # 链接的数据库 默认0
    time-out: 1000 # 超时时间(单位毫秒) 默认2000
    need-pool: true # 是否需要连接池 默认false即单个长连接
    max-total: 10 # 最大连接数 默认16
    max-idle: 5 # 最大空闲连接数 默认8
    min-idle: 2 # 最小空闲连接数 默认4
```