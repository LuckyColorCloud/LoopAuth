package com.sobercoding.loopauth.springbootstarter;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.context.LoopAuthContext;
import com.sobercoding.loopauth.rbac.RbacStrategy;
import com.sobercoding.loopauth.rbac.face.PermissionInterface;
import com.sobercoding.loopauth.session.SessionStrategy;
import com.sobercoding.loopauth.session.config.CookieConfig;
import com.sobercoding.loopauth.session.config.RedisConfig;
import com.sobercoding.loopauth.session.config.SessionConfig;
import com.sobercoding.loopauth.session.dao.LoopAuthDao;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 注入
 */
public class LoopAuthInject {

    /**
     * 注入
     * @param loopAuthConfig 会话配置
     */
    @Autowired(required = false)
    public void setSessionConfig(SessionConfig loopAuthConfig) {
        SessionStrategy.setSessionConfig(loopAuthConfig);
    }

    /**
     * 注入
     * @param redisConfig redis配置
     */
    @Autowired(required = false)
    public void setRedisConfig(RedisConfig redisConfig) {
        SessionStrategy.setRedisConfig(redisConfig);
    }

    /**
     * 注入
     * @param cookieConfig cookie配置
     */
    @Autowired(required = false)
    public void setLoopAuthConfig(CookieConfig cookieConfig) {
        SessionStrategy.setCookieConfig(cookieConfig);
    }

    /**
     * 注入
     * @param loopAuthContext 上下文
     */
    @Autowired(required = false)
    public void setLoopAuthContext(LoopAuthContext loopAuthContext){
        SessionStrategy.setLoopAuthContext(loopAuthContext);
    }

    /**
     * 注入权限认证Bean
     * @author Sober
     * @param permissionInterface 权限类
     */
    @Autowired(required = false)
    public void setPermissionInterface(PermissionInterface permissionInterface) {
        RbacStrategy.setPermissionInterface(permissionInterface);
    }

    /**
     * 注入ABAC认证Bean
     * @author Sober
     * @param abacInterface ABAC认证
     */
    @Autowired(required = false)
    public void setAbacInterfaceImpl(AbacInterface abacInterface) {
        AbacStrategy.setAbacInterface(abacInterface);
    }

    /**
     * 注入持久层
     * @author Sober
     * @param loopAuthDao 持久层类
     */
    @Autowired(required = false)
    public void setLoopAuthDao(LoopAuthDao loopAuthDao) {
        SessionStrategy.setLoopAuthDao(loopAuthDao);
    }

}
