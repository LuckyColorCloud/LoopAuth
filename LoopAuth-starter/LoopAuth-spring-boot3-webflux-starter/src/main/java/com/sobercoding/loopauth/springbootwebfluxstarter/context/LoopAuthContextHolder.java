package com.sobercoding.loopauth.springbootwebfluxstarter.context;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Sober
 */
public class LoopAuthContextHolder {

    /**
     * 上下文key
     */
    public static final Class<ServerWebExchange> CONTEXT_KEY = ServerWebExchange.class;

    /**
     * 设置上下文
     * @return reactor.core.publisher.Mono
     */
    public static Mono<ServerWebExchange> getContext() {
        return Mono.deferContextual(Mono::just)
                .map(ctx -> ctx.get(CONTEXT_KEY));
    }

}
