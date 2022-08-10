package com.sobercoding.loopauth;

import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.util.JsonUtil;
import com.sobercoding.loopauth.util.LoopAuthUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Sober
 */
@SpringBootApplication
@MapperScan("com.sobercoding.loopauth.mapper")
public class SpringbootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTestApplication.class, args);
    }


}
