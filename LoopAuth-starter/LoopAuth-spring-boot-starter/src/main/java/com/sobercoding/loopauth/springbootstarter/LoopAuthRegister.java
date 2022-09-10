package com.sobercoding.loopauth.springbootstarter;

import com.sobercoding.loopauth.rbac.RbacStrategy;
import com.sobercoding.loopauth.session.SessionStrategy;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.session.config.SessionConfig;
import com.sobercoding.loopauth.session.context.LoopAuthContext;
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
    public SessionConfig getLoopAuthConfig() {
        return new SessionConfig();
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
