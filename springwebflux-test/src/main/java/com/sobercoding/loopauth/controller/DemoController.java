package com.sobercoding.loopauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Sober
 */
@RestController
public class DemoController {


    @GetMapping("/test")
    public Mono<String> hello(){
        return Mono.just("Hello WebFlux Test dev!");
    }

}
