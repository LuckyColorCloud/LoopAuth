package com.sobercoding.loopauth;


import com.sobercoding.loopauth.certificate.TokenBehavior;
import com.sobercoding.loopauth.config.LoopAuthConfig;
import com.sobercoding.loopauth.context.LoopAuthContext;
import com.sobercoding.loopauth.dao.LoopAuthDao;
import com.sobercoding.loopauth.dao.LoopAuthDaoFace;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: LoopAuth  Bean管理器
 * @create: 2022/07/20 19:43
 */

public class LoopAuthStrategy {

    /**
     * 配置文件
     */
    private volatile static LoopAuthConfig loopAuthConfig;

    public static void setLoopAuthConfig(LoopAuthConfig loopAuthConfig) {
        LoopAuthStrategy.loopAuthConfig = loopAuthConfig;
    }

    public static LoopAuthConfig getLoopAuthConfig() {
        if (LoopAuthStrategy.loopAuthConfig == null){
            synchronized(LoopAuthStrategy.class){
                if (LoopAuthStrategy.loopAuthConfig == null){
                    LoopAuthStrategy.loopAuthConfig = LoopAuthConfig.builder().build();
                }
            }
        }
        return LoopAuthStrategy.loopAuthConfig;
    }

    /**
     * Token会话操作
     */
    private volatile static LoopAuthDao loopAuthDao;

    public static LoopAuthDao getLoopAuthDao() {
        if (LoopAuthStrategy.loopAuthDao == null){
            synchronized(LoopAuthStrategy.class){
                if (LoopAuthStrategy.loopAuthDao == null){
                    LoopAuthStrategy.loopAuthDao = new LoopAuthDaoFace();
                }
            }
        }
        return LoopAuthStrategy.loopAuthDao;
    }

    /**
     * 上下文管理
     */
    private volatile static LoopAuthContext loopAuthContext;

    /**
     * Token风格
     */
    private volatile static TokenBehavior tokenBehavior;

    public static TokenBehavior getTokenBehavior() {
        if (LoopAuthStrategy.tokenBehavior == null){
            synchronized(LoopAuthStrategy.class){
                if (LoopAuthStrategy.tokenBehavior == null){
                    LoopAuthStrategy.tokenBehavior = LoopAuthStrategy.getLoopAuthConfig().getTokenStyle().getBean();
                }
            }
        }
        return LoopAuthStrategy.tokenBehavior;
    }


}
