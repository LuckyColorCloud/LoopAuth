package com.sobercoding.config;

import com.sobercoding.context.LoopAuthContextForSpring;
import com.sobercoding.loopauth.jedis.JedisDaoImpl;
import com.sobercoding.loopauth.session.SessionStrategy;
import com.sobercoding.loopauth.session.config.CookieConfig;
import com.sobercoding.loopauth.session.config.RedisConfig;
import com.sobercoding.loopauth.session.config.SessionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sober
 */
@Configuration
public class LoopAuthConfig {


    /**
     * 读取yml配置
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
     * 手动注入bean
     * @param loopAuthConfig
     * @param redisConfig
     * @param cookieConfig
     */
    @Autowired(required = false)
    public void setLoopAuthConfig(SessionConfig loopAuthConfig,
                                  RedisConfig redisConfig,
                                  CookieConfig cookieConfig) {
        SessionStrategy.setSessionConfig(loopAuthConfig);
        SessionStrategy.setRedisConfig(redisConfig);
        SessionStrategy.setCookieConfig(cookieConfig);
        // 注入Context
        SessionStrategy.setLoopAuthContext(new LoopAuthContextForSpring());
        // 注入Redis
        SessionStrategy.setLoopAuthDao(new JedisDaoImpl());
    }

}
