package com.sobercoding.loopauth.jedis;


import com.sobercoding.loopauth.exception.LoopAuthDaoException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.session.SessionStrategy;
import com.sobercoding.loopauth.session.dao.LoopAuthDao;
import com.sobercoding.loopauth.session.model.TokenModel;
import com.sobercoding.loopauth.util.JsonUtil;
import com.sobercoding.loopauth.util.LoopAuthUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Set;


/**
 * Redis 操作助手
 * @author: Weny
 */
public class JedisDaoImpl implements LoopAuthDao {

    private volatile Jedis redisConn;

    private Jedis getRedisConn() {
        if (redisConn == null) {
            synchronized (this) {
                if (redisConn == null) {
                    redisConn = new JedisConn().getJedis();
                }
            }
        }
        return redisConn;
    }

    /**
     * 获取用户会话
     * @author Sober
     * @param key 字典
     * @return java.lang.Object
     */
    @Override
    public Object get(String key) {
        String json = getRedisConn().get(key);
        if (SessionStrategy.getLoopAuthConfig().getTokenPersistencePrefix().equals(key.substring(0, SessionStrategy.getLoopAuthConfig().getTokenPersistencePrefix().length()))) {
            if (LoopAuthUtil.isNotEmpty(json)) {
                return json;
            }
        }
        if (SessionStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix().equals(key.substring(0, SessionStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix().length()))) {
            if (LoopAuthUtil.isNotEmpty(json)) {
                return JsonUtil.<TokenModel>jsonToList(getRedisConn().get(key), TokenModel.class);
            }
        }
        return null;
    }

    /**
     * 查看key是否存在
     * @author Sober
     * @param key 字典
     * @return boolean
     */
    @Override
    public boolean containsKey(String key) {
        return getRedisConn().exists(key);
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
        String json = null;
        if (SessionStrategy.getLoopAuthConfig().getTokenPersistencePrefix().equals(key.substring(0, SessionStrategy.getLoopAuthConfig().getTokenPersistencePrefix().length()))) {
            json = (String) value;
        }
        if (SessionStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix().equals(key.substring(0, SessionStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix().length()))) {
            Set<TokenModel> tokenModels = (Set<TokenModel>) value;
            json = JsonUtil.objToJson(tokenModels);
        }
        LoopAuthDaoException.isEmpty(json, LoopAuthExceptionEnum.DATA_EXCEPTION);
        LoopAuthDaoException.isTrue("ok".equalsIgnoreCase(getRedisConn().set(key, json, SetParams.setParams().ex(expirationTime / 1000))),
                LoopAuthExceptionEnum.CACHE_FAILED);
    }

    /**
     * 删除
     * @author Sober
     * @param key 字典
     */
    @Override
    public void remove(String key) {
        getRedisConn().del(key);
    }


}
