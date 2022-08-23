package com.sobercoding.loopauth.springbootstarter;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.config.LoopAuthConfig;
import com.sobercoding.loopauth.context.LoopAuthContext;
import com.sobercoding.loopauth.dao.LoopAuthDao;
import com.sobercoding.loopauth.permission.PermissionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;


/**
 * @author: Sober
 */
public class LoopAuthInject {

    @Autowired(required = false)
    public void setLoopAuthConfig(LoopAuthConfig loopAuthConfig) {
        LoopAuthStrategy.setLoopAuthConfig(loopAuthConfig);
    }

    @Autowired(required = false)
    public void setLoopAuthContext(LoopAuthContext loopAuthContext){
        LoopAuthStrategy.setLoopAuthContext(loopAuthContext);
    }

    /**
     * 注入权限认证Bean
     * @author Sober
     * @param permissionInterface 权限类
     */
    @Autowired(required = false)
    public void setPermissionInterface(PermissionInterface permissionInterface) {
        LoopAuthStrategy.setPermissionInterface(permissionInterface);
    }

    /**
     * 注入持久层
     * @author Sober
     * @param loopAuthDao 持久层类
     */
    @Autowired(required = false)
    public void setLoopAuthDao(LoopAuthDao loopAuthDao) {
        LoopAuthStrategy.setLoopAuthDao(loopAuthDao);
    }

}
