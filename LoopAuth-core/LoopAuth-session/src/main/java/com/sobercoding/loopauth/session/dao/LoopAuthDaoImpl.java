package com.sobercoding.loopauth.session.dao;

import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 持久层实现
 * @author: Sober
 */
public class LoopAuthDaoImpl implements LoopAuthDao {

    /**
     * 登录状态缓存
     * Token 和 Token模型 键值对应
     * 用户id 和 用户所有Token 键值对应
     */
    public Map<String, Object> dataPersistenceMap = new ConcurrentHashMap<>();

    /**
     * dataPersistenceMap有效期设置
     */
    public Map<String, String> expirationTime = new ConcurrentHashMap<>();


    /**
     * 获取用户会话
     * @author Sober
     * @param key 字典
     * @return java.lang.Object
     */
    @Override
    public Object get(String key) {
        String expirationTime = this.expirationTime.get(key);
        if (LoopAuthUtil.isEmpty(expirationTime)){
            return null;
        }
        long nowTime = System.currentTimeMillis();
        // 过期验证
        if (nowTime < Long.parseLong(expirationTime)){
            // 未过期
            return this.dataPersistenceMap.get(key);
        }else {
            // 过期操作
            remove(key);
            return null;
        }
    }

    /**
     * 查看key是否存在
     * @author Sober
     * @param key 字典
     * @return boolean
     */
    @Override
    public boolean containsKey(String key) {
        long expirationTime = Long.parseLong(this.expirationTime.get(key));
        if (LoopAuthUtil.isEmpty(expirationTime)){
            return false;
        }
        long nowTime = System.currentTimeMillis();
        // 过期验证
        if (nowTime < expirationTime){
            // 未过期
            return this.dataPersistenceMap.containsKey(key);
        }else {
            // 过期操作
            remove(key);
            return false;
        }
    }

    /**
     * 写入用户会话
     * @author Sober
     * @param key 字典
     * @param value 值
     * @param expirationTime 到期时间戳
     */
    @Override
    public void set(String key, Object value, long expirationTime) {
        // 插入数据
        this.dataPersistenceMap.put(key,value);
        // 插入过期时间
        this.expirationTime.put(key,String.valueOf(System.currentTimeMillis() + expirationTime));
    }


    /**
     * 删除
     * @author Sober
     * @param key 字典
     */
    @Override
    public void remove(String key) {
        // 删除数据
        this.dataPersistenceMap.remove(key);
        // 删除过期时间
        this.expirationTime.remove(key);
    }


}
