package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.model.AbacPoAndSu;
import com.sobercoding.loopauth.abac.model.authProperty.IntervalType;
import com.sobercoding.loopauth.abac.model.authProperty.TimeInterval;
import com.sobercoding.loopauth.abac.model.builder.AbacPolicyFunBuilder;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthPermissionException;
import com.sobercoding.loopauth.jedis.JedisDaoImpl;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.model.LoopAuthVerifyMode;
import com.sobercoding.loopauth.rbac.RbacStrategy;
import com.sobercoding.loopauth.servlet.filter.LoopAuthServletFilter;
import com.sobercoding.loopauth.session.SessionStrategy;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.session.context.LoopAuthStorage;
import com.sobercoding.loopauth.session.dao.LoopAuthDao;
import org.springframework.context.annotation.*;

import javax.websocket.Session;

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
//                        LoopAuthSession.isLogin();
//                    }
//                })
//                // 异常处理函数：每次认证函数发生异常时执行此函数
//                .setLoopAuthErrorFilter(e -> {
//                    e.printStackTrace();
//                    return e.getMessage();
//                });
//    }

//    @Bean
//    public LoopAuthDao getLoopAuthDao() {
//        return new JedisDaoImpl();
//    }

    @Bean
    public void policyFun() {
        AbacStrategy.abacPoAndSuMap = new AbacPolicyFunBuilder()
                .loginId()
                .setLoginId(() -> LoopAuthSession.getTokenModel().getLoginId())
                .loginIdNot()
                .setLoginIdNot(() -> LoopAuthSession.getTokenModel().getLoginId())
                .role(LoopAuthVerifyMode.OR)
                .setRole(LoopAuthVerifyMode.OR, () -> RbacStrategy.getPermissionInterface().getRoleSet(LoopAuthSession.getTokenModel().getLoginId(), ""))
                .permission(LoopAuthVerifyMode.OR)
                // 时间区间范围内
                .setPolicyFun("timeSection",
                        new AbacPoAndSu()
                                .setMaFunction((value, rule) -> {
                                    TimeInterval timeInterval = (TimeInterval) rule;
                                    long newTime = (long) value;
                                    if (!(newTime > timeInterval.getStart() && newTime < timeInterval.getEnd())) {
                                        throw new LoopAuthPermissionException(LoopAuthExceptionEnum.NO_PERMISSION);
                                    }
                                })
                                .setSupplierMap(IntervalType.NONE::creation)
                )
                .build();

    }

}
