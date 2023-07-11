package com.sobercoding.loopauth.springbootwebfluxstarter.filter;

import com.sobercoding.loopauth.function.LoopAuthErrorFilter;
import com.sobercoding.loopauth.function.LoopAuthWebFluxFilterFun;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.context.LoopAuthContextThreadLocal;
import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthContextHolder;
import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthRequestForWebFlux;
import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthResponseForWebFlux;
import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthStorageForWebFlux;
import com.sobercoding.loopauth.util.LoopAuthUtil;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sober
 */
@Order(-100)
public class LoopAuthWebFluxFilter implements WebFilter {

    /**
     * 拦截路由
     */
    private ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> includeList = new ConcurrentHashMap<>();

    /**
     * 放行路由
     */
    private ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> excludeList = new ConcurrentHashMap<>();


    /**
     * ===================添加拦截路由=====================
     */
    public LoopAuthWebFluxFilter addInclude(String path, LoopAuthHttpMode... mode) {
        if (mode.length <= 0){
            includeList.put(path, new HashSet<LoopAuthHttpMode>(Collections.singleton(LoopAuthHttpMode.ALL)));
        }else {
            includeList.put(path, new HashSet<LoopAuthHttpMode>(Arrays.asList(mode)));
        }
        return this;
    }

    /**====================================================*/

    /**
     * ===================添加放行路由=====================
     */
    public LoopAuthWebFluxFilter addExclude(String path, LoopAuthHttpMode... mode) {
        if (mode.length <= 0){
            excludeList.put(path, new HashSet<LoopAuthHttpMode>(Collections.singleton(LoopAuthHttpMode.ALL)));
        }else {
            excludeList.put(path, new HashSet<LoopAuthHttpMode>(Arrays.asList(mode)));
        }
        return this;
    }

    /**====================================================*/

    /**
     * ===================获取放行路由=====================
     */
    public ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> getIncludeList() {
        return includeList;
    }

    public ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> getExcludeList() {
        return excludeList;
    }
    /**====================================================*/

    /**
     * 过滤路由
     */
    public LoopAuthWebFluxFilterFun<ServerWebExchange> loopAuthFilter = (boolean isIntercept, String route, ServerWebExchange e) -> {
        if (isIntercept){
            // 拦截
            LoopAuthSession.isLogin();
        }
    };
    /**
     * 过滤异常 处理
     */
    public LoopAuthErrorFilter loopAuthErrorFilter = e -> {
        e.printStackTrace();
        return e.getMessage();
    };

    /**
     * 设置 过滤规则
     *
     * @param loopAuthFilter 拦截器
     * @return com.sobercoding.loopauth.servlet.filter.LoopAuthServletFilter
     */
    public LoopAuthWebFluxFilter setLoopAuthFilter(LoopAuthWebFluxFilterFun<ServerWebExchange> loopAuthFilter) {
        this.loopAuthFilter = loopAuthFilter;
        return this;
    }

    /**
     * 设置异常处理规则
     *
     * @param loopAuthErrorFilter 拦截器异常执行
     * @return com.sobercoding.loopauth.servlet.filter.LoopAuthServletFilter
     */
    public LoopAuthWebFluxFilter setLoopAuthErrorFilter(LoopAuthErrorFilter loopAuthErrorFilter) {
        this.loopAuthErrorFilter = loopAuthErrorFilter;
        return this;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            //写入上下文
            LoopAuthContextThreadLocal.setContext(new LoopAuthRequestForWebFlux(exchange.getRequest()),
                    new LoopAuthResponseForWebFlux(exchange.getResponse()),
                    new LoopAuthStorageForWebFlux(exchange));

            // 路由
            String route = exchange.getRequest().getURI().getPath();
            // 当前请求方式
            LoopAuthHttpMode loopAuthHttpMode = LoopAuthHttpMode.toEnum(exchange.getRequest().getMethod().name());
            // 执行请求进入前操作
            loopAuthFilter.run(LoopAuthUtil.matchPaths(excludeList, includeList, route, loopAuthHttpMode), route, exchange);
        } catch (Throwable e) {
            // 1. 获取异常处理策略结果
            String result = String.valueOf(loopAuthErrorFilter.run(e));
            // 2. 写入输出流
            if(exchange.getResponse().getHeaders().getFirst("Content-Type") == null) {
                exchange.getResponse().getHeaders().set("Content-Type", "text/plain; charset=utf-8");
            }
            // 清除上下文
            LoopAuthContextThreadLocal.clearContextBox();
            return exchange.getResponse()
                    .writeWith(
                            Mono.just(
                                    exchange.getResponse()
                                            .bufferFactory()
                                            .wrap(result.getBytes())
                            )
                    );
        }
        // 执行
        return chain.filter(exchange)
                .contextWrite(ctx -> {
                    ctx = ctx.put(LoopAuthContextHolder.CONTEXT_KEY, exchange);
                    return ctx;
                })
                .doFinally(r -> {
                    // 清除上下文
                    LoopAuthContextThreadLocal.clearContextBox();
                });
    }
}
