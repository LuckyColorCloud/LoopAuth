package com.sobercoding.loopauth;

import com.sobercoding.loopauth.config.LoopAuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/30 22:57
 */
public class LoopAuthInject {


    /**
     * @Method: getLoopAuthConfig
     * @Author: Sober
     * @Version: 0.0.1
     * @Description:
     * @param
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
