package com.sobercoding.config;

import com.sobercoding.Impl.PermissionInterfaceImpl;
import com.sobercoding.loopauth.rbac.RbacStrategy;
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
     * 手动注入bean
     */
    @Autowired(required = false)
    public void setLoopAuthConfig() {
        // 注入获取Role\Permission的Bean
        RbacStrategy.setPermissionInterface(new PermissionInterfaceImpl());
        // 注入获取loginId的方法
        RbacStrategy.getLoginId = () -> "1";
    }

}
