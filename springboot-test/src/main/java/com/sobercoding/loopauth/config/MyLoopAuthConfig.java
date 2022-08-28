package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.dao.LoopAuthDao;
import com.sobercoding.loopauth.face.LoopAuthFaceImpl;
import com.sobercoding.loopauth.jedis.JedisDaoImpl;
import com.sobercoding.loopauth.router.LoopAuthHttpMode;
import com.sobercoding.loopauth.servlet.filter.LoopAuthServletFilter;
import org.springframework.context.annotation.*;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/09 15:22
 */
@Configuration
public class MyLoopAuthConfig {

    /**
     * 注册 [LoopAuth 全局过滤器] 此优先级高于  注解  如这里报错就不在进入注解
     */
//    @Bean
//    public LoopAuthServletFilter getSaServletFilter() {
//        return new LoopAuthServletFilter()
//                .addInclude("/**")
//                .addExclude("/test/login", LoopAuthHttpMode.GET)
//                // 认证函数: 每次请求执行
//                .setLoopAuthFilter((isIntercept,route) -> {
//                    if (isIntercept){
//                        // 拦截
//                        LoopAuthFaceImpl.isLogin();
//                    }
//                })
//                // 异常处理函数：每次认证函数发生异常时执行此函数
//                .setLoopAuthErrorFilter(e -> {
//                    e.printStackTrace();
//                    return e.getMessage();
//                });
//    }

    @Bean
    public LoopAuthDao getLoopAuthDao() {
        return new JedisDaoImpl();
    }

}
