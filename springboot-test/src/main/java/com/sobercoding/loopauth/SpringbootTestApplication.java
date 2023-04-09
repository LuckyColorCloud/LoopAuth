package com.sobercoding.loopauth;

import com.sobercoding.loopauth.springbootstarter.pretrain.LoopAuthPretrain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;


/**
 * @author Sober
 */
@LoopAuthPretrain({"com.sobercoding.loopauth.auth.property.*", "com.sobercoding.loopauth.auth.verify.*"})
@SpringBootApplication
public class SpringbootTestApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringbootTestApplication.class, args);

    }


}
