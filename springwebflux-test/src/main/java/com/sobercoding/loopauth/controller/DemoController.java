package com.sobercoding.loopauth.controller;

import com.sobercoding.loopauth.context.ReactiveRequestContextHolder;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
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
        LoopAuthSession.login("1");
        return Mono.just("登录成功");
    }

    @GetMapping("/islogin")
    public Mono<String> hello1(){
        LoopAuthSession.isLogin();
        return Mono.just("已经登录");
    }

}
