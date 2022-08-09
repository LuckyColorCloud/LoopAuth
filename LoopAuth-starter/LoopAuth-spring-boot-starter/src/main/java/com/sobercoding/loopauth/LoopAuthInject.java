package com.sobercoding.loopauth;

import com.sobercoding.loopauth.config.LoopAuthConfig;

import javax.annotation.Resource;


/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/30 22:57
 */
public class LoopAuthInject {

    /**
     * @param
     * @Method: setLoopAuthConfig
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 注入loopAuthConfig到core
     * @Return:
     * @Exception:
     * @Date: 2022/7/31 0:00
     */
    @Resource
    public void setLoopAuthConfig(LoopAuthConfig loopAuthConfig) {
        LoopAuthStrategy.setLoopAuthConfig(loopAuthConfig);
    }


}
