package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.springbootstarter.interceptor.InterceptorBuilder;
import com.sobercoding.loopauth.springbootstarter.interceptor.LoopAuthAnnotationInterceptor;
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
        // 注册注解拦截器
//        registry.addInterceptor(new LoopAuthAnnotationInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new InterceptorBuilder().Abac().builder()).addPathPatterns("/**");
    }


}
