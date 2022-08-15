package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.filter.LoopAuthServletFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/09 15:22
 */
@Configuration
public class MyLoopAuthConfig {

//    /**
//     * 注册 [LoopAuth 全局过滤器] 此优先级高于  注解  如这里报错就不在进入注解
//     */
//    @Bean
//    public LoopAuthServletFilter getSaServletFilter() {
//        return new LoopAuthServletFilter()
//                .addInclude("/**")
//                .addExclude("/test/login")
//                // 认证函数: 每次请求执行
//                .setLoopAuthFilter(obj -> {
//                    System.out.println(obj);
//                    System.out.println("=======================>个人业务 认证处理");
//                })
//                // 异常处理函数：每次认证函数发生异常时执行此函数
//                .setLoopAuthErrorFilter(e -> {
//                    System.out.println("=======================>全局异常 ");
//                    e.printStackTrace();
//                    return e.getMessage();
//                });
//    }

}
