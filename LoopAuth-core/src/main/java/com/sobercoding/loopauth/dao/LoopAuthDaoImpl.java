package com.sobercoding.loopauth.dao;

import com.sobercoding.loopauth.model.TokenModel;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
        return this.dataPersistenceMap.get(key);
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
        return this.dataPersistenceMap.containsKey(key);
    }

    /**
     * @Method: set
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 写入缓存
     * @param key 会话模型
     * @param value 值
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    @Override
    public void set(String key, Object value) {
        this.dataPersistenceMap.put(key,value);
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
