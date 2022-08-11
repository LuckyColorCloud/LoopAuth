package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.filter.LoopAuthServletFilter;
import com.sobercoding.loopauth.interceptor.LoopAuthAnnotationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Yun
 */
@Component
public class LoopAuthConfigure implements WebMvcConfigurer {
    /**
     * 注册LoopAuth 的拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器
        registry.addInterceptor(new LoopAuthAnnotationInterceptor()).addPathPatterns("/**");
    }

    /**
     * 注册 [LoopAuth 全局过滤器]
     */
    @Bean
    public LoopAuthServletFilter getSaServletFilter() {
        return new LoopAuthServletFilter()
                .addInclude("/**")
                // 认证函数: 每次请求执行
                .setLoopAuthFilter(obj -> {
                    System.out.println("=======================>个人业务 认证处理");
                })
                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setLoopAuthErrorFilter(e -> {
                    System.out.println("=======================>全局异常 ");
                    return "";
                });
    }


}
