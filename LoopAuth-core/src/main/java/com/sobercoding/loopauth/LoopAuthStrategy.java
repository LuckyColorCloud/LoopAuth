package com.sobercoding.loopauth;


import com.sobercoding.loopauth.dao.LoopAuthDaoImpl;
import com.sobercoding.loopauth.fabricate.TokenBehavior;
import com.sobercoding.loopauth.config.LoopAuthConfig;
import com.sobercoding.loopauth.dao.LoopAuthDao;

import java.util.function.Function;

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
                    LoopAuthStrategy.loopAuthConfig = new LoopAuthConfig();
                }
            }
        }
        return LoopAuthStrategy.loopAuthConfig;
    }

    /**
     * Token风格
     */
    private volatile static TokenBehavior tokenBehavior;

    public static void setTokenBehavior(TokenBehavior tokenBehavior) {
        LoopAuthStrategy.tokenBehavior = tokenBehavior;
    }

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

    /**
     * 会话缓存操作
     */
    private volatile static LoopAuthDao loopAuthDao;

    public static void setLoopAuthDao(LoopAuthDao loopAuthDao) {
        LoopAuthStrategy.loopAuthDao = loopAuthDao;
    }

    public static LoopAuthDao getLoopAuthDao() {
        if (LoopAuthStrategy.loopAuthDao == null){
            synchronized(LoopAuthStrategy.class){
                if (LoopAuthStrategy.loopAuthDao == null){
                    LoopAuthStrategy.loopAuthDao = new LoopAuthDaoImpl();
                }
            }
        }
        return LoopAuthStrategy.loopAuthDao;
    }

    /**
     * @Method:
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取盐默认方法
     * @param: userId 用户id
     * @Return:
     * @Exception: 
     * @Date: 2022/8/9 15:21
     */
    public static Function<String,String> getSecretKey = userId -> LoopAuthStrategy
            .getLoopAuthConfig().getSecretKey();


}
