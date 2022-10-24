package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.abac.carryout.LoopAuthAbac;
import com.sobercoding.loopauth.jedis.JedisDaoImpl;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.model.Result;
import com.sobercoding.loopauth.session.annotation.CheckLogin;
import com.sobercoding.loopauth.session.annotation.CheckSessionAnnotation;
import com.sobercoding.loopauth.session.dao.LoopAuthDao;
import com.sobercoding.loopauth.springbootwebfluxstarter.filter.LoopAuthWebFluxFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author Sober
 */
@Configuration
public class MyLoopAuthConfig {

    @Bean
    public LoopAuthDao getLoopAuthDao() {
        return new JedisDaoImpl();
    }

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * 注册 [LoopAuth 全局过滤器]
     */
    @Bean
    public LoopAuthWebFluxFilter getLoopAuthServletFilter() {
        return new LoopAuthWebFluxFilter()
                .addInclude("/**")
                .addExclude("/test/login", LoopAuthHttpMode.GET)
                // 认证函数: 每次请求执行
                .setLoopAuthFilter((isIntercept,route,exchange) -> {
                    if (isIntercept){

                        // 获取请求对应的HandlerMethod
                        HandlerMethod handlerMethod = requestMappingHandlerMapping
                                .getHandler(exchange).cast(HandlerMethod.class).share().block();
                        // 登录注解鉴权
                        CheckSessionAnnotation.checkMethodAnnotation.accept(handlerMethod.getMethod());
                        // RBAC注解鉴权
                        CheckSessionAnnotation.checkMethodAnnotation.accept(handlerMethod.getMethod());

                        // ABAC鉴权
                        LoopAuthAbac.check(route, exchange.getRequest().getMethodValue());
                    }
                })
                .setLoopAuthErrorFilter(e -> Result.error(e.getMessage()));
    }


}
