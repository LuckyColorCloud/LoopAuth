package com.sobercoding.loopauth.springboot3starter;

import com.sobercoding.loopauth.context.LoopAuthContext;
import com.sobercoding.loopauth.rbac.RbacStrategy;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.session.config.CookieConfig;
import com.sobercoding.loopauth.session.config.RedisConfig;
import com.sobercoding.loopauth.session.config.SessionConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * @author: Sober
 */
public class LoopAuthRegister {

    /**
     * 注入配置类
     * @author Sober
     * @return com.sobercoding.loopauth.session.config.SessionConfig
     */
    @Bean
    @ConfigurationProperties(prefix = "loop-auth.session")
    public SessionConfig getSessionConfig() {
        return new SessionConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "loop-auth.session.redis")
    public RedisConfig getRedisConfig() {
        return new RedisConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "loop-auth.session.cookie")
    public CookieConfig getCookieConfig() {
        return new CookieConfig();
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

    @Bean
    public void otherConfig(){
        RbacStrategy.getLoginId = () -> LoopAuthSession.getTokenModel().getLoginId();
    }

}
