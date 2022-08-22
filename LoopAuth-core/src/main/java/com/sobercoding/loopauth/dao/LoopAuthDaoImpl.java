package com.sobercoding.loopauth.dao;

import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 缓存实现
 * @create: 2022/07/20 23:35
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
     * @Method: get
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取指定缓存
     * @param key 字典
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 17:16
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
     * @Method: containsKey
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取指定缓存
     * @param key 字典
     * @Return: boolean
     * @Exception:
     * @Date: 2022/8/8 17:16
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
    @Override
    public void set(String key, Object value, long expirationTime) {
        this.dataPersistenceMap.put(key,value);
        this.expirationTime.put(key,String.valueOf(System.currentTimeMillis() + expirationTime));
    }


    /**
     * @Method: remove
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 清除指定信息
     * @param key 字典
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    @Override
    public void remove(String key) {
        this.dataPersistenceMap.remove(key);
    }


}
