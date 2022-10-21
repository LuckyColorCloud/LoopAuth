package com.sobercoding.loopauth.controller;

import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Sober
 */
@RestController
@RequestMapping("/test")
public class DemoController {


    @RequestMapping("/login")
    public Mono<String> hello(){
        LoopAuthSession.login("2");
        return Mono.just(LoopAuthSession.getUserSession().toString());
    }

    @GetMapping("/islogin")
    public Mono<String> hello1(){
        LoopAuthSession.isLogin();
        return Mono.just(LoopAuthSession.getUserSession().toString());
    }

    @GetMapping("/abac")
    public Mono<String> abac(){
        return Mono.just("检测通过");
    }

}
