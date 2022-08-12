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
    String get(String key);


    /**
     * @Method: containsKey
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取指定缓存
     * @param key 字典
     * @Return: boolean
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    boolean containsKey(String key);

    /**
     * @Method: getAll
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取全部缓存
     * @Return:
     * @Exception:
     * @Date: 2022/8/10 23:11
     */
    Map<String, String> getAll();

    /**
     * @Method: setUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 写入用户会话
     * @param key 字典
     * @param value 值
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    void set(String key, String value);

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

    /**
     * @Method: removeAll
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 清空登录态
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    void removeAll();

}
