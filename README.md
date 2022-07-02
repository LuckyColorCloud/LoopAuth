# LoopAuth
LoopAuth是一个Java开发的鉴权框架，主要使用RBAC进行权限校验。

同时我们会孵化更多LoopAuth相关插件例如:

- 提供ABAC思想的鉴权细粒度，优化RBAC缺陷插件
- 提供快速的登陆插件
- 提供鉴权缓存与业务缓存分离插件
- 针对临时用户，授权限时访问插件
- 等等等

## 目录结构
>- LoopAuth #项目总目录
>  - LoopAuth-core #核心代码模块
>  - LoopAuth-plugin #插件模块
>    - LoopAuth-abac #ABAC支持模块
>    - ... #更多插件
>  - LoopAuth-starter #多种框架启动器
>    - LoopAuth-spring-boot-starter #SpringBoot项目启动器
>    - ... #更多框架兼容启动器