package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.session.context.LoopAuthContextThreadLocal;
import com.sobercoding.loopauth.session.context.LoopAuthRequest;
import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthRequestForWebFlux;
import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthResponseForWebFlux;
import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthStorageForWebFlux;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author Sober
 */
@Configuration
public class MyWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        LoopAuthContextThreadLocal.setContext(new LoopAuthRequestForWebFlux(exchange.getRequest()),
                new LoopAuthResponseForWebFlux(exchange.getResponse()),
                new LoopAuthStorageForWebFlux(exchange));

        return chain.filter(exchange);
    }
}
