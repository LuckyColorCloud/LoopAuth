package com.sobercoding.loopauth;

import com.sobercoding.loopauth.abac.annotation.PropertyPretrain;
import com.sobercoding.loopauth.abac.annotation.VerifyPrestrain;
import com.sobercoding.loopauth.springbootstarter.pretrain.LoopAuthPretrain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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
