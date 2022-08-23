package com.sobercoding.loopauth.dao;

import java.util.Map;

/**
 * 持久层
 * @author: Sober
 */
public interface LoopAuthDao {

    /**
     * 获取用户会话
     * @author Sober
     * @param key 字典
     * @return java.lang.Object
     */
    Object get(String key);


    /**
     * 查看key是否存在
     * @author Sober
     * @param key 字典
     * @return boolean
     */
    boolean containsKey(String key);

    /**
     * 写入用户会话
     * @author Sober
     * @param key 字典
     * @param value 值
     * @param expirationTime 到期时间戳
     */
    void set(String key, Object value, long expirationTime);

    /**
     * 删除
     * @author Sober
     * @param key 字典
     */
    void remove(String key);


}
