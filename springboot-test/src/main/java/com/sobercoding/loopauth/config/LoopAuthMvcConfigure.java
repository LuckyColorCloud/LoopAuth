package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.springbootstarter.interceptor.InterceptorBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Yun
 */
@Component
public class LoopAuthMvcConfigure implements WebMvcConfigurer {
    /**
     * 注册LoopAuth 的拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注解拦截器
        registry.addInterceptor(new InterceptorBuilder().Annotation().builder()).addPathPatterns("/**");
        // abac拦截器
        registry.addInterceptor(new InterceptorBuilder().Abac().builder()).addPathPatterns("/**");
    }


}
