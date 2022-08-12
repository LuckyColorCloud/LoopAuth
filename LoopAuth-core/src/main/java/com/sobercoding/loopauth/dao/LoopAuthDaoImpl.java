package com.sobercoding.loopauth.dao;

import com.sobercoding.loopauth.model.TokenModel;

import java.util.List;
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
     * 用户id 和 Token模型 键值对应
     * 等于缓存UserSession类数据 但是都序列化成String了
     */
    private final Map<String, Object> LOOP_AUTH_DATA_PERSISTENCE  = new ConcurrentHashMap<>();


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
        return this.LOOP_AUTH_DATA_PERSISTENCE.get(key);
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
        return this.LOOP_AUTH_DATA_PERSISTENCE.containsKey(key);
    }

    @Override
    public Map<String, Object> getAll() {
        return this.LOOP_AUTH_DATA_PERSISTENCE;
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
        this.LOOP_AUTH_DATA_PERSISTENCE.put(key, value);
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
        this.LOOP_AUTH_DATA_PERSISTENCE.remove(key);
    }

    /**
     * @Method: removeAll
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 清空缓存
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    @Override
    public void removeAll() {
        this.LOOP_AUTH_DATA_PERSISTENCE.clear();
    }

}
