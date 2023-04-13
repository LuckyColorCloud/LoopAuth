package com.sobercoding.loopauth;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.auth.verify.IpMate;
import com.sobercoding.loopauth.context.LoopAuthStorage;
import com.sobercoding.loopauth.function.VerifyFunction;
import com.sobercoding.loopauth.springbootstarter.pretrain.LoopAuthPretrain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;


/**
 * @author Sober
 */
@LoopAuthPretrain({"com.sobercoding.loopauth.auth.property.*", "com.sobercoding.loopauth.auth.verify.*"})
@SpringBootApplication
public class SpringbootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTestApplication.class, args);
    }


}
