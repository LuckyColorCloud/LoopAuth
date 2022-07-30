package com.sobercoding.loopauth;

import com.sobercoding.loopauth.config.LoopAuthConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/30 23:52
 */
public class LoopAuthRegister {

    /**
     * @Method: setLoopAuthConfig
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 注入loopAuthConfig到core
     * @param
     * @Return:
     * @Exception:
     * @Date: 2022/7/31 0:00
     */
    @Resource
    public void setLoopAuthConfig(LoopAuthConfig loopAuthConfig) {
        LoopAuthStrategy.setLoopAuthConfig(loopAuthConfig);
    }
}
