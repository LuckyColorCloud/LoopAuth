package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.policy.AbacPolicyFunBuilder;
import com.sobercoding.loopauth.abac.policy.PolicyWrapper;
import com.sobercoding.loopauth.abac.policy.model.Action;
import com.sobercoding.loopauth.abac.policy.model.Contextual;
import com.sobercoding.loopauth.abac.policy.model.Subject;
import com.sobercoding.loopauth.impl.AbacInterFaceImpl;
import com.sobercoding.loopauth.model.ActionModel;
import com.sobercoding.loopauth.model.ContextualModel;
import com.sobercoding.loopauth.model.UserModel;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.springbootstarter.interceptor.InterceptorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.function.Supplier;

/**
 * @author Sober
 */
@Component
public class LoopAuthMvcConfigure implements WebMvcConfigurer {
    /**
     * 注册LoopAuth 的拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器
        registry.addInterceptor(new InterceptorBuilder().annotation().builder()).addPathPatterns("/**");
        // abac 拦截器
        registry.addInterceptor(new InterceptorBuilder().abac().builder()).addPathPatterns("/**");
    }

//    @Bean
//    public LoopAuthDao getLoopAuthDao() {
//        return new JedisDaoImpl();
//    }

    @Bean
    public void setAbacPoAndSuMap() {
//        AbacStrategy.abacPoAndSuMap = new AbacPolicyFunBuilder()
//                // 自定义登录id校验的鉴权规则
//                .loginId()
//                .setLoginId(() -> LoopAuthSession.getTokenModel().getLoginId())
//                .build();

    }

//    /**
//     * 注册 [LoopAuth 全局过滤器] 此优先级高于  注解  如这里报错就不在进入注解
//     */
//    @Bean
//    public LoopAuthServletFilter getLoopAuthServletFilter() {
//        return new LoopAuthServletFilter()
//                .addInclude("/**")
//                .addExclude("/test/login", LoopAuthHttpMode.GET)
//                // 认证函数: 每次请求执行
//                .setLoopAuthFilter((isIntercept,route) -> {
//                    if (isIntercept){
//                        // 拦截代码块
//                        LoopAuthSession.isLogin();
//                    }else {
//                        // 放行代码块
//                    }
//                })
//                // 异常处理函数：每次认证函数发生异常时执行此函数
//                .setLoopAuthErrorFilter(e -> Result.error(e.getMessage()));
//    }

}