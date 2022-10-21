package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.model.AbacPoAndSu;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.abac.model.authProperty.IntervalType;
import com.sobercoding.loopauth.abac.model.authProperty.TimeInterval;
import com.sobercoding.loopauth.abac.model.builder.AbacPolicyFunBuilder;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthPermissionException;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.model.LoopAuthVerifyMode;
import com.sobercoding.loopauth.rbac.RbacStrategy;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthContextHolder;
import com.sobercoding.loopauth.springbootwebfluxstarter.filter.LoopAuthWebFluxFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

/**
 * @author Sober
 */
@Configuration
public class MyLoopAuthConfig {

    /**
     * 注册 [LoopAuth 全局过滤器] 此优先级高于  注解  如这里报错就不在进入注解
     */
    @Bean
    public LoopAuthWebFluxFilter getLoopAuthServletFilter() {
        return new LoopAuthWebFluxFilter()
                .addInclude("/**")
                .addExclude("/test/login", LoopAuthHttpMode.GET)
                // 认证函数: 每次请求执行
                .setLoopAuthFilter((isIntercept,route,exchange) -> {
                    if (isIntercept){
                        // 当前请求方式
                        LoopAuthHttpMode loopAuthHttpMode = LoopAuthHttpMode.toEnum(exchange.getRequest().getMethodValue());
                        Optional<Set<Policy>> policy = Optional.ofNullable(AbacStrategy.getAbacInterface().getPolicySet(route, loopAuthHttpMode));
                        policy.ifPresent(policies -> policies.forEach(item -> {
                            Set<String> keySet = item.getPropertyMap().keySet();
                            keySet.forEach(key -> {
                                Optional.ofNullable(AbacStrategy.abacPoAndSuMap
                                                .get(key))
                                        .ifPresent(abacPoAndSu -> {
                                            abacPoAndSu.getMaFunction()
                                                    .mate(abacPoAndSu.getSupplierMap().get(), item.getPropertyMap().get(key));
                                        });
                            });
                        }));
                    }
                });
    }

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
