package com.sobercoding.loopauth.springbootstarter;

import com.sobercoding.loopauth.config.LoopAuthConfig;
import com.sobercoding.loopauth.context.LoopAuthContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * @author: Sober
 */
public class LoopAuthRegister {

    /**
     * 注入配置类
     * @author Sober
     * @return com.sobercoding.loopauth.config.LoopAuthConfig
     */
    @Bean
    @ConfigurationProperties(prefix = "loop-auth")
    public LoopAuthConfig getLoopAuthConfig() {
        return new LoopAuthConfig();
    }

    /**
     * 注入 上下文
     * @author Sober
     * @return com.sobercoding.loopauth.context.LoopAuthContext
     */
    @Bean
    public LoopAuthContext getLoopAuthContext(){
        return new LoopAuthContextForSpring();
    }


}
