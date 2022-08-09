package com.sobercoding.loopauth;

import com.sobercoding.loopauth.config.LoopAuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/30 23:52
 */
public class LoopAuthRegister {

    /**
     * @param
     * @Method: getLoopAuthConfig
     * @Author: Sober
     * @Version: 0.0.1
     * @Description:
     * @Return:
     * @Exception:
     * @Date: 2022/7/30 23:58
     */
    @Bean
    @ConfigurationProperties(prefix = "loop-auth")
    public LoopAuthConfig getLoopAuthConfig() {
        return new LoopAuthConfig();
    }
}
