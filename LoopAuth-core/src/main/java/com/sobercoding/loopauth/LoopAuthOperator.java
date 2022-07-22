package com.sobercoding.loopauth;


import com.sobercoding.loopauth.config.LoopAuthConfig;
import com.sobercoding.loopauth.context.LoopAuthContext;
import com.sobercoding.loopauth.dao.LoopAuthDao;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 执行者
 * LoopAuth全局管理器，
 * 实现所有组件功能插槽，便于用户重写
 * @create: 2022/07/20 19:43
 */

public class LoopAuthOperator {

    /**
     * 配置文件
     */
    private volatile static LoopAuthConfig loopAuthConfig;

    /**
     * Dao
     */
    private volatile static LoopAuthDao loopAuthDao;

    /**
     * 上下文管理
     */
    private volatile static LoopAuthContext loopAuthContext;

}
