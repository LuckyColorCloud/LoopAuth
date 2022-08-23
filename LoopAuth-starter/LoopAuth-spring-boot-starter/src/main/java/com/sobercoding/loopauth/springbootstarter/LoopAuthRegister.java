package com.sobercoding.loopauth.springbootstarter;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.config.LoopAuthConfig;
import com.sobercoding.loopauth.context.LoopAuthContext;
import com.sobercoding.loopauth.permission.PermissionInterface;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

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

    /**
     * 注入 上下文
     * @return
     */
    @Bean
    public LoopAuthContext getLoopAuthContext(){
        return new LoopAuthContextForSpring();
    }


}
