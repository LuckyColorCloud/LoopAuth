package com.sobercoding.loopauth.dao;

import java.util.Map;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/20 23:35
 */
public interface LoopAuthDao {

    /**
     * @Method: getUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取用户会话
     * @param key 字典
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    Object get(String key);


    /**
     * @Method: containsKey
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 查看key是否存在
     * @param key 字典
     * @Return: boolean
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    boolean containsKey(String key);

    /**
     * @Method: setUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 写入用户会话
     * @param key 字典
     * @param value 值
     * @param expirationTime 到期时间戳
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    void set(String key, Object value, long expirationTime);

    /**
     * @Method: setUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 清空登录状态
     * @param key 字典
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    void remove(String key);


}
