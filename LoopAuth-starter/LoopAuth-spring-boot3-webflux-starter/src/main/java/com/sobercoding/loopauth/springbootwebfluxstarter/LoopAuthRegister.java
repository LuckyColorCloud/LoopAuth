package com.sobercoding.loopauth.springbootwebfluxstarter;

import com.sobercoding.loopauth.rbac.RbacStrategy;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.session.config.CookieConfig;
import com.sobercoding.loopauth.session.config.RedisConfig;
import com.sobercoding.loopauth.session.config.SessionConfig;
import com.sobercoding.loopauth.context.LoopAuthContext;
import com.sobercoding.loopauth.context.LoopAuthContextThreadLocal;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * 创建
 * @author Sober
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

    /**
     * 创建bean
     * @return com.sobercoding.loopauth.session.config.RedisConfig
     */
    @Bean
    @ConfigurationProperties(prefix = "loop-auth.session.redis")
    public RedisConfig getRedisConfig() {
        return new RedisConfig();
    }

    /**
     * 创建bean
     * @return com.sobercoding.loopauth.session.config.CookieConfig
     */
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
        return new LoopAuthContextThreadLocal();
    }

    /**
     * 其他配置注入
     */
    @Bean
    public void otherConfig(){
        RbacStrategy.getLoginId = () -> LoopAuthSession.getTokenModel().getLoginId();
    }

}
