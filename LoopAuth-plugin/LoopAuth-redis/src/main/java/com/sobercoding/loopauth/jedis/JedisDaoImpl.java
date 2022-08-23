package com.sobercoding.loopauth.jedis;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.config.LoopAuthConfig;
import com.sobercoding.loopauth.config.RedisConfig;
import com.sobercoding.loopauth.dao.LoopAuthDao;
import com.sobercoding.loopauth.exception.LoopAuthDaoException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.util.JsonUtil;
import com.sobercoding.loopauth.util.LoopAuthUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import java.util.Set;

/**
 * <p>
 * Description: Redis 操作助手
 * </p>
 *
 * @author: Weny
 * @date: 2022/8/13
 * @see: com.sobercoding
 * @version: v1.0.0
 */
public class JedisDaoImpl implements LoopAuthDao{

    private volatile Jedis redisConn;

    private Jedis getRedisConn(){
        if (redisConn == null){
            synchronized(this){
                if (redisConn == null){
                    redisConn = new JedisConn().getJedis();
                }
            }
        }
        return redisConn;
    }

    /**
     * 获取缓存值
     * value 需要根据 Config 设置的 tokenPersistencePrefix 及 loginIdPersistencePrefix 转换
     * key 前几个字符如果为 tokenPersistencePrefix设置的值则 转 TokenModel
     * key 前几个字符如果为 loginIdPersistencePrefix设置的值则 转 Set<String>
     *
     * @param key Redis键
     * @return value
     */
    @Override
    public Object get(String key) {
        String json = getRedisConn().get(key);
        if (LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix().equals(key.substring(0, LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix().length()))) {
            if (LoopAuthUtil.isNotEmpty(json)) {
                return JsonUtil.jsonToObj(json,TokenModel.class);
            }
        }
        if (LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix().equals(key.substring(0, LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix().length()))) {
            if (LoopAuthUtil.isNotEmpty(json)) {
                return JsonUtil.<String>jsonToList(getRedisConn().get(key),String.class);
            }
        }
        return null;
    }

    /**
     * 判断key是否存在
     *
     * @param key Redis中的键
     * @return
     */
    @Override
    public boolean containsKey(String key) {
        return getRedisConn().exists(key);
    }

    @Override
    public void set(String key, Object value, long expirationTime) {
        String json = null;
        if (LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix().equals(key.substring(0, LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix().length()))) {
            TokenModel tokenModel = (TokenModel) value;
            json = JsonUtil.objToJson(tokenModel);
        }
        if (LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix().equals(key.substring(0, LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix().length()))) {
            Set<String> strings = (Set<String>) value;
            json = JsonUtil.objToJson(strings);
        }
        LoopAuthDaoException.isEmpty(json, LoopAuthExceptionEnum.DATA_EXCEPTION);
        LoopAuthDaoException.isOK(getRedisConn().set(key, json, SetParams.setParams().ex(expirationTime / 1000)),
                LoopAuthExceptionEnum.CACHE_FAILED);
    }

    /**
     * 获取Redis中的键
     *
     * @param key 操作失败抛出异常  异常请参考exception目录其他异常 新建一个DaoException异常类，并在Enum枚举类新增  持久层操作失败的异常code msg
     */
    @Override
    public void remove(String key) {
        LoopAuthDaoException.isOK(getRedisConn().del(key) == 1 ? "OK":"", LoopAuthExceptionEnum.CACHE_FAILED);
    }



}
